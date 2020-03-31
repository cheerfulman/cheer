package com.zhu.controller;

import com.zhu.Service.CommentService;
import com.zhu.Service.QuestionService;
import com.zhu.DTO.CommentDTO1;
import com.zhu.DTO.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        //根据 问题ID查询问题
        QuestionDTO questionDTO = questionService.questionGetById(id);
        //通过这个问题查 评论数
        List<CommentDTO1> comments = commentService.ListByQuestionId(id,1);

        //相关问题
        List<QuestionDTO> questionDTOS = questionService.selectRelative(questionDTO);

        //增加阅读数
        questionService.inView(id);
        model.addAttribute("relatives",questionDTOS);
        model.addAttribute("comments",comments);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
