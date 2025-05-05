package com.kitm.atsiskaitymas_2025_05_05.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentRequestDTO {

    @NotBlank
    private String text;

    @NotBlank
    private Long film_id;

    public @NotBlank String getText() {
        return text;
    }

    public void setText(@NotBlank String text) {
        this.text = text;
    }

    public @NotBlank Long getFilm_id() {
        return film_id;
    }

    public void setFilm_id(@NotBlank Long film_id) {
        this.film_id = film_id;
    }
}
