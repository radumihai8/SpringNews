package com.unibuc.newsapp.service;

import com.unibuc.newsapp.dto.ArticleDTO;
import com.unibuc.newsapp.entity.Article;
import com.unibuc.newsapp.entity.ArticleCategory;
import com.unibuc.newsapp.entity.Category;
import com.unibuc.newsapp.repository.ArticleCategoryRepository;
import com.unibuc.newsapp.repository.ArticleRepository;
import com.unibuc.newsapp.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @Transactional
    public Article createArticle(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setPublishDate(articleDTO.getPublishDate());

        Set<ArticleCategory> articleCategories = new HashSet<>();
        for (Long categoryId : articleDTO.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setArticle(article);
            articleCategory.setCategory(category);
            articleCategories.add(articleCategory);
        }

        article.setArticleCategories(articleCategories);

        return articleRepository.save(article);
    }

    @Transactional
    public Article updateArticle(Long id, ArticleDTO articleDTO) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setPublishDate(articleDTO.getPublishDate());

        // Modify the existing collection
        article.getArticleCategories().clear();
        for (Long categoryId : articleDTO.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
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
