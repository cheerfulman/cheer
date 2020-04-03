package com.zhu;



import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
@MapperScan(basePackages = "com.zhu.mapper")
class CommunityApplicationTests {
    @Test
    void contextLoads(){

    }

}
