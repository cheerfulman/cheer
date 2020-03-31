package com.zhu.DTO;

import lombok.Data;

@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
    private Long commentator;
    private Integer commentCount;
}
