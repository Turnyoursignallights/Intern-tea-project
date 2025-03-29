package com.tea.management.order.service;

import com.tea.management.inventory.entity.Tea;
import com.tea.management.inventory.service.TeaMapper;
import com.tea.management.order.dto.*;
import com.tea.management.order.entity.Order;
import com.tea.management.order.entity.OrderItem;
import com.tea.management.order.entity.OrderNumberGenerator;
import com.tea.management.order.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    /**
     * Converts an Order entity to an OrderResponseDto
     */
    public static OrderResponseDto toDto(Order order) {
        List<OrderItemResponseDto> itemDtos = order.getItems().stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());

        return new OrderResponseDto(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderDate(),
                order.getLastUpdated(),
                order.getStatus().toString(),
                order.getCustomerName(),
                order.getCustomerEmail(),
                order.getCustomerPhone(),
                order.getShippingAddress(),
                order.getPaymentMethod() != null ? order.getPaymentMethod().toString() : null,
                order.getDeliveryMethod(),
                order.getTrackingNumber(),
                order.getTotalAmount(),
                itemDtos
        );
    }

    /**
     * Converts an OrderItem entity to an OrderItemResponseDto
     */
    public static OrderItemResponseDto toDto(OrderItem orderItem) {
        return new OrderItemResponseDto(
                orderItem.getId(),  // Now directly passing the UUID
                orderItem.getOrder().getId(),  // Now directly passing the UUID
                TeaMapper.toDto(orderItem.getTea()),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }

    /**
     * Converts OrderRequestDto to Order entity
     */
    public static Order toEntity(OrderRequestDto orderRequestDto) {

        Order order = new Order();

        order.setOrderNumber(OrderNumberGenerator.generateOrderNumber());
        order.setCustomerName(orderRequestDto.getCustomerName());
        order.setCustomerEmail(orderRequestDto.getCustomerEmail());
        order.setCustomerPhone(orderRequestDto.getCustomerPhone());
        order.setShippingAddress(orderRequestDto.getShippingAddress());

        if (orderRequestDto.getPaymentMethod() != null) {
            try {
                order.setPaymentMethod(Order.PaymentMethod.valueOf(orderRequestDto.getPaymentMethod()));
            } catch (IllegalArgumentException e) {
                // Default to a safe value if the input doesn't match an enum value
                order.setPaymentMethod(Order.PaymentMethod.CREDIT_CARD);
            }
        }

        order.setDeliveryMethod(orderRequestDto.getDeliveryMethod());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setLastUpdated(LocalDateTime.now());

        // Initialize with empty items - items will be added separately
        order.setItems(new ArrayList<>());

        return order;
    }

    /**
     * Creates an OrderItem from an OrderItemRequestDto and a Tea entity
     */
    public static OrderItem toOrderItemEntity(OrderItemRequestDto itemDto, Tea tea) {
        OrderItem orderItem = new OrderItem();
        orderItem.setTea(tea);
        orderItem.setQuantity(itemDto.getQuantity());
        orderItem.setPrice(tea.getSellPrice());
        return orderItem;
    }

    /**
     * Updates an existing Order entity with data from OrderRequestDto
     */
    public static void mergeFieldsForUpdate(Order existingOrder, OrderRequestDto orderRequestDto) {
        // NOTE: We don't update the orderNumber when updating an order

        if (orderRequestDto.getCustomerName() != null) {
            existingOrder.setCustomerName(orderRequestDto.getCustomerName());
        }

        if (orderRequestDto.getCustomerEmail() != null) {
            existingOrder.setCustomerEmail(orderRequestDto.getCustomerEmail());
        }

        if (orderRequestDto.getCustomerPhone() != null) {
            existingOrder.setCustomerPhone(orderRequestDto.getCustomerPhone());
        }

        if (orderRequestDto.getShippingAddress() != null) {
            existingOrder.setShippingAddress(orderRequestDto.getShippingAddress());
        }

        if (orderRequestDto.getPaymentMethod() != null) {
            try {
                existingOrder.setPaymentMethod(Order.PaymentMethod.valueOf(orderRequestDto.getPaymentMethod()));
            } catch (IllegalArgumentException e) {
                // Ignore invalid payment method to avoid overwriting with invalid data
            }
        }

        if (orderRequestDto.getDeliveryMethod() != null) {
            existingOrder.setDeliveryMethod(orderRequestDto.getDeliveryMethod());
        }

        // Don't update items here - that should be handled separately to manage
        // the bidirectional relationship properly
        existingOrder.setLastUpdated(LocalDateTime.now());
    }

    /**
     * Updates an Order's status from OrderStatusUpdateDto
     */
    public static void updateOrderStatus(Order order, OrderStatusUpdateDto statusUpdateDto) {
        try {
            OrderStatus newStatus = OrderStatus.valueOf(statusUpdateDto.getStatus());
            order.setStatus(newStatus);
            order.setLastUpdated(LocalDateTime.now());
        } catch (IllegalArgumentException e) {
            // Handle invalid status value
            throw new IllegalArgumentException("Invalid order status: " + statusUpdateDto.getStatus());
        }
    }
}