package com.kitm.atsiskaitymas_2025_05_05.services;

import com.kitm.atsiskaitymas_2025_05_05.dao.CommentRepository;
import com.kitm.atsiskaitymas_2025_05_05.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository)
    {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAll()
    {
        return commentRepository.findAll();
    }

    public Comment findById(Long id)
    {
        Optional<Comment> result = commentRepository.findById(id);

        Comment comment = null;

        if (result.isPresent())
        {
            comment = result.get();
        }
        else
        {
            throw new RuntimeException("Did not find Comment by id -" + id);
        }

        return comment;
    }

    @Transactional
    public Comment save(Comment comment)
    {
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteById(Long id)
    {
        commentRepository.deleteById(id);
    }
}
