package com.itauge.blog.controller.admin;

import com.itauge.blog.entity.User;
import com.itauge.blog.service.BlogService;
import com.itauge.blog.service.TypeService;
import com.itauge.blog.service.UserService;
import com.itauge.blog.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
        //獲取當前用戶
        Subject subject = SecurityUtils.getSubject();
        //獲得token
        String md5password = MD5Util.getMd5(password);
        UsernamePasswordToken token = new UsernamePasswordToken(username,md5password);
        try {
            //利用令牌進行登錄
            subject.login(token);
            User user = (User) subject.getPrincipal();
            //設置session
            session.setAttribute("user",user);
            return "redirect:/admin/blogs";
        }catch (UnknownAccountException ua){
            attributes.addFlashAttribute("msg","用戶名不正確");
            return "redirect:/admin/login";
        }catch (IncorrectCredentialsException ic){
            attributes.addFlashAttribute("msg","密碼錯誤");
            return "redirect:/admin/login";
        }catch (LockedAccountException la){
            attributes.addFlashAttribute("msg","賬號被鎖定");
            return "redirect:/admin/login";
        }catch (Exception e){
            attributes.addFlashAttribute("msg","沒有權限");
            return "redirect:/admin/login";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }

}

