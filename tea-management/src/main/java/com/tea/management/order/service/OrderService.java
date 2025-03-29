package com.tea.management.order.service;

import com.tea.management.order.dto.OrderRequestDto;
import com.tea.management.order.dto.OrderResponseDto;
import com.tea.management.order.dto.OrderStatusUpdateDto;
import com.tea.management.order.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    /**
     * Create a new order
     *
     * @param orderRequestDto Order creation request
     * @return Created order details
     */
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    /**
     * Get all orders
     *
     * @return List of all orders
     */
    List<OrderResponseDto> getAllOrders();

    /**
     * Get order by ID
     *
     * @param orderId Order UUID
     * @return Order details
     */
    OrderResponseDto getOrderById(UUID orderId);

    /**
     * Get orders by customer email
     *
     * @param email Customer email
     * @return List of orders for the customer
     */
    List<OrderResponseDto> getOrdersByCustomerEmail(String email);

    /**
     * Get orders by status
     *
     * @param status Order status
     * @return List of orders with the given status
     */
    List<OrderResponseDto> getOrdersByStatus(OrderStatus status);

    /**
     * Get orders between dates
     *
     * @param startDate Start date
     * @param endDate   End date
     * @return List of orders in the date range
     */
    List<OrderResponseDto> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Update order status
     *
     * @param statusUpdateDto Status update request
     * @return Updated order details
     */
    OrderResponseDto updateOrderStatus(OrderStatusUpdateDto statusUpdateDto);

    /**
     * Cancel an order
     *
     * @param orderId Order UUID
     * @return Updated order details
     */
    OrderResponseDto cancelOrder(UUID orderId);

    /**
     * Update an existing order
     *
     * @param orderId         Order UUID
     * @param orderRequestDto Order update request
     * @return Updated order details
     */
    OrderResponseDto updateOrder(UUID orderId, OrderRequestDto orderRequestDto);
}