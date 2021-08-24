package com.itauge.blog.controller.web;

import com.itauge.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ArchiveShowController {

    @Autowired
    BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archMap",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }
}
