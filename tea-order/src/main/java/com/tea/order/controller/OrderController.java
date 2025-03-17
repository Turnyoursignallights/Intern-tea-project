package com.tea.order.controller;

import com.tea.order.entity.Order;
import com.tea.order.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody OrderRequest request) {
        Order order = new Order();
        order.setId(request.getTeaId());
        order.setTeaId(request.getTeaId());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(request.getUnitPrice() * request.getQuantity());
        order.setOrderDate(LocalDateTime.now());

        return orderRepository.save(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

//    @GetMapping("/{id}")
//    public Order getOrderById(@PathVariable Integer id) {
//        return orderRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
//    }
}

class OrderRequest {
    private Integer teaId;
    private Integer quantity;
    private Double unitPrice;

    // Getters and setters
    public Integer getTeaId() { return teaId; }
    public void setTeaId(Integer teaId) { this.teaId = teaId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
}