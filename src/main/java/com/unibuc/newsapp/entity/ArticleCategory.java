package com.unibuc.newsapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "article_category")
public class ArticleCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Constructors
    public ArticleCategory() {
    }

    public ArticleCategory(Article article, Category category) {
        this.article = article;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ArticleCategory{" +
                "id=" + id +
                ", article=" + (article != null ? article.getId() : null) +
                ", category=" + (category != null ? category.getId() : null) +
                '}';
    }
}
