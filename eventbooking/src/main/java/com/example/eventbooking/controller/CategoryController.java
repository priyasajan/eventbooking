package com.example.eventbooking.controller;

import com.example.eventbooking.dto.CategoryRequestDTO;
import com.example.eventbooking.dto.CategoryResponseDTO;
import com.example.eventbooking.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;




    @PostMapping
    public CategoryResponseDTO saveCategory(
            @Valid @RequestBody CategoryRequestDTO request) {

        return categoryService.saveCategory(request);
    }



    @GetMapping
    public List<CategoryResponseDTO> getAllCategories(
            @RequestParam int page,
            @RequestParam int size) {

        return categoryService.getAllCategories(page, size);
    }



    @GetMapping("/{id}")
    public CategoryResponseDTO getCategoryById(@PathVariable Long id) {

        return categoryService.getCategoryById(id);
    }



    @PutMapping("/{id}")
    public CategoryResponseDTO updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDTO request) {

        return categoryService.updateCategory(id, request);
    }



    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);
    }

}