package com.kitm.atsiskaitymas_2025_05_05.dto;

import jakarta.validation.constraints.NotBlank;

public class FilmRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private Double imdb;

    @NotBlank
    private Long category_id;

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotBlank String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }

    public @NotBlank Double getImdb() {
        return imdb;
    }

    public void setImdb(@NotBlank Double imdb) {
        this.imdb = imdb;
    }

    public @NotBlank Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(@NotBlank Long category_id) {
        this.category_id = category_id;
    }
}
