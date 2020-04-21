package com.zhu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import com.zhu.DTO.LikeDTO;
import com.zhu.DTO.ResultDTO;
import com.zhu.Redis.LikeService;
import com.zhu.Service.NoticeService;
import com.zhu.enums.LikeType;
import com.zhu.exception.CustomizeErrorCode;
import com.zhu.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class LikeController {
    @Autowired
    private LikeService likeService;

    @Autowired
    private NoticeService noticeService;

    @ResponseBody
    @PostMapping("/like")
    public Object post(Integer type, Long subjectId, HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");

        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        HashMap<String,Object> map = new HashMap<>();
        for(LikeType likeType : LikeType.values()){
            if(type.equals(likeType.getType()) ){
                boolean likeStatus = likeService.liked(likeType.getExplain(),subjectId,user.getId());

                // 如果点赞 成功， 通知被点赞人
                if(likeStatus == true){
                    noticeService.noticeTypeLike(subjectId,user.getId());
                }

                Long count = likeService.count(likeType.getExplain(),subjectId);
                map.put("likeStatus",likeStatus);
                map.put("count",count);
                System.out.println(count);
                break;
            }
        }

        return map;
    }
}
