package com.itauge.blog.controller.admin;

import com.itauge.blog.entity.Type;
import com.itauge.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/add")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types_input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable("id") Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types_input";
    }

    @PostMapping("/types/saveType")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null){
            attributes.addFlashAttribute("msg","不能重複添加");
            return  "redirect:/admin/types_input";
        }

        if (result.hasErrors()){
            attributes.addFlashAttribute("msg","請輸入分類名");
            return "admin/types_input";
        }

        Type t = typeService.saveType(type);
        if (t == null){
            attributes.addFlashAttribute("msg","新增失敗");
        }else {
            attributes.addFlashAttribute("msg","新增成功");
        }
        return "redirect:/admin/types";

    }

    @PostMapping("/types/{id}/update")
    public String editPost(@Valid Type type, BindingResult result,
                           @PathVariable("id") Long id,
                           RedirectAttributes attributes){
        Type type1 = typeService.getType(id);

        if (type1.getId() != id){
            attributes.addAttribute("msg","非法操作請重新操作");
            return "redirect:/admin/types";
        }

        Type t = typeService.updateType(id,type);
        if (t == null){
            attributes.addFlashAttribute("msg","更新失敗");
        }else {
            attributes.addFlashAttribute("msg","更新成功");
        }
        return "redirect:/admin/types";

    }

    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable("id") Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("msg","刪除成功");
        return "redirect:/admin/types";
    }
}
