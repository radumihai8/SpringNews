package com.unibuc.newsapp.repository;

import com.unibuc.newsapp.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
