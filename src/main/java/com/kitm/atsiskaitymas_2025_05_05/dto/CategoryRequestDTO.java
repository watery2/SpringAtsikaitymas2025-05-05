package com.kitm.atsiskaitymas_2025_05_05.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDTO {

    @NotBlank(message = "can not be blank")
    String name;

    public @NotBlank(message = "can not be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "can not be blank") String name) {
        this.name = name;
    }
}
