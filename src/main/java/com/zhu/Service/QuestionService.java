package com.zhu.Service;

import com.zhu.DTO.paginationDTO;
import com.zhu.DTO.QuestionDTO;
import com.zhu.exception.CustomizeErrorCode;
import com.zhu.exception.CustomizeException;
import com.zhu.mapper.QuestionMapper;
import com.zhu.mapper.UserMapper;
import com.zhu.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;


    public paginationDTO listMyQuestions(Integer totalQuestion, Integer page, Integer size, Long id){
        //向上取整,总页数
        Integer totalPage = (totalQuestion+size-1)/size;
        if(page < 1)page = 1;
        if(page > totalPage) page = totalPage;


        //分页类
        paginationDTO<QuestionDTO> paginationDTO = new paginationDTO<>();

        paginationDTO.setPage(totalPage,page,size);

        //带有头像 url的 list
        List<QuestionDTO> ans = new ArrayList<>();

        Integer offset = size * (page - 1);
        if(offset < 0){
            paginationDTO.setT(ans);
            return paginationDTO;
        }
        //所有发布的问题
        List<Question> list = questionMapper.queryAll(offset,size,id);

        //将所有 发布的问题question 加上 User 里面的头像参数
        for(Question question : list){
            /**
             * 寻找user
             * 将user和question 添加到questionDTO中 （user里面有头像的src）
             */
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            ans.add(questionDTO);
        }

        paginationDTO.setT(ans);
        return paginationDTO;
    }


    // 查询自己发布的所有问题
    public List<QuestionDTO> myQuestions(User user){
        List<QuestionDTO> list = new ArrayList<>();
        List<Question> list1 = questionMapper.queryQuestionByUserId(user.getId());
        for(Question q : list1){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            questionDTO.setUser(user);
            list.add(questionDTO);
        }
        return list;
    }

    public QuestionDTO questionGetById(Long id){
        Question question = (Question) questionMapper.queryByQueId(id);
//        System.out.println(question);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
//        System.out.println(questionDTO);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            questionMapper.insertQue(question);
        }else{
            question.setGmtModified(System.currentTimeMillis());
            int update = questionMapper.update(question);
            if(update != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void inView(Long id) {
        Question question = questionMapper.queryByQueId(id);
        question.setViewCount(question.getViewCount()+1);
        questionMapper.addView(question);
    }

    public List<QuestionDTO> selectRelative(QuestionDTO questionDTO) {
        if(StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
        String tag = questionDTO.getTag().replaceAll(",","|");
        questionDTO.setTag(tag);
        List<QuestionDTO> questionDTOS = questionMapper.selectRelative(questionDTO);
        return questionDTOS;
    }

    public paginationDTO listAllQuestions(String search,Integer totalQuestion, Integer page, Integer size) {
        //总页面 totalPage
        Integer totalPage = (totalQuestion+size-1)/size;
        //处理 错误 页面
        if(page < 1)page = 1;
        if(page > totalPage) page = totalPage;


        //分页类 泛型为 QuestionDTO
        paginationDTO<QuestionDTO> paginationDTO = new paginationDTO<>();

        paginationDTO.setPage(totalPage,page,size);

        //带有头像 url的 list
        List<QuestionDTO> ans = new ArrayList<>();

        Integer offset = size * (page - 1);
        if(offset < 0){
            paginationDTO.setT(ans);
            return paginationDTO;
        }
        //所有发布的问题
        List<Question> list = questionMapper.queryAll1(search,offset,size);

        //将所有 发布的问题question 加上 User 里面的头像参数
        for(Question question : list){
            /**
             * 寻找user
             * 将user和question 添加到questionDTO中 （user里面有头像的src）
             */
            // 找到 作者的 头像
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            ans.add(questionDTO);
        }
        paginationDTO.setT(ans);
        return paginationDTO;
    }

    public Integer countPage(String search) {
        return questionMapper.countPage(search);
    }
}
