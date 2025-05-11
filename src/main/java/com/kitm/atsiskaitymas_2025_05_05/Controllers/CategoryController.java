package com.kitm.atsiskaitymas_2025_05_05.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitm.atsiskaitymas_2025_05_05.dto.CategoryRequestDTO;
import com.kitm.atsiskaitymas_2025_05_05.dto.CatergoryResponseDTO;
import com.kitm.atsiskaitymas_2025_05_05.entity.Category;
import com.kitm.atsiskaitymas_2025_05_05.entity.User;
import com.kitm.atsiskaitymas_2025_05_05.mapper.CategoryMapper;
import com.kitm.atsiskaitymas_2025_05_05.security.services.UserDetailsImpl;
import com.kitm.atsiskaitymas_2025_05_05.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatergoryResponseDTO> addCategory(@Valid @RequestBody CategoryRequestDTO dto)
    {
        Category category = CategoryMapper.toEntity(dto);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = new User(userDetails.getUsername(), userDetails.getPassword());
        user.setId(userDetails.getId());

        category.setUser(user);

        Category categorySaved = categoryService.save(category);

        return ResponseEntity.ok(CategoryMapper.toResponse(categorySaved));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, Object>> findAll()
    {

        List<CatergoryResponseDTO> categorys = categoryService.findAll().stream().map(CategoryMapper::toResponse).collect(Collectors.toList());

        Map<String, Object> response = Map.of("status", "success",
                "results", categorys.size(),
                "data", categorys);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CatergoryResponseDTO> getCategory(@PathVariable Long id)
    {
        Optional<Category> result = Optional.ofNullable(categoryService.findById(id));

        return result.map(category -> ResponseEntity.ok(CategoryMapper.toResponse(category))).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id)
    {
        Category category = categoryService.findById(id);

        if (category == null)
        {
            return ResponseEntity.notFound().build();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        categoryService.deleteById(id);

        return ResponseEntity.ok("Deleted hotel id - " + id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatergoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO dto)
    {
        Category category = categoryService.findById(id);

        if (category == null)
        {
            return ResponseEntity.notFound().build();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Category updatedCategory = CategoryMapper.toEntity(dto);
        updatedCategory.setId(id);

        Category saveCategory = categoryService.save(updatedCategory);

        return ResponseEntity.ok(CategoryMapper.toResponse(saveCategory));
    }
}
