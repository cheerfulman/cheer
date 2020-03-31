package com.zhu.DTO;

import lombok.Data;

import java.util.List;

@Data
public class TagDTO {
    /**
     * categoryName 代表标签的类别
     * tags 数组 存储 改类别下的所有标签
     */

    private String categoryName;
    private List<String> tags;
}
