package com.cdd.gsl.common.result;

public class CommonResult<T> {


    public static final int RESULT_SUCCESS_FLAG = new Integer(1);
    public static final int RESULT_FAILURE_FLAG = new Integer(0);

    private int flag;

    private String message;

    private T data;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public CommonResult(int flag, T data, String message){
        this.flag = flag;
        this.data = data;
        this.message = message;
    }
    public CommonResult(int flag, T data){
        this.flag = flag;
        this.data = data;
    }

    public CommonResult(){}

    public static <T> CommonResult<T> forSuccess(T data, String message){
        return new CommonResult<>(RESULT_SUCCESS_FLAG, data, message);
    }

    public static <T> CommonResult<T> forSuccess(T data){
        return forSuccess(data, null);
    }

    public static <T> CommonResult<T> forSuccess(){
        return forSuccess(null);
    }

    public static <T> CommonResult<T> forFailure(T data, String message){
        return new CommonResult<>(RESULT_FAILURE_FLAG, data, message);
    }

    public static <T> CommonResult<T> forFailure(String message){
        return forFailure(null, message);
    }

    public static <T> CommonResult<T> forFailure(){
        return forFailure(null);
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "flag=" + flag +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
