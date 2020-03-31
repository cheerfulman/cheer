package com.zhu.controller;

import com.zhu.Service.NoticeService;
import com.zhu.Service.UserService;
import com.zhu.mapper.UserMapper;
import com.zhu.DTO.AccessToKenDTO;
import com.zhu.pojo.GithubUser;
import com.zhu.pojo.User;
import com.zhu.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private UserMapper userMapper;

    /**
     *
     * @param code 授权码
     * @param state 随机生成的，为了方便跨转使用
     * @param response 代表服务器的响应
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
                           HttpServletResponse response){
        /**
         * 当点击 确认授权时，就会从github返回一个code （授权码）
         * 使用该授权码，去github得到令牌
         */
        AccessToKenDTO accessToKenDTO = getAccessToKenDTO(code,state);
        /**
         * 使用 授权码 得到令牌getAccessToKen()
         */
        String accessToKen = githubProvider.getAccessToKen(accessToKenDTO);

        /**
         * 再次 通过令牌 访问github得到该用户的信息
         */
        GithubUser githubUser = githubProvider.getUser(accessToKen);

        if(githubUser != null && githubUser.getId() != null){

            User user = getUser(githubUser);
            String token = user.getToken();

            //将该信息，封装到User 类中，并插入数据库
            userService.createOrUpdate(user);
            //将该 随机 字符串 当作 Cookie

            response.addCookie(new Cookie("token",token));

            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }

    //从Github 得到的 授权码 code
    public AccessToKenDTO getAccessToKenDTO(String code,String state){

        AccessToKenDTO accessToKenDTO = new AccessToKenDTO();
        accessToKenDTO.setClient_id(clientId);
        accessToKenDTO.setClient_secret(clientSecret);
        accessToKenDTO.setCode(code);
        accessToKenDTO.setState(state);
        accessToKenDTO.setRedirect_uri(redirectUri);
        return accessToKenDTO;
    }

    /**
     * 将 github的信息，存储到 User类中
     * @param githubUser 授权的github账户信息
     * @return User
     */
    public User getUser(GithubUser githubUser){
        User user = new User();
        String token = UUID.randomUUID().toString();
        user.setName(githubUser.getName());
        user.setToken(token);
        user.setAccountId(String.valueOf(githubUser.getId()));
        user.setAvatarUrl(githubUser.getAvatar_url());
        return user;
    }

}
