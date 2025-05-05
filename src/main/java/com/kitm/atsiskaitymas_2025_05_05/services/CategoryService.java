package com.kitm.atsiskaitymas_2025_05_05.services;

import com.kitm.atsiskaitymas_2025_05_05.dao.CategoryRepository;
import com.kitm.atsiskaitymas_2025_05_05.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll()
    {
        return categoryRepository.findAll();
    }

    public Category findById(Long id)
    {
        Optional<Category> result = categoryRepository.findById(id);

        Category category = null;

        if (result.isPresent())
        {
            category = result.get();
        }
        else
        {
            throw new RuntimeException("Did not find Category by id -" + id);
        }

        return category;
    }

    @Transactional
    public Category save(Category category)
    {
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());

        if (existingCategory.isPresent())
        {
            throw new RuntimeException("Category already exists - " + category.getName());
        }

        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteById(Long id)
    {
        categoryRepository.deleteById(id);
    }
}
