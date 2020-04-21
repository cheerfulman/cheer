package com.zhu.controller;

import com.zhu.Service.NoticeService;
import com.zhu.Service.QuestionService;
import com.zhu.pojo.Notification;
import com.zhu.DTO.paginationDTO;
import com.zhu.DTO.QuestionDTO;
import com.zhu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NoticeService noticeService;

    /**
     *
     * @param action 前端返回参数是 我的问题，还是 我的通知
     * @param page 当前页码
     * @param size 一页的数量
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          @RequestParam(name = "page", defaultValue = "1")Integer page,
                          @RequestParam(name = "size", defaultValue = "5")Integer size,
                          Model model,
                          HttpServletRequest request){
        // 判断用户是否登录，提取用户信息
        User user = (User)request.getSession().getAttribute("user");
        if(user != null) {
            if("questions".equals(action)){
                // 通过该用户 查出该用户 发布的所有问题
                List<QuestionDTO> queD = questionService.myQuestions(user);

                //有多少个问题
                Integer totalQuestion = queD.size();

                //分页显示
                paginationDTO<QuestionDTO> paginationDTO = questionService.listMyQuestions(totalQuestion,page,size,user.getId());


                model.addAttribute("pagination", paginationDTO);
                model.addAttribute("section","questions");
                model.addAttribute("sectionName","我的提问");
            }else if("replies".equals(action)){
                //该用户有多少个通知
                Integer noticeCount = noticeService.CountNotice(user.getId());

                // 分页显示
                paginationDTO<Notification> list = noticeService.list(noticeCount, page, size,user.getId());
                // 该用户有多少个未读 通知
                Integer unRead = noticeService.unRead(user.getId());

                model.addAttribute("pagination",list);
                model.addAttribute("section","replies");
                model.addAttribute("sectionName","最新回复");
            }
            return "profile";
        } else {
            System.out.println("用户未登录");
            return "redirect:/";
        }


    }
}
