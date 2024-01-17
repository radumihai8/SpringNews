package com.unibuc.newsapp.controller;

import com.unibuc.newsapp.exceptions.ResourceNotFoundException;
import com.unibuc.newsapp.dto.CommentDTO;
import com.unibuc.newsapp.entity.Comment;
import com.unibuc.newsapp.entity.User;
import com.unibuc.newsapp.repository.CommentRepository;
import com.unibuc.newsapp.repository.UserRepository;
import com.unibuc.newsapp.service.CommentService;
import com.unibuc.newsapp.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;


@RestController
@RequestMapping("/api/comments")
@SecurityRequirement(name = "Bearer")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;



    @PostMapping("/create/{articleId}/")
    public ResponseEntity<CommentDTO> addCommentToArticle(
            @PathVariable Long articleId,
            @RequestBody String content) {

        // Get the username from Security Context
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Logger logger = Logger.getLogger(CommentController.class.getName());
        logger.info("Username: " + username);
        //print authentication object
        logger.info("Authentication: " + SecurityContextHolder.getContext().getAuthentication());

        User user = userRepository.findByUsername(username);
        logger.info("User: " + user);

        Comment comment = new Comment();
        comment.setContent(content);

        CommentDTO newComment = commentService.addCommentToArticle(articleId, comment, user);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }


    @GetMapping("/read/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long commentId) {
        CommentDTO comment = commentService.getCommentById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/list/{articleId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByArticleId(@PathVariable Long articleId) {
        List<CommentDTO> comments = commentService.getCommentsByArticleId(articleId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentDTO commentDTO,
            Principal principal) {

        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        //The user validation happens in the service
        CommentDTO updatedComment = commentService.updateComment(commentId, commentDTO, user);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(
            @PathVariable Long commentId,
            Principal principal) {
        //The user validation happens in the service
        User user = userRepository.findByUsername(principal.getName());
        commentService.deleteComment(commentId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
