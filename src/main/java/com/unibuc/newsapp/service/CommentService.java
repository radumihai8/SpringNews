package com.unibuc.newsapp.service;

import com.unibuc.newsapp.controller.CommentController;
import com.unibuc.newsapp.dto.CommentDTO;
import com.unibuc.newsapp.entity.Article;
import com.unibuc.newsapp.entity.Comment;
import com.unibuc.newsapp.entity.User;
import com.unibuc.newsapp.repository.ArticleRepository;
import com.unibuc.newsapp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public CommentDTO addCommentToArticle(Long articleId, Comment comment, User user) {
        Logger logger = Logger.getLogger(CommentController.class.getName());
        logger.info("User: " + user);
        logger.info("ArticleId: " + articleId);
        comment.setArticle(articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found")));
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    public Optional<CommentDTO> getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        return Optional.of(convertToDTO(comment));
    }

    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    public CommentDTO updateComment(Long id, CommentDTO commentDTO, User currentUser) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().equals(currentUser)) {
            throw new RuntimeException("Unauthorized to edit this comment");
        }

        comment.setContent(commentDTO.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return convertToDTO(updatedComment);
    }

    public void deleteComment(Long id, User currentUser) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        if (!comment.getUser().equals(currentUser)) {
            throw new RuntimeException("Unauthorized to delete this comment");
        }
        commentRepository.delete(comment);
    }

    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setUserId(comment.getUser().getId());
        return dto;
    }

    public List<CommentDTO> getCommentsByArticleId(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
