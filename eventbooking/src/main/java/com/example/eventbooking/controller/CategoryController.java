
        package com.example.eventbooking.controller;

import com.example.eventbooking.dto.CategoryRequestDTO;
import com.example.eventbooking.dto.CategoryResponseDTO;
import com.example.eventbooking.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


   //admin create category

    @Operation(
            summary = "Create a category",
            description = "Admin can create a new event category.",
            tags = {"Admin"}
    )
    @PostMapping
    public CategoryResponseDTO saveCategory(
            @Valid @RequestBody CategoryRequestDTO request) {

        return categoryService.saveCategory(request);
    }


   //get all category

    @Operation(
            summary = "Get all categories",
            description = "Admin can view all event categories.",
            tags = {"Admin"}
    )
    @GetMapping
    public List<CategoryResponseDTO> getAllCategories(
            @RequestParam int page,
            @RequestParam int size) {

        return categoryService.getAllCategories(page, size);
    }



    @Operation(
            summary = "Get category by ID",
            description = "Admin can view a specific category.",
            tags = {"Admin"}
    )
    @GetMapping("/{id}")
    public CategoryResponseDTO getCategoryById(
            @PathVariable Long id) {

        return categoryService.getCategoryById(id);
    }




    @Operation(
            summary = "Update category",
            description = "Admin can update an existing category.",
            tags = {"Admin"}
    )
    @PutMapping("/{id}")
    public CategoryResponseDTO updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDTO request) {

        return categoryService.updateCategory(id, request);
    }


   //delete category

    @Operation(
            summary = "Delete category",
            description = "Admin can delete an existing category.",
            tags = {"Admin"}
    )
    @DeleteMapping("/{id}")
    public void deleteCategory(
            @PathVariable Long id) {

        categoryService.deleteCategory(id);
    }
}
