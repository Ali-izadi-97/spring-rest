package com.spring.rest;

import com.spring.rest.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RestApplicationTests {
    private static final String ROOT_URL = "http://localhost:8080";
    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testGetAllPosts() {
        ResponseEntity<Post[]> responseEntity = restTemplate.getForEntity(ROOT_URL + "/posts", Post[].class);
        List<Post> posts = Arrays.asList(responseEntity.getBody());
        assertNotNull(posts);
    }

    @Test
    public void testGetPostById() {
        Post post = restTemplate.getForObject(ROOT_URL + "/posts/1", Post.class);
        assertNotNull(post);
    }

    @Test
    public void testCreatePost() {
        Post post = new Post();
        post.setTitle("Exploring SpringBoot REST");
        post.setContent("SpringBoot is awesome!!");
        post.setCreatedOn(new Date());
        ResponseEntity<Post> postResponse =
                restTemplate.postForEntity(ROOT_URL + "/posts", post, Post.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdatePost()
    {
        int id = 1;
        Post oldPost = restTemplate.getForObject(ROOT_URL+"/posts/"+id, Post.class);
        Post post = restTemplate.getForObject(ROOT_URL+"/posts/"+id, Post.class);
        post.setContent("This my updated post1 content");
        post.setUpdatedOn(new Date());
        restTemplate.put(ROOT_URL+"/posts/"+id, post);
        Post updatedPost = restTemplate.getForObject(ROOT_URL+"/posts/"+id, Post.class);
        assertNotNull(updatedPost);
        assertNotEquals(updatedPost, oldPost);
    }
}
