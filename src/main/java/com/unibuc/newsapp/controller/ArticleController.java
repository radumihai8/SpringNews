package com.unibuc.newsapp.controller;

import com.unibuc.newsapp.dto.CreateArticleDTO;
import com.unibuc.newsapp.exceptions.ResourceNotFoundException;
import com.unibuc.newsapp.dto.ArticleDTO;
import com.unibuc.newsapp.entity.Article;
import com.unibuc.newsapp.service.ArticleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@SecurityRequirement(name = "Bearer")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody CreateArticleDTO articleDTO) {
        Article createdArticle = articleService.createArticle(articleDTO);
        ArticleDTO createdArticleDTO = convertToDTO(createdArticle);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdArticleDTO);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody CreateArticleDTO articleDTO) {
        Article updatedArticle = articleService.updateArticle(id, articleDTO);
        articleDTO.setContent(articleDTO.getContent());
        articleDTO.setTitle(articleDTO.getTitle());
        articleDTO.setCategoryIds(articleDTO.getCategoryIds());

        ArticleDTO updatedArticleDTO = convertToDTO(updatedArticle);
        return ResponseEntity.ok(updatedArticleDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        Article article = articleService.getArticleById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
        ArticleDTO articleDTO = convertToDTO(article);
        return ResponseEntity.ok(articleDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        List<ArticleDTO> articleDTOs = articles.stream()
                .map(article -> convertToDTO(article))
                .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(articleDTOs);
    }

    private ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setPublishDate(article.getPublishDate());

        //set category ids
        articleDTO.setCategoryIds(article.getArticleCategories().stream()
                .map(articleCategory -> articleCategory.getCategory().getId())
                .collect(java.util.stream.Collectors.toSet()));

        return articleDTO;
    }
}
