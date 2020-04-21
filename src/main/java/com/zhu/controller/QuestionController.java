package com.zhu.controller;

import com.zhu.Redis.LikeService;
import com.zhu.Service.CommentService;
import com.zhu.Service.QuestionService;
import com.zhu.DTO.CommentDTO1;
import com.zhu.DTO.QuestionDTO;
import com.zhu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model, HttpServletRequest request){
        //根据 问题ID查询问题
        QuestionDTO questionDTO = questionService.questionGetById(id);

        //通过这个问题查 评论数
        List<CommentDTO1> comments = commentService.ListByQuestionId(id,1);

        //相关问题
        List<QuestionDTO> questionDTOS = questionService.selectRelative(questionDTO);

        // 如果用户没登录， 点赞光标则 不亮
        model.addAttribute("liked",false);
        User user = (User)request.getSession().getAttribute("user");
        if(user != null){
            // 否则 查看自己 是否点赞过，点赞的 亮光
            for(CommentDTO1 c: comments){
                boolean liked = likeService.hasId("like_comment",c.getId(),user.getId());
                model.addAttribute("liked",liked);
            }
        }
//        likeService.hasId("like_comment",)
        //增加阅读数
        questionService.inView(id);
        model.addAttribute("relatives",questionDTOS);
        model.addAttribute("comments",comments);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
