package com.spring.rest.controller;

import com.spring.rest.repository.CommentRepository;
import com.spring.rest.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
