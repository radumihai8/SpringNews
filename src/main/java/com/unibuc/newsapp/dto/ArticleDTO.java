package com.unibuc.newsapp.dto;

import java.util.Date;
import java.util.Set;

public class ArticleDTO {

    private Long id;
    private String title;
    private String content;
    private Date publishDate;
    private Set<Long> categoryIds; // Store category IDs

    // Constructors
    public ArticleDTO() {
    }

    public ArticleDTO(Long id, String title, String content, Date publishDate, Set<Long> categoryIds) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.categoryIds = categoryIds;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public Set<Long> getCategoryIds() {
        return categoryIds;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public void setCategoryIds(Set<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
