package com.kitm.atsiskaitymas_2025_05_05.mapper;

import com.kitm.atsiskaitymas_2025_05_05.dto.CommentResponseDTO;
import com.kitm.atsiskaitymas_2025_05_05.dto.FilmRequestDTO;
import com.kitm.atsiskaitymas_2025_05_05.dto.FilmResponseDTO;
import com.kitm.atsiskaitymas_2025_05_05.entity.Category;
import com.kitm.atsiskaitymas_2025_05_05.entity.Film;

import java.util.List;
import java.util.stream.Collectors;

public class FilmMapper {

    public static Film toEntity(FilmRequestDTO dto, Category category)
    {
        Film film = new Film();

        film.setName(dto.getName());
        film.setCategory(category);
        film.setImdb(dto.getImdb());
        film.setDescription(dto.getDescription());

        return film;
    }

    public static FilmResponseDTO mapToDto(Film film)
    {
        FilmResponseDTO dto = new FilmResponseDTO();

        dto.setId(film.getId());
        dto.setName(film.getName());
        dto.setDescription(film.getDescription());
        dto.setImdb(film.getImdb());
        dto.setUserId(film.getUser().getId());
        dto.setCategory(film.getCategory().getId());

        List<CommentResponseDTO> commentResponseDTOList = film.getComments().stream().map(CommentMapper::toResponse).collect(Collectors.toList());

        dto.setComments(commentResponseDTOList);

        return dto;
    }
}
