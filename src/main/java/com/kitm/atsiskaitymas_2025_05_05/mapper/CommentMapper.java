package com.kitm.atsiskaitymas_2025_05_05.mapper;

import com.kitm.atsiskaitymas_2025_05_05.dto.CommentRequestDTO;
import com.kitm.atsiskaitymas_2025_05_05.dto.CommentResponseDTO;
import com.kitm.atsiskaitymas_2025_05_05.entity.Comment;
import com.kitm.atsiskaitymas_2025_05_05.entity.Film;

public class CommentMapper {

    public static Comment toEntity(CommentRequestDTO dto, Film film)
    {
        Comment comment = new Comment();

        comment.setText(dto.getText());

        comment.setFilm(film);

        return comment;
    }

    public static CommentResponseDTO toResponse(Comment comment)
    {
        CommentResponseDTO dto = new CommentResponseDTO();

        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setUser_id(comment.getUser().getId());
        dto.setFilm_id(comment.getFilm().getId());

        return dto;
    }
}
