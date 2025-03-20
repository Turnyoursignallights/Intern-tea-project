package com.tea.management.inventory.repository;

import com.tea.management.inventory.entity.Tea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeaRepositoryJpa extends JpaRepository<Tea, Integer> {
}
