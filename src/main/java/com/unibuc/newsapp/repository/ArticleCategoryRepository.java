package com.unibuc.newsapp.repository;

import com.unibuc.newsapp.entity.Article;
import com.unibuc.newsapp.entity.ArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;


import java.util.List;

@Repository
public interface ArticleCategoryRepository extends JpaRepository<Article, Long> {

}
