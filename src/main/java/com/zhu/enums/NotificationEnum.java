package com.zhu.enums;

public enum NotificationEnum {
    REPLAY_QUESTION(1,"回复了问题"),
    REPLAY_COMMENT(2,"回复了评论"),
    REPLAY_LIKE(3,"赞了你");
    private int type;
    private String name;


    NotificationEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
