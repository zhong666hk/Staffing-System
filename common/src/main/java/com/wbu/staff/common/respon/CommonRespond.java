package com.wbu.staff.common.respon;

import com.wbu.staff.common.exception.AppExceptionExample;
import lombok.Data;

@Data
public class CommonRespond <T>{
    //响应状态码
    private int code;
    // 响应信息
    private String message;

    // 返回结果
    private T data;

    public CommonRespond(){}

    public CommonRespond(int code,String message,T data){
        this.code=code;
        this.message=message;
        this.data=data;
    }

    public CommonRespond(int code,String message){
        this(code,message,null);
    }

    public CommonRespond(int code,T data){
        this(code,null,data);
    }

    /**
     * 第一个T表示<T>是一个泛型----告诉编译器
     *  第二个T表示方法返回的是T类型的数据
     */
    public static <T> CommonRespond<T> error(AppExceptionExample appExceptionExample){
        return new CommonRespond<>(appExceptionExample.getCode(), appExceptionExample.getMessage());
    }

    public static <T> CommonRespond<T> error(RespondExample respondExample){
        return new CommonRespond<>(respondExample.getCode(),respondExample.getMessage());
    }
    public static <T>CommonRespond<T> error(int code,String message){
        return new CommonRespond<>(code,message);
    }


    public static <T>CommonRespond<T> succeed(String message, T data){
        return new CommonRespond<>(200,message,data);
    }

    public static <T>CommonRespond<T> succeed(T data){
        return new CommonRespond<>(200,null,data);
    }



}
