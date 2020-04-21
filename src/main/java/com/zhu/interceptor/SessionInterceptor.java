package com.zhu.interceptor;

import com.zhu.Redis.LikeService;
import com.zhu.Service.NoticeService;
import com.zhu.mapper.UserMapper;
import com.zhu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private LikeService likeService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NoticeService noticeService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for(Cookie c : cookies){
                if(c.getName().equals("token")){
                    String token = c.getValue();
                    User user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user",user);
                        Integer unRead = noticeService.unRead(user.getId());
                        request.getSession().setAttribute("unread",unRead);
                    }
                    break;
                }
            }
        }

        // true 就会继续 执行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
