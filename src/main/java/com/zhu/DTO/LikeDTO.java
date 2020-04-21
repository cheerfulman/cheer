package com.zhu.DTO;

import com.alibaba.fastjson.JSON;
import com.zhu.enums.LikeType;
import com.zhu.pojo.User;
import lombok.Data;

@Data
public class LikeDTO {
    private Integer type;
    private Long subjectId;
}
