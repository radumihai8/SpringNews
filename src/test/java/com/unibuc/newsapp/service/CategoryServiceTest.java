package com.unibuc.newsapp.service;

import com.unibuc.newsapp.entity.Category;
import com.unibuc.newsapp.exceptions.ResourceNotFoundException;
import com.unibuc.newsapp.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addCategoryTest() {
        Category category = new Category();
        category.setName("Test Category");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.addCategory(category);

        assertNotNull(result);
        assertEquals("Test Category", result.getName());
    }

    @Test
    public void getCategoryByIdTest() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Test Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Optional<Category> result = categoryService.getCategoryById(categoryId);

        assertTrue(result.isPresent());
        assertEquals(categoryId, result.get().getId());
    }

    @Test
    public void getCategoryByIdNotFoundTest() {
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
    }

    @Test
    public void getAllCategoriesTest() {
        Category category = new Category();
        category.setName("Test Category");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));

        List<Category> result = categoryService.getAllCategories();

        assertFalse(result.isEmpty());
        assertEquals("Test Category", result.get(0).getName());
    }

    @Test
    public void updateCategoryTest() {
        Long categoryId = 1L;
        String newName = "Updated Category";
        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("Test Category");

        Category updatedCategoryDetails = new Category();
        updatedCategoryDetails.setName(newName);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenAnswer(i -> i.getArguments()[0]);

        Category result = categoryService.updateCategory(categoryId, updatedCategoryDetails);

        assertNotNull(result);
        assertEquals(newName, result.getName());
    }

    @Test
    public void deleteCategoryTest() {
        Long categoryId = 1L;

        doNothing().when(categoryRepository).deleteById(anyLong());

        categoryService.deleteCategory(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }
}
