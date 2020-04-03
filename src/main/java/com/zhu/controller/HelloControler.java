package com.zhu.controller;

import com.zhu.Service.QuestionService;
import com.zhu.DTO.paginationDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class HelloControler {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String hello(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        @RequestParam(name = "search",required = false) String search){
        if(StringUtils.isBlank(search))search = null;
        else search = search.replaceAll(" ","|");
        Integer totalQuestion = questionService.countPage(search);

        paginationDTO pagination = questionService.listAllQuestions(search,totalQuestion,page,size);
        model.addAttribute("search",search);
        model.addAttribute("pagination",pagination);
        return "index";
    }



    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
