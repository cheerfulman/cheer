package com.zhu.controller;

import com.zhu.Service.UserService;
import com.zhu.mapper.UserMapper;
import com.zhu.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @ResponseBody
    @PostMapping("/confirmUser")
    public String confirmUser(@Param("username") String username){
        if(StringUtils.isBlank(username))return "用户名不能为空";
        User user = userService.findByName(username);
        if(user != null){
            String msg = "用户名已被注册";
            return msg;
        }else{
            return "ok";
        }
    }

    @ResponseBody
    @PostMapping("/confirmPassword")
    public String confirmPassword(@Param("password1") String password1,@Param("password2") String password2){
        if(StringUtils.isBlank(password1) || StringUtils.isBlank(password2))return "密码不能为空";
        if(password1.length() < 6 || password1.length() > 19) return "请保持密码在6~20位之间";
        if(!password1.equals(password2))return "两次密码输入不一致";
        return "成功";
    }

    @PostMapping("/register")
    public String registerUser(@Param("username") String username, @Param("password") String password,
                               Model model, HttpServletRequest request){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(username);
            user.setPassword(password);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            user.setAvatarUrl("/images/default.png");
            request.getSession().setAttribute("user",user);
            userService.insert(user);
            return "register";
        }
}
