package com.itauge.blog.controller.web;

import com.itauge.blog.entity.Type;
import com.itauge.blog.service.BlogService;
import com.itauge.blog.service.TypeService;
import com.itauge.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    TypeService typeService;

    @Autowired
    BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable("id") Long id, @PageableDefault(size = 10,direction = Sort.Direction.DESC,sort = {"updateTime"})Pageable pageable,
                        Model model){
        List<Type> typeList = typeService.listTypeTop(10000);
        if (id == -1){
            id = typeList.get(0).getId();
        }

        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",typeList);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        return "typess";
    }

}
