package com.zhu.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找的问题不存在~"),
    TARGET_PARAM_NOT_FOUNT(2002,"未选中任何问题"),
    NO_LOGIN(2003,"未登录请先登录"),
    SYS_ERROR(2004,"本小站竟然人满为患，爱了爱了，么么哒~！"),
    TYPE_PARAM_WRONG(2005,"评论类型不存在或错误"),
    COMMENT_NOT_FOUNT(2006,"评论为空哦~");

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
