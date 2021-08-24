package com.itauge.blog.controller.web;

import com.itauge.blog.entity.Blog;
import com.itauge.blog.entity.Comment;
import com.itauge.blog.entity.User;
import com.itauge.blog.service.BlogService;
import com.itauge.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentShowController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId") Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentParentIdIsNull(blogId));
        return "blog::commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment,HttpSession session){
        Long blogId = comment.getBlog().getId();
        Blog blog = blogService.getBlog(blogId);
        comment.setBlog(blog);
        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAvatar("/static/images/adminIcon.jpg");
            comment.setAdmin(true);
            comment.setNickname(user.getNickname());
        }else {
            comment.setAvatar("/static/images/head.png");
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + comment.getBlog().getId() ;
    }


}
