package com.unibuc.newsapp.controller;

import com.unibuc.newsapp.dto.CommentDTO;
import com.unibuc.newsapp.entity.Comment;
import com.unibuc.newsapp.entity.User;
import com.unibuc.newsapp.exceptions.ResourceNotFoundException;
import com.unibuc.newsapp.repository.UserRepository;
import com.unibuc.newsapp.service.CommentService;
import com.unibuc.newsapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @Mock
    private Principal principal;

    @InjectMocks
    private CommentController commentController;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCommentToArticle() {
        Long articleId = 1L;
        String content = "Test Comment";
        CommentDTO commentDTO = new CommentDTO();
        User user = new User();
        user.setUsername("testUser");

        when(principal.getName()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(commentService.addCommentToArticle(eq(articleId), any(Comment.class), eq(user))).thenReturn(commentDTO);

        ResponseEntity<CommentDTO> response = commentController.addCommentToArticle(articleId, content, principal);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    public void testGetCommentById() {
        Long commentId = 1L;
        CommentDTO commentDTO = new CommentDTO();

        when(commentService.getCommentById(commentId)).thenReturn(Optional.of(commentDTO));

        ResponseEntity<CommentDTO> response = commentController.getCommentById(commentId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetCommentByIdNotFound() {
        Long commentId = 1L;

        when(commentService.getCommentById(commentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commentController.getCommentById(commentId));
    }

    @Test
    public void testGetAllComments() {
        List<CommentDTO> comments = Arrays.asList(new CommentDTO());

        when(commentService.getAllComments()).thenReturn(comments);

        ResponseEntity<List<CommentDTO>> response = commentController.getAllComments();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testGetCommentsByArticleId() {
        Long articleId = 1L;
        List<CommentDTO> comments = Arrays.asList(new CommentDTO());

        when(commentService.getCommentsByArticleId(articleId)).thenReturn(comments);

        ResponseEntity<List<CommentDTO>> response = commentController.getCommentsByArticleId(articleId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testUpdateComment() {
        Long commentId = 1L;
        CommentDTO commentDTO = new CommentDTO();
        CommentDTO updatedCommentDTO = new CommentDTO();

        when(principal.getName()).thenReturn("testUser");
        when(commentService.updateComment(eq(commentId), any(CommentDTO.class), any(User.class))).thenReturn(updatedCommentDTO);

        ResponseEntity<CommentDTO> response = commentController.updateComment(commentId, commentDTO, principal);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteComment() {
        Long commentId = 1L;

        when(principal.getName()).thenReturn("testUser");
        doNothing().when(commentService).deleteComment(eq(commentId), any(User.class));

        ResponseEntity<HttpStatus> response = commentController.deleteComment(commentId, principal);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
