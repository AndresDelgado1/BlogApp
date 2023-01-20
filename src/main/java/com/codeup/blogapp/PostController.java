package com.codeup.blogapp;

import com.codeup.blogapp.model.Post;
import com.codeup.blogapp.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private final PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }



    @GetMapping("/posts")
    public String allPosts(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "/posts/index";
    }




    @GetMapping("/posts/{id}")
    public String id(@PathVariable String id) {
        return "Hello " + id + "!";
    }


//    @GetMapping("/posts/create")
//    public String getCreate(Model model) {
//        model.addAttribute("post", new Post());
//        return "/posts/create";
//    }
    @GetMapping("/posts/create")
        public String getCreate() {
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@RequestParam(name="title") String title, @RequestParam(name="body") String body ){
        postDao.save(new Post(title, body));
        return "redirect:/posts";
    }
}
