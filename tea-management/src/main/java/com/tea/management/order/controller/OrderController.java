package com.tea.management.order.controller;

import com.tea.management.order.dto.OrderRequestDto;
import com.tea.management.order.dto.OrderResponseDto;
import com.tea.management.order.dto.OrderStatusUpdateDto;
import com.tea.management.order.entity.OrderStatus;
import com.tea.management.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }

    @GetMapping
    public List<OrderResponseDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto getOrderById(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/customer")
    public List<OrderResponseDto> getOrdersByCustomerEmail(@RequestParam String email) {
        return orderService.getOrdersByCustomerEmail(email);
    }

    @GetMapping("/status/{status}")
    public List<OrderResponseDto> getOrdersByStatus(@PathVariable OrderStatus status) {
        return orderService.getOrdersByStatus(status);
    }

    @GetMapping("/date-range")
    public List<OrderResponseDto> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return orderService.getOrdersByDateRange(startDate, endDate);
    }

    @PatchMapping("/status")
    public OrderResponseDto updateOrderStatus(@Valid @RequestBody OrderStatusUpdateDto statusUpdateDto) {
        return orderService.updateOrderStatus(statusUpdateDto);
    }

    @PatchMapping("/{orderId}/cancel")
    public OrderResponseDto cancelOrder(@PathVariable UUID orderId) {
        return orderService.cancelOrder(orderId);
    }

    @PutMapping("/{orderId}")
    public OrderResponseDto updateOrder(
            @PathVariable UUID orderId,
            @Valid @RequestBody OrderRequestDto orderRequestDto) {
        return orderService.updateOrder(orderId, orderRequestDto);
    }
}