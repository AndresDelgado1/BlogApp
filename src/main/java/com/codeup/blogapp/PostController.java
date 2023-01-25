package com.codeup.blogapp;

import com.codeup.blogapp.model.Post;
import com.codeup.blogapp.model.User;
import com.codeup.blogapp.repositories.PostRepository;
import com.codeup.blogapp.repositories.UserRepository;
import com.codeup.blogapp.service.EmailService;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserRepository userDao;
    private final EmailService emailService;

    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }







    @GetMapping("/posts")
    public String allPosts(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "/posts/index";
    }




    @GetMapping("/posts/{id}")
    public String id(@PathVariable long id, Model model) {
        Post post = postDao.getReferenceById(id);
        model.addAttribute("title", post.getTitle());
        model.addAttribute("body", post.getBody());
        return "/posts/show";
    }





//    👇---------------------------no form model binding-------------------------👇

//    @GetMapping("/posts/create")
//    public String getForm() {
//        return "/posts/create";
//    }

//    @PostMapping("/posts/create")
//    public String createPost(@RequestParam(name="title") String title, @RequestParam(name="body") String body ){
////        postDao.save(new Post(title, body));
////        return "redirect:/posts";
//
//        Post post = new Post();
//        post.setTitle(title);
//        post.setBody(body);
//        User user = userDao.getReferenceById(1L);
//        post.setUser(user);
//        postDao.save(post);
//        return "redirect:/posts";
//    }


//    👇--------------------------form model binding----------------------------👇

    @GetMapping("/posts/create")
    public String getForm(Model model){
        model.addAttribute("post", new Post());
        return "posts/create";
    }

//    @PostMapping("/posts/create")
//    public String createPost(@ModelAttribute Post post){
//        User user = userDao.getReferenceById(1L);
//        post.setUser(user);
//
//        emailService.prepareAndSend(post,  post.getTitle(),  post.getBody());
//
//        postDao.save(post);
//        return "redirect:/posts";
//    }
@PostMapping(path = "/posts/create")
public String postCreate(@ModelAttribute Post createdPost){
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    createdPost.setUser(user);
    emailService.prepareAndSend(createdPost, "Your latest blog post: " + createdPost.getTitle(), "This is the body of your post!" + createdPost.getBody());
    postDao.save(createdPost);
    return "redirect:/posts";
}



    @GetMapping(path = "/posts/edit/{id}")
    public String getEdit(@PathVariable long id, Model model){
//        model.addAttribute("title", "Edit Post");
        Post post = postDao.getReferenceById(id);
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping(path = "/posts/edit/{id}")
    public String postEdit(@PathVariable long id, @RequestParam String title, @RequestParam String body){
        Post post = postDao.getReferenceById(id);
        post.setTitle(title);
        post.setBody(body);
        postDao.save(post);
        return "redirect:/posts";
    }


}

