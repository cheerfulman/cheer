package com.zhu.Redis;

import com.zhu.enums.LikeType;

public interface ILikeService {
    boolean liked(String type, Long subjectId, Long postId);
    Long count(String type, Long subjectId);
    boolean hasId(String type, Long subjectId, Long postId);
}
