package com.itauge.blog.controller.admin;

import com.itauge.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/comment")
    public String comment(@PageableDefault(size = 10,sort = {"createTime"},direction = Sort.Direction.DESC)Pageable pageable, Model model){
        model.addAttribute("page",commentService.findAll(pageable));
        return "admin/comments";
    }

    @GetMapping("/comment/{id}/delete")
    public String deleteComment(@PathVariable("id") Long id, Model attributes){
        commentService.deleteComment(id);
        attributes.addAttribute("msg","操作成功");
        return "/admin/comments";
    }
}
