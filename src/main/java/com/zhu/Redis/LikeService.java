package com.zhu.Redis;

import com.zhu.util.LikeUtil;
import com.zhu.enums.LikeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class LikeService implements ILikeService{

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean liked(String type, Long subjectId, Long postId) {
        String key = LikeUtil.getKey(type,subjectId);
        if(redisTemplate.opsForSet().isMember(key,postId)) {
            redisTemplate.opsForSet().remove(key,postId);
            return false;
        }
        else {
            redisTemplate.opsForSet().add(key,postId);
            return true;
        }

    }

    @Override
    public Long count(String type, Long subjectId) {
        String key = LikeUtil.getKey(type,subjectId);

        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public boolean hasId(String type, Long subjectId, Long postId) {
        String key = LikeUtil.getKey(type,subjectId);
        return redisTemplate.opsForSet().isMember(key,postId);
    }


}
