package com.unibuc.newsapp.controller;

import com.unibuc.newsapp.dto.CreateArticleDTO;
import com.unibuc.newsapp.dto.ArticleDTO;
import com.unibuc.newsapp.entity.Article;
import com.unibuc.newsapp.entity.ArticleCategory;
import com.unibuc.newsapp.entity.Category;
import com.unibuc.newsapp.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ArticleControllerTest {

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createArticleTest() {
        CreateArticleDTO createArticleDTO = new CreateArticleDTO();
        createArticleDTO.setTitle("Test Title");
        createArticleDTO.setContent("Test Content");
        createArticleDTO.setCategoryIds(Collections.singleton(1L));

        Article article = new Article();
        article.setTitle(createArticleDTO.getTitle());
        article.setContent(createArticleDTO.getContent());
        article.setArticleCategories(new HashSet<>());

        when(articleService.createArticle(any(CreateArticleDTO.class))).thenReturn(article);

        ResponseEntity<ArticleDTO> response = articleController.createArticle(createArticleDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createArticleDTO.getTitle(), response.getBody().getTitle());
    }

    @Test
    public void testUpdateArticle() {
        CreateArticleDTO updateArticleDTO = new CreateArticleDTO();
        updateArticleDTO.setTitle("Updated Title");
        updateArticleDTO.setContent("Updated Content");
        updateArticleDTO.setCategoryIds(Collections.singleton(1L));

        Article updatedArticle = new Article();
        updatedArticle.setTitle(updateArticleDTO.getTitle());
        updatedArticle.setContent(updateArticleDTO.getContent());
        updatedArticle.setArticleCategories(new HashSet<>());

        when(articleService.updateArticle(anyLong(), any(CreateArticleDTO.class))).thenReturn(updatedArticle);

        ResponseEntity<ArticleDTO> response = articleController.updateArticle(1L, updateArticleDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updateArticleDTO.getTitle(), response.getBody().getTitle());
    }

    @Test
    public void testDeleteArticle() {
        doNothing().when(articleService).deleteArticle(anyLong());
        ResponseEntity<?> response = articleController.deleteArticle(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetArticleById() {
        Article article = new Article();
        article.setTitle("Test Title");
        article.setContent("Test Content");

        Category category = new Category();
        category.setName("Test Category");

        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setCategory(category);
        articleCategory.setArticle(article);

        article.setArticleCategories(Collections.singleton(articleCategory));

        when(articleService.getArticleById(anyLong())).thenReturn(Optional.of(article));
        ResponseEntity<ArticleDTO> response = articleController.getArticleById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(article.getTitle(), response.getBody().getTitle());
    }

    @Test
    public void testGetAllArticles() {
        Article article = new Article();
        article.setTitle("Test Title");
        article.setContent("Test Content");

        Category category = new Category();
        category.setName("Test Category");

        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setCategory(category);
        articleCategory.setArticle(article);

        article.setArticleCategories(Collections.singleton(articleCategory));

        when(articleService.getAllArticles()).thenReturn(Arrays.asList(article));
        ResponseEntity<List<ArticleDTO>> response = articleController.getAllArticles();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(article.getTitle(), response.getBody().get(0).getTitle());
    }

}
