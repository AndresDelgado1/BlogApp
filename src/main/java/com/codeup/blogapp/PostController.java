package com.codeup.blogapp;

import com.codeup.blogapp.model.Post;
import com.codeup.blogapp.model.User;
import com.codeup.blogapp.repositories.PostRepository;
import com.codeup.blogapp.repositories.UserRepository;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserRepository userDao;

    public PostController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
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





//    👇---------------------------no form model binding-------------------------👇

    @GetMapping("/posts/create")
    public String getForm() {
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@RequestParam(name="title") String title, @RequestParam(name="body") String body ){
//        postDao.save(new Post(title, body));
//        return "redirect:/posts";

        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        User user = userDao.getReferenceById(1L);
        post.setUser(user);
        postDao.save(post);
        return "redirect:/posts";
    }


//    👇--------------------------form model binding----------------------------👇

//    @GetMapping("/posts/create")
//    public String getForm(Model model){
//        model.addAttribute("post", new Post());
//        return "posts/create";
//    }
//
//    @PostMapping("/posts/create")
//    public String createPost(@ModelAttribute Post post){
//        User user = userDao.getReferenceById(1L);
//        post.setUser(user);
//        postDao.save(post);
//        return "redirect:/posts";
//    }

}

