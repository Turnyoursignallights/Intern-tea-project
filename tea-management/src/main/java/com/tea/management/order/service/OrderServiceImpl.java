package com.tea.management.order.service;

import com.tea.management.inventory.entity.Tea;
import com.tea.management.inventory.exception.TeaNotFoundException;
import com.tea.management.inventory.repository.TeaRepositoryJpa;
import com.tea.management.order.dto.*;
import com.tea.management.order.entity.Order;
import com.tea.management.order.entity.OrderItem;
import com.tea.management.order.entity.OrderStatus;
import com.tea.management.order.exception.OrderNotFoundException;
import com.tea.management.order.exception.InsufficientInventoryException;
import com.tea.management.order.repository.OrderRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final TeaRepositoryJpa teaRepository;

    public OrderServiceImpl(OrderRepository orderRepository, TeaRepositoryJpa teaRepository) {
        this.orderRepository = orderRepository;
        this.teaRepository = teaRepository;
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        int maxRetries = 5;
        int attempts = 0;
        Order savedOrder = null;

        while (attempts < maxRetries && savedOrder == null) {
            try {
                // Convert DTO to entity - this generates a new order number
                Order order = OrderMapper.toEntity(orderRequestDto);

                // Process each order item, check inventory, and update stock
                for (OrderItemRequestDto itemDto : orderRequestDto.getItems()) {
                    Tea tea = teaRepository.findById(itemDto.getTeaId())
                            .orElseThrow(TeaNotFoundException::new);

                    // Check if we have enough stock
                    if (tea.getStockQuantity() < itemDto.getQuantity()) {
                        throw new InsufficientInventoryException("Not enough stock for tea: " + tea.getName() +
                                ". Available: " + tea.getStockQuantity() + ", Requested: " + itemDto.getQuantity());
                    }

                    // Reduce stock quantity
                    tea.setStockQuantity(tea.getStockQuantity() - itemDto.getQuantity());
                    teaRepository.save(tea);

                    // Create order item
                    OrderItem orderItem = OrderMapper.toOrderItemEntity(itemDto, tea);
                    order.addItem(orderItem);
                }

                // Try to save with the generated order number
                savedOrder = orderRepository.save(order);
            } catch (DataIntegrityViolationException e) {
                // This is likely a duplicate order number collision
                attempts++;
                if (attempts >= maxRetries) {
                    throw new RuntimeException("Failed to generate a unique order number after " + maxRetries + " attempts", e);
                }
                // Continue to retry with a new order number in the next iteration
            }
        }
        // Convert back to DTO and return
        return OrderMapper.toDto(savedOrder);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
        return OrderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDto> getOrdersByCustomerEmail(String email) {
        return orderRepository.findByCustomerEmail(email).stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDto> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status).stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDto> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate).stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatus(OrderStatusUpdateDto statusUpdateDto) {
        Order order = orderRepository.findById(statusUpdateDto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + statusUpdateDto.getOrderId()));

        OrderMapper.updateOrderStatus(order, statusUpdateDto);

        // If the order is canceled, restore inventory
        if (OrderStatus.CANCELLED.toString().equals(statusUpdateDto.getStatus())) {
            restoreInventoryForCanceledOrder(order);
        }

        Order updatedOrder = orderRepository.save(order);
        return OrderMapper.toDto(updatedOrder);
    }

    @Override
    @Transactional
    public OrderResponseDto cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        // Only allow cancellation of orders that are in PENDING or PROCESSING status
        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.PROCESSING) {
            throw new IllegalStateException("Cannot cancel order with status: " + order.getStatus());
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setLastUpdated(LocalDateTime.now());

        // Restore inventory for canceled order
        restoreInventoryForCanceledOrder(order);

        Order updatedOrder = orderRepository.save(order);
        return OrderMapper.toDto(updatedOrder);
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrder(UUID orderId, OrderRequestDto orderRequestDto) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        // Only allow updates for orders in PENDING status
        if (existingOrder.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Cannot update order with status: " + existingOrder.getStatus());
        }

        // Update basic order information
        OrderMapper.mergeFieldsForUpdate(existingOrder, orderRequestDto);

        // Handle updated order items if present
        if (orderRequestDto.getItems() != null && !orderRequestDto.getItems().isEmpty()) {
            // First restore inventory for existing items

            // First restore inventory for existing items
            restoreInventoryForCanceledOrder(existingOrder);

            // Clear existing items
            existingOrder.getItems().clear();

            // Add new items
            for (OrderItemRequestDto itemDto : orderRequestDto.getItems()) {
                Tea tea = teaRepository.findById(itemDto.getTeaId())
                        .orElseThrow(TeaNotFoundException::new);

                // Check if we have enough stock
                if (tea.getStockQuantity() < itemDto.getQuantity()) {
                    throw new InsufficientInventoryException("Not enough stock for tea: " + tea.getName() +
                            ". Available: " + tea.getStockQuantity() + ", Requested: " + itemDto.getQuantity());
                }

                // Reduce stock quantity
                tea.setStockQuantity(tea.getStockQuantity() - itemDto.getQuantity());
                teaRepository.save(tea);

                // Create order item
                OrderItem orderItem = OrderMapper.toOrderItemEntity(itemDto, tea);
                existingOrder.addItem(orderItem);
            }
        }

        Order updatedOrder = orderRepository.save(existingOrder);
        return OrderMapper.toDto(updatedOrder);
    }

    /**
     * Helper method to restore inventory for canceled orders
     */
    private void restoreInventoryForCanceledOrder(Order order) {
        for (OrderItem item : order.getItems()) {
            Tea tea = item.getTea();
            tea.setStockQuantity(tea.getStockQuantity() + item.getQuantity());
            teaRepository.save(tea);
        }
    }
}
