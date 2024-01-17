package com.unibuc.newsapp.dto;

import java.util.Date;
import java.util.Set;

public class CreateArticleDTO {
    private String title;
    private String content;
    private Set<Long> categoryIds;

    // Constructors
    public CreateArticleDTO() {
    }

    public CreateArticleDTO(String title, String content, Set<Long> categoryIds) {
        this.title = title;
        this.content = content;
        this.categoryIds = categoryIds;
    }

    // Getters

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Set<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategoryIds(Set<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
