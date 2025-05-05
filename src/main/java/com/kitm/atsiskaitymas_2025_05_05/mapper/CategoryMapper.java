package com.kitm.atsiskaitymas_2025_05_05.mapper;

import com.kitm.atsiskaitymas_2025_05_05.dto.CategoryRequestDTO;
import com.kitm.atsiskaitymas_2025_05_05.dto.CatergoryResponseDTO;
import com.kitm.atsiskaitymas_2025_05_05.entity.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryRequestDTO dto)
    {
        Category category = new Category();

        category.setName(dto.getName());

        return category;
    }

    public static CatergoryResponseDTO toResponse(Category category)
    {
        CatergoryResponseDTO dto = new CatergoryResponseDTO();

        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setUserId(category.getUser().getId());

        return dto;
    }
}
