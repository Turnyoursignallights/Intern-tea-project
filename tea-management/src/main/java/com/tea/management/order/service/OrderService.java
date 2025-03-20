package com.tea.management.order.service;

import com.tea.management.inventory.entity.Tea;
import com.tea.management.inventory.repository.TeaRepositoryJpa;
import com.tea.management.order.entity.Order;
import com.tea.management.order.entity.OrderItem;
import com.tea.management.order.entity.OrderStatus;
import com.tea.management.order.exception.OrderNotFoundException;
import com.tea.management.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final TeaRepositoryJpa teaRepository;

    public OrderService(OrderRepository orderRepository, TeaRepositoryJpa teaRepository) {
        this.orderRepository = orderRepository;
        this.teaRepository = teaRepository;
    }

    @Transactional
    public Order createOrder(Order order) {
        // Validate and process each item in the order
        for (OrderItem item : order.getItems()) {
            Tea tea = teaRepository.findById(item.getTea().getId())
                    .orElseThrow(() -> new RuntimeException("Tea not found with id: " + item.getTea().getId()));

            // Check inventory
            if (tea.getStockQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for tea: " + tea.getName());
            }

            // Update inventory
            tea.setStockQuantity(tea.getStockQuantity() - item.getQuantity());
            teaRepository.save(tea);

            // Set the tea and price
            item.setTea(tea);
            item.setPrice(tea.getSellPrice());
            item.setOrder(order);
        }

        // Calculate total
        double total = 0.0;
        for (OrderItem item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }

    public Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByCustomerEmail(String email) {
        return orderRepository.findByCustomerEmail(email);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> getOrdersByDateRange(LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByOrderDateBetween(start, end);
    }

    @Transactional
    public Order updateOrderStatus(UUID id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        // If cancelling an order, restore inventory
        if (status == OrderStatus.CANCELLED && order.getStatus() != OrderStatus.CANCELLED) {
            restoreInventory(order);
        }

        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        // Restore inventory if deleting an order that's not cancelled
        if (order.getStatus() != OrderStatus.CANCELLED) {
            restoreInventory(order);
        }

        orderRepository.delete(order);
    }

    private void restoreInventory(Order order) {
        for (OrderItem item : order.getItems()) {
            Tea tea = item.getTea();
            tea.setStockQuantity(tea.getStockQuantity() + item.getQuantity());
            teaRepository.save(tea);
        }
    }
}