package com.zhu.controller;


import com.zhu.Service.NoticeService;
import com.zhu.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class noticeController {
    @Autowired
    private NoticeService noticeService;
    @RequestMapping("/notice/{noticeId}")
    public String clickNotice(@PathVariable("noticeId") Long noticeId){

        noticeService.read(noticeId);

        Integer id = noticeService.getQuestion(noticeId);
        return "redirect:/question/"+id;
    }
}
