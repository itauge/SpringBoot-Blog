package com.itauge.blog.controller.admin;

import com.itauge.blog.entity.Blog;
import com.itauge.blog.entity.Tag;
import com.itauge.blog.entity.Type;
import com.itauge.blog.entity.User;
import com.itauge.blog.service.BlogService;
import com.itauge.blog.service.TagService;
import com.itauge.blog.service.TypeService;
import com.itauge.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String toBlogs(@PageableDefault(size = 10,
            sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
//        model.addAttribute("recommendBlog", blogService.getTop(3));

        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 10,
            sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));

        //下面語法局部渲染,需要前端th:fragment="blogList"
        return "admin/blogs :: blogList";

    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.getAll());
        model.addAttribute("blog",new Blog());
        model.addAttribute("tags",tagService.getAll());
        return "admin/blogs_input";
    }



    @GetMapping("/blogs/{id}/update")
    public String editInput(@PathVariable("id") Long id, Model model){
        Blog blog = blogService.getBlog(id);
        blog.initTags();
        model.addAttribute("types",typeService.getAll());
        model.addAttribute("tags",tagService.getAll());
        model.addAttribute("blog",blog);
        return "admin/blogs_input";
    }

    @PostMapping("/blogs/input")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session,Model model){
        blog.setUser((User) session.getAttribute("user"));

        if(blog.getType().getId() == null | "".equals(blog.getContent()) | "".equals(blog.getContent())){
            model.addAttribute("msg", "分类未选择/博客内容/博客描述未填写");
            return "admin/blogs_input";
        }
        Long id = blog.getType().getId();

        blog.setType(typeService.getType(id));
        blog.setTags(tagService.getTagsById(blog.getTagIds()));


        Blog saveBlog;

        if(blog.getId()==null){
            saveBlog = blogService.saveBlog(blog);
        }else {
            saveBlog = blogService.updateBlog(blog.getId(), blog);
        }

        if (saveBlog == null){
            attributes.addFlashAttribute("msg","操作失敗");
        }else {
            attributes.addFlashAttribute("msg","操作成功");
        }

        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable("id") Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("msg","刪除成功");
        return "redirect:/admin/blogs";
    }

}
