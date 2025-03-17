package com.tea.inventory.product.repository;

import com.tea.inventory.product.entity.Tea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeaRepositoryJpa extends JpaRepository<Tea, Integer> {
}
