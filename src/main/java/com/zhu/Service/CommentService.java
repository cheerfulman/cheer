package com.zhu.Service;


import com.zhu.Redis.LikeService;
import com.zhu.enums.CommentTypeEnum;
import com.zhu.exception.CustomizeErrorCode;
import com.zhu.exception.CustomizeException;
import com.zhu.mapper.CommentMapper;
import com.zhu.mapper.QuestionMapper;
import com.zhu.mapper.UserMapper;
import com.zhu.pojo.Comment;
import com.zhu.DTO.CommentDTO1;
import com.zhu.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LikeService likeService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public void insert(Comment comment){
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUNT);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUNT);
        }
        //回复的是评论
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //哪条回复下的 评论
            Comment comment1 = commentMapper.queryById(comment.getParentId());
            if(comment1 == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUNT);
            }else {
                commentMapper.insert1(comment);
                commentMapper.addCommentCount(comment.getParentId());

                //插入通知
                noticeService.noticeTypeComment(comment,comment1);


            }
        }else { //回复了问题
            commentMapper.insert1(comment);
            questionMapper.addComment(comment.getParentId());
            //插入通知
            noticeService.noticeTypeQuestion(comment);
        }
    }

    public List<CommentDTO1> ListByQuestionId(Long id,Integer type) {
        List<Comment> comments = commentMapper.queryAllQuestionById(id, type);
        System.out.println(comments.size());
        if(comments.size() == 0){
            return new ArrayList<>();
        }

        //将所有评论 的 评论人id 去重
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());


        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        // 将 ID 与 用户映射
        Map<Long,User> map = new HashMap<>();
        for (Long userId : userIds) {
            User user = userMapper.findById(userId);
            map.put(userId,user);
        }

        List<CommentDTO1> commentDTO1s = comments.stream().map(comment -> {
            CommentDTO1 commentDTO1 = new CommentDTO1();
            BeanUtils.copyProperties(comment,commentDTO1);
            commentDTO1.setUser(map.get(comment.getCommentator()));
            return commentDTO1;
        }).collect(Collectors.toList());

        for(CommentDTO1 c: commentDTO1s){
            c.setLikeCount(likeService.count("like_comment",c.getId()));
        }
        return commentDTO1s;
    }
}
