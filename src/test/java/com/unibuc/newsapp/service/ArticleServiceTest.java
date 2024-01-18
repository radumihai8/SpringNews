package com.unibuc.newsapp.service;

import com.unibuc.newsapp.dto.CreateArticleDTO;
import com.unibuc.newsapp.entity.Article;
import com.unibuc.newsapp.entity.Category;
import com.unibuc.newsapp.repository.ArticleRepository;
import com.unibuc.newsapp.repository.CategoryRepository;
import com.unibuc.newsapp.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ArticleService articleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createArticleTest() {
        CreateArticleDTO articleDTO = new CreateArticleDTO();
        articleDTO.setTitle("Test Title");
        articleDTO.setContent("Test Content");
        articleDTO.setCategoryIds(Collections.singleton(1L));

        Category category = new Category();
        category.setId(1L);
        category.setName("Category1");

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(articleRepository.save(any(Article.class))).thenAnswer(i -> i.getArguments()[0]);

        Article result = articleService.createArticle(articleDTO);

        assertNotNull(result);
        assertEquals(articleDTO.getTitle(), result.getTitle());
        assertFalse(result.getArticleCategories().isEmpty());
    }

    @Test
    public void createArticleCategoryNotFoundTest() {
        CreateArticleDTO articleDTO = new CreateArticleDTO();
        articleDTO.setCategoryIds(Collections.singleton(1L));

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> articleService.createArticle(articleDTO));
    }

    @Test
    public void updateArticleTest() {
        Long articleId = 1L;
        CreateArticleDTO articleDTO = new CreateArticleDTO();
        articleDTO.setTitle("Updated Title");
        articleDTO.setContent("Updated Content");
        articleDTO.setCategoryIds(Collections.singleton(2L));

        Category category = new Category();
        category.setId(2L);
        category.setName("Category2");

        Article existingArticle = new Article();
        existingArticle.setId(articleId);
        existingArticle.setArticleCategories(new HashSet<>());
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(existingArticle));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(articleRepository.save(any(Article.class))).thenAnswer(i -> i.getArguments()[0]);

        Article result = articleService.updateArticle(articleId, articleDTO);

        assertNotNull(result);
        assertEquals(articleDTO.getTitle(), result.getTitle());
        assertFalse(result.getArticleCategories().isEmpty());
    }


    @Test
    public void updateArticleNotFoundTest() {
        Long articleId = 1L;
        CreateArticleDTO articleDTO = new CreateArticleDTO();

        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> articleService.updateArticle(articleId, articleDTO));
    }

    @Test
    public void deleteArticleTest() {
        doNothing().when(articleRepository).deleteById(anyLong());
        articleService.deleteArticle(1L);
        verify(articleRepository, times(1)).deleteById(1L);
    }

    @Test
    public void getArticleByIdTest() {
        Long articleId = 1L;
        Article article = new Article();
        article.setId(articleId);
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        Optional<Article> result = articleService.getArticleById(articleId);

        assertTrue(result.isPresent());
        assertEquals(articleId, result.get().getId());
    }

    @Test
    public void getAllArticlesTest() {
        Article article = new Article();
        article.setTitle("Test Title");

        when(articleRepository.findAll()).thenReturn(Collections.singletonList(article));

        List<Article> result = articleService.getAllArticles();

        assertFalse(result.isEmpty());
        assertEquals("Test Title", result.iterator().next().getTitle());
    }
}
