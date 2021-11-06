package com.knightboost.moonlight.common;

public class ApiResult<T> {

    public static final int CODE_SUCCESS = 200;

    /**
     * 状态码：200 成功，其他为失败
     */
    public int code;

    /**
     * 成功为success，其他为失败原因
     */
    public String message;

    /**
     * 数据结果集
     */
    public T data;

    public ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ApiResult<T> success(){
        return new ApiResult<T>(CODE_SUCCESS,"success",null);
    }
    public static <T> ApiResult<T> success(T data){
        return new ApiResult<T>(CODE_SUCCESS,"success",data);
    }

    public static <T> ApiResult<T> failed(int code,String message){
        return new ApiResult<T>(code,message,null);
    }

    public static <T> ApiResult<T> failed(String message){
        return new ApiResult<T>(-1,message,null);
    }
}
