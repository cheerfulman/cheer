package com.zhu;

import com.zhu.mapper.QuestionMapper;
import com.zhu.mapper.UserMapper;

import com.zhu.provider.OSSProvider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;

@SpringBootTest
@MapperScan(basePackages = "com.zhu.mapper")
class CommunityApplicationTests {
    @Autowired
    private OSSProvider ossProvider;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;
    @Test
    void contextLoads(HttpServletRequest request) throws Exception {
//        request.getServletContext().getAttribute();
    }

}
