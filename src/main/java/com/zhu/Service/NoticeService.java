package com.zhu.Service;

import com.zhu.DTO.paginationDTO;
import com.zhu.enums.NotificationEnum;
import com.zhu.mapper.CommentMapper;
import com.zhu.mapper.NoticeMapper;
import com.zhu.mapper.QuestionMapper;
import com.zhu.mapper.UserMapper;
import com.zhu.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NoticeMapper noticeMapper;

    //回复的评论

    /**
     *
     * @param comment 评论
     * @param comment1 评论的回复
     */
    public void noticeTypeComment(Comment comment,Comment comment1){
        System.out.println(comment.getParentId() + " " + comment1.getParentId());
        Notification notification = new Notification();
        notification.setType(NotificationEnum.REPLAY_COMMENT.getType());
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotifier(comment.getCommentator());
        notification.setReceiver(comment1.getCommentator());
        notification.setStatus(0);

        notification.setOuterId(comment1.getParentId());
        //获取用户的名字
        User user = userMapper.findById(comment.getCommentator());
        notification.setNoticeName(user.getName());
        //获取评论所在的题目
        Question question = questionMapper.queryByQueId(comment1.getParentId());
        notification.setTitle(question.getTitle());

        noticeMapper.insert(notification);
    }

    //回复了问题
    public void noticeTypeQuestion(Comment comment){
        Notification notification = new Notification();

        notification.setType(NotificationEnum.REPLAY_QUESTION.getType());
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotifier(comment.getCommentator());

        Question question = questionMapper.queryByQueId(comment.getParentId());
        notification.setReceiver(question.getCreator());

        notification.setStatus(0);
        //获取用户的名字
        User user = userMapper.findById(comment.getCommentator());
        notification.setNoticeName(user.getName());

        //获取问题的题目
        notification.setTitle(question.getTitle());
        notification.setOuterId(comment.getParentId());
        noticeMapper.insert(notification);
    }
    public Integer CountNotice(Long id){
        return noticeMapper.queryAllNoticeByUserId(id);
    }
    // 点赞通知
    public void noticeTypeLike(Long subjectId,Long postId){

        Notification notification = new Notification();
        // 通知类型为点赞
        notification.setType(NotificationEnum.REPLAY_LIKE.getType());
        // 创建时间
        notification.setGmtCreate(System.currentTimeMillis());
        // 被点赞 评论 Id
        Comment comment = commentMapper.queryById(subjectId);
        notification.setReceiver(comment.getCommentator());
        // 点赞人的 id
        notification.setNotifier(postId);
        // 未读状态
        notification.setStatus(0);
        // 得到 点赞人的名字
        User user = userMapper.findById(postId);
        notification.setNoticeName(user.getName());

        // 提示文字 title

        int len = comment.getContent().length();
        System.out.println(len);
        // 如果评论的字数大于7，就取前7 + 三个.
        if(len >= 7){
            String title = comment.getContent().substring(0,5) + "...";
            notification.setTitle(title);
        }else{
            notification.setTitle(comment.getContent());
        }
        notification.setOuterId(comment.getParentId());
        noticeMapper.insert(notification);

//        Question question = questionMapper.queryByQueId(comment.getParentId());

    }


    /**
     *
     * @param noticeCount 通知的总数量
     * @param page  当前页
     * @param size  一页的通知数量
     * @param id
     * @return
     */
    public paginationDTO list(Integer noticeCount, Integer page, Integer size, Long id) {
        paginationDTO<Notification> paginationDTO = new paginationDTO<>();
        List<Notification> list = new ArrayList<>();
        //如果通知为0, 直接返回
        if(noticeCount <= 0){
            return paginationDTO;
        }
        //向上取整,总页数
        Integer totalPage = (noticeCount+size-1)/size;
        if(page < 1)page = 1;
        if(page > totalPage) page = totalPage;
        //分页类
        paginationDTO.setPage(totalPage,page,size);

        Integer offset = size * (page - 1);

        //所有发布的问题

        list = noticeMapper.queryAll(offset,size,id);

        paginationDTO.setT(list);
        return paginationDTO;
    }

    public void read(Long noticeId) {
        noticeMapper.read(noticeId);
    }

    public Integer unRead(Long id) {
        return noticeMapper.unRead(id);
    }

    public Integer getQuestion(Long noticeId) {
        Long noticeOuterId = noticeMapper.getNoticeById(noticeId);
        Question question = questionMapper.queryByQueId(noticeOuterId);
        return question.getId();
    }
}
