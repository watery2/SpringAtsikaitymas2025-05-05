package com.kitm.atsiskaitymas_2025_05_05.dao;

import com.kitm.atsiskaitymas_2025_05_05.entity.Category;
import com.kitm.atsiskaitymas_2025_05_05.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {
    Optional<Film> findByName(String name);
    List<Film> findByCategory(Category category);
}
