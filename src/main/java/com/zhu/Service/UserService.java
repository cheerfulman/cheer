package com.zhu.Service;

import com.zhu.mapper.UserMapper;
import com.zhu.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 如果 数据库中有该 账户 则更新
     * 否则，插入
     * @param user
     */
    public void createOrUpdate(User user){
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if(dbUser == null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setPassword(UUID.randomUUID().toString().replaceAll("-"," ").substring(20));
            userMapper.insert(user);

        }else{
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }
    }

    public void insert(User user) {
        userMapper.insert(user);
    }

    public User findByName(String username) {
        return userMapper.findByName(username);
    }
}
