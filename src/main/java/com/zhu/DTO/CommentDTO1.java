package com.zhu.DTO;

import com.zhu.pojo.User;
import lombok.Data;

import java.util.Objects;

@Data
public class CommentDTO1 {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private Integer commentCount;
    private User user;
}
