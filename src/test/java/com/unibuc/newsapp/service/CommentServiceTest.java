package com.unibuc.newsapp.service;

import com.unibuc.newsapp.dto.CommentDTO;
import com.unibuc.newsapp.entity.Article;
import com.unibuc.newsapp.entity.Comment;
import com.unibuc.newsapp.entity.User;
import com.unibuc.newsapp.exceptions.ResourceNotFoundException;
import com.unibuc.newsapp.repository.ArticleRepository;
import com.unibuc.newsapp.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addCommentToArticleTest() {
        Long articleId = 1L;
        Comment comment = new Comment();
        User user = new User();
        user.setId(1L);
        comment.setUser(user);
        Article article = new Article();
        article.setId(articleId);

        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDTO result = commentService.addCommentToArticle(articleId, comment, user);

        assertNotNull(result);
    }

    @Test
    public void addCommentToArticleNotFoundTest() {
        Long articleId = 1L;
        Comment comment = new Comment();
        User user = new User();

        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commentService.addCommentToArticle(articleId, comment, user));
    }

    @Test
    public void getCommentByIdTest() {
        Long commentId = 1L;
        Long articleId = 1L;
        Comment comment = new Comment();
        User user = new User();
        user.setId(1L);
        comment.setUser(user);
        Article article = new Article();
        article.setId(articleId);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        Optional<CommentDTO> result = commentService.getCommentById(commentId);

        assertTrue(result.isPresent());
    }

    @Test
    public void getAllCommentsTest() {
        Comment comment = new Comment();
        Long articleId = 1L;
        User user = new User();
        user.setId(1L);
        comment.setUser(user);
        Article article = new Article();
        article.setId(articleId);

        when(commentRepository.findAll()).thenReturn(Arrays.asList(comment));

        List<CommentDTO> result = commentService.getAllComments();

        assertFalse(result.isEmpty());
    }

    @Test
    public void updateCommentTest() {
        Long commentId = 1L;
        User currentUser = new User();
        Comment existingComment = new Comment();
        existingComment.setUser(currentUser);
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("Updated Content");

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(any(Comment.class))).thenAnswer(i -> i.getArguments()[0]);

        CommentDTO result = commentService.updateComment(commentId, commentDTO, currentUser);

        assertNotNull(result);
        assertEquals("Updated Content", result.getContent());
    }

    @Test
    public void deleteCommentTest() {
        Long commentId = 1L;
        User currentUser = new User();
        Comment existingComment = new Comment();
        existingComment.setUser(currentUser);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
        doNothing().when(commentRepository).delete(any(Comment.class));

        commentService.deleteComment(commentId, currentUser);

        verify(commentRepository, times(1)).delete(existingComment);
    }

    @Test
    public void getCommentsByArticleIdTest() {
        Long articleId = 1L;
        Comment comment = new Comment();
        User user = new User();
        user.setId(1L);
        comment.setUser(user);
        Article article = new Article();
        article.setId(articleId);
        comment.setArticle(article);
        when(commentRepository.findByArticleId(articleId)).thenReturn(Arrays.asList(comment));

        List<CommentDTO> result = commentService.getCommentsByArticleId(articleId);

        assertFalse(result.isEmpty());
    }
}
