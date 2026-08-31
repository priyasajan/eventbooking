package com.example.eventbooking.service;

import com.example.eventbooking.dto.CategoryRequestDTO;
import com.example.eventbooking.dto.CategoryResponseDTO;
import com.example.eventbooking.entity.Category;
import com.example.eventbooking.exception.CategoryAlreadyExistsException;
import com.example.eventbooking.exception.CategoryNotFoundException;
import com.example.eventbooking.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    // CREATE CATEGORY


    public CategoryResponseDTO saveCategory(CategoryRequestDTO request) {

        // Check duplicate category
        if (categoryRepository.existsByCategoryName(request.getCategoryName())) {
            throw new CategoryAlreadyExistsException("Category already exists");
        }

        // Convert RequestDTO -> Entity
        Category category = mapToEntity(request);

        // Save category
        Category savedCategory = categoryRepository.save(category);

        // Return ResponseDTO
        return mapToResponseDTO(savedCategory);
    }

    // GET ALL CATEGORIES

    public List<CategoryResponseDTO> getAllCategories(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        List<Category> categories = categoryPage.getContent();

        List<CategoryResponseDTO> responseList = new ArrayList<>();

        for (Category category : categories) {
            responseList.add(mapToResponseDTO(category));
        }

        return responseList;
    }


    // GET CATEGORY BY ID


    public CategoryResponseDTO getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        return mapToResponseDTO(category);
    }



    // UPDATE CATEGORY

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());

        Category updatedCategory = categoryRepository.save(category);

        return mapToResponseDTO(updatedCategory);
    }


    // DELETE CATEGORY

    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        categoryRepository.delete(category);
    }


    // REQUEST DTO -> ENTITY


    private Category mapToEntity(CategoryRequestDTO request) {

        Category category = new Category();

        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());

        return category;


    }


    // ENTITY -> RESPONSE DTO

    private CategoryResponseDTO mapToResponseDTO(Category category) {

        CategoryResponseDTO response = new CategoryResponseDTO();

        response.setId(category.getId());
        response.setCategoryName(category.getCategoryName());
        response.setDescription(category.getDescription());

        return response;
    }

}