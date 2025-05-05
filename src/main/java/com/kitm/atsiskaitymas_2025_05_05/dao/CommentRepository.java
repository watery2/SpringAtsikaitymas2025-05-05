package com.kitm.atsiskaitymas_2025_05_05.dao;

import com.kitm.atsiskaitymas_2025_05_05.entity.Category;
import com.kitm.atsiskaitymas_2025_05_05.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
