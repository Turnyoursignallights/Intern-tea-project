package com.tea.demo.product.repository;

import com.tea.demo.product.Entity.Tea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeaRepositoryJpa extends JpaRepository<Tea,Integer> {
}
