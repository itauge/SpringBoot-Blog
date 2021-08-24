package com.itauge.blog.controller.admin;

import com.itauge.blog.entity.User;
import com.itauge.blog.service.BlogService;
import com.itauge.blog.service.TypeService;
import com.itauge.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;

    @RequestMapping({"/login","/login.html","/"})
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username,password);

        if (user != null){
            session.setAttribute("user",user);
            return "redirect:/admin/blogs";
        }else {
            attributes.addFlashAttribute("msg","用戶和密碼錯誤");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}

