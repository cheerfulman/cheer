package com.zhu.controller;

import com.zhu.Service.UserService;
import com.zhu.mapper.UserMapper;
import com.zhu.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public String login(@Param("username") String username, @Param("password") String password,
                        Model model, HttpServletRequest request, HttpServletResponse response){
        User user = userService.findByName(username);
        if(user == null){
            model.addAttribute("error2","用户名不存在");
            model.addAttribute("username",username);
            model.addAttribute("password",password);
            return "register";
        }
        if(!user.getPassword().equals(password.trim())){
            model.addAttribute("error3","密码错误");
            model.addAttribute("username",username);
            model.addAttribute("password",password);
            return "register";
        }
        request.getSession().setAttribute("user",user);
        response.addCookie(new Cookie("token",user.getToken()));
        return "redirect:/";
    }
}
