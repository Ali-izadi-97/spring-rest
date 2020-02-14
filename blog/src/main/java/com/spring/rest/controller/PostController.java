package com.spring.rest.controller;

import com.spring.rest.exception.ResourceNotFoundException;
import com.spring.rest.model.Comment;
import com.spring.rest.model.Post;
import com.spring.rest.repository.CommentRepository;
import com.spring.rest.repository.PostRepository;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public PostController(PostRepository post,
                          CommentRepository comment) {
        this.postRepository = post;
        this.commentRepository = comment;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @GetMapping("")
    public List<Post> listPosts() {
        return postRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Post getPost(@PathVariable(name = "id") Integer id) {
        return postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("no post found with id=" + id)
        );
    }

    @PostMapping(value = "/{id}")
    public Post getPost(@PathVariable(name = "id") Integer id, @RequestBody Post post) {
        postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(("No post found with id=" + id))
        );
        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable("id") Integer id, @RequestBody
            Post post) {
        postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("No post found with id=" + id));
        return postRepository.save(post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable(name = "id") Integer id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(("No post found with id=" + id))
        );
        postRepository.deleteById(post.getId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/comments")
    public void createPostComment(@PathVariable("id") Integer id,
                                  @RequestBody Comment comment) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("No post found with id=" + id));
        post.getComments().add(comment);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public void deletePostComment(@PathVariable("postId") Integer postId,
                                  @PathVariable("commentId") Integer
                                          commentId) {
        commentRepository.deleteById(commentId);
    }
}
