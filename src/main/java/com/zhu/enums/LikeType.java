package com.zhu.enums;

public enum LikeType {
    LIKE_COMMENT(1,"like_comment");
    private Integer type;
    private String explain;

    LikeType(Integer type, String explain){
        this.type = type;
        this.explain = explain;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
