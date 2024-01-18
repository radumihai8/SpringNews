package com.unibuc.newsapp.controller;

import com.unibuc.newsapp.dto.CategoryDTO;
import com.unibuc.newsapp.entity.Category;
import com.unibuc.newsapp.exceptions.ResourceNotFoundException;
import com.unibuc.newsapp.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addCategoryTest() {
        String title = "Test Category";
        Category category = new Category();
        category.setName(title);

        when(categoryService.addCategory(any(Category.class))).thenReturn(category);

        ResponseEntity<Category> response = categoryController.addCategory(title);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(title, response.getBody().getName());
    }

    @Test
    public void getCategoryByIdTest() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Test Category");

        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(category));

        ResponseEntity<CategoryDTO> response = categoryController.getCategoryById(categoryId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryId, response.getBody().getId());
    }

    @Test
    public void getCategoryByIdNotFoundTest() {
        Long categoryId = 1L;

        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryController.getCategoryById(categoryId));
    }

    @Test
    public void getAllCategoriesTest() {
        Category category = new Category();
        category.setName("Test Category");

        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(category));

        ResponseEntity<List<CategoryDTO>> response = categoryController.getAllCategories();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void updateCategoryTest() {
        Long categoryId = 1L;
        String newName = "Updated Category";
        Category updatedCategory = new Category();
        updatedCategory.setId(categoryId);
        updatedCategory.setName(newName);

        when(categoryService.updateCategory(anyLong(), any(Category.class))).thenReturn(updatedCategory);

        ResponseEntity<Category> response = categoryController.updateCategory(categoryId, newName);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newName, response.getBody().getName());
    }

    @Test
    public void deleteCategoryTest() {
        Long categoryId = 1L;

        doNothing().when(categoryService).deleteCategory(anyLong());

        ResponseEntity<HttpStatus> response = categoryController.deleteCategory(categoryId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
