package com.zhu.pojo;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String accountId;
    private String name;
    private String token;
    private long gmtCreate;
    private long gmtModified;
    private String avatarUrl;
    private String password;
}
