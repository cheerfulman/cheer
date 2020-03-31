package com.zhu.controller;

import com.zhu.DTO.CommentDTO;
import com.zhu.DTO.CommentDTO1;
import com.zhu.DTO.ResultDTO;
import com.zhu.Service.CommentService;
import com.zhu.exception.CustomizeErrorCode;
import com.zhu.mapper.CommentMapper;
import com.zhu.mapper.QuestionMapper;
import com.zhu.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     *
     * @param commentDTO 传递 评论 信息的载体
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(CommentDTO commentDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentDTO == null || StringUtils.isBlank(commentDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_NOT_FOUNT);
        }
        Comment comment = new Comment();

        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(commentDTO.getCommentator());
        comment.setLikeCount(0L);
        comment.setCommentator(user.getId());

//        System.out.println(comment);

        //插入数据库
        commentService.insert(comment);

        return ResultDTO.okOf();
    }


    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO comments(@PathVariable(name = "id") Long id){
        List<CommentDTO1> commentDTO1s = commentService.ListByQuestionId(id, 2);
        return ResultDTO.okOf(commentDTO1s);
    }
}
