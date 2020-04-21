package com.zhu.util;

import com.zhu.enums.LikeType;

public class LikeUtil {
    public static String getKey(String type, Long id){
        return type + ":" + id;
    }
}
