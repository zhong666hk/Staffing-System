package com.wbu.staff.common.exception;

public enum AppExceptionExample {
    SYSTEM_INNER_ERROR(500,"系统内部异常"),
    ;


    private int code;
    private String message;

    private AppExceptionExample(int code, String message){
        this.code=code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
