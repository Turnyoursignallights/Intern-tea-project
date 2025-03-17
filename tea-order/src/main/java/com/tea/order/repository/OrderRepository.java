package com.tea.order.repository;

import com.tea.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Basic CRUD operations are provided by JpaRepository
}