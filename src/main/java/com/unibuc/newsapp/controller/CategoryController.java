package com.unibuc.newsapp.controller;

import com.unibuc.newsapp.exceptions.ResourceNotFoundException;
import com.unibuc.newsapp.dto.CategoryDTO;
import com.unibuc.newsapp.entity.Category;
import com.unibuc.newsapp.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@SecurityRequirement(name = "Bearer")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody String title) {
        Category category = new Category();
        category.setName(title);
        Category newCategory = categoryService.addCategory(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        CategoryDTO categoryDTO = convertToDTO(category);
        return ResponseEntity.ok(categoryDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        //convert to DTO
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(category -> convertToDTO(category))
                .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(categoryDTOs);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody String name) {
        Category categoryDetails = new Category();
        categoryDetails.setName(name);
        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);

        CategoryDTO categoryDTO = convertToDTO(updatedCategory);

        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //convertToDTO method
    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }
}

