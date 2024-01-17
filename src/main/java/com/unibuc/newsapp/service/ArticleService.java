package com.unibuc.newsapp.service;

import com.unibuc.newsapp.dto.CreateArticleDTO;
import com.unibuc.newsapp.exceptions.ResourceNotFoundException;
import com.unibuc.newsapp.dto.ArticleDTO;
import com.unibuc.newsapp.entity.Article;
import com.unibuc.newsapp.entity.ArticleCategory;
import com.unibuc.newsapp.entity.Category;
import com.unibuc.newsapp.repository.ArticleCategoryRepository;
import com.unibuc.newsapp.repository.ArticleRepository;
import com.unibuc.newsapp.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @Transactional
    public Article createArticle(CreateArticleDTO articleDTO) {
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setPublishDate(new Date());

        Set<ArticleCategory> articleCategories = new HashSet<>();
        for (Long categoryId : articleDTO.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setArticle(article);
            articleCategory.setCategory(category);
            articleCategories.add(articleCategory);
        }

        article.setArticleCategories(articleCategories);

        return articleRepository.save(article);
    }

    @Transactional
    public Article updateArticle(Long id, CreateArticleDTO articleDTO) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());

        // Modify the existing collection
        article.getArticleCategories().clear();
        for (Long categoryId : articleDTO.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setArticle(article);
            articleCategory.setCategory(category);
            article.getArticleCategories().add(articleCategory);
        }

        return articleRepository.save(article);
    }



    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
}
