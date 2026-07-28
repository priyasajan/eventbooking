package com.example.eventbooking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {

    @NotBlank(message = "Category name is required ")
    private String categoryName;

    @NotBlank(message = "Description is required")
    private String description;


}