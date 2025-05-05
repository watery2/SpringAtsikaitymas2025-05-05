package com.kitm.atsiskaitymas_2025_05_05.dao;

import com.kitm.atsiskaitymas_2025_05_05.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
