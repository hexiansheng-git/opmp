package com.hhwy.utils.exception;

/**
 * 自定义业务异常类
 */
public class CustomBusinessException extends RuntimeException{

    private ErrorCodes errorCode;
    private String msg;
    private Object data;

    public CustomBusinessException(String errorMsg){
        super(errorMsg);
        this.errorCode = ErrorCodes.Error;
        this.msg = errorMsg;
    }
    public CustomBusinessException(ErrorCodes errorCode, String errorMsg){
        super(errorMsg);
        this.errorCode = errorCode;
        this.msg = errorMsg;
    }

    public CustomBusinessException(ErrorCodes errorCode, String errorMsg, Object data){
        super(errorMsg);
        this.errorCode = errorCode;
        this.msg = errorMsg;
        this.data = data;
    }
    
    public boolean isWarning(){
        return this.errorCode == ErrorCodes.Warning;
    }
    public static CustomBusinessException warning(String msg){
        return new CustomBusinessException(ErrorCodes.Warning,msg);
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCodes errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public enum ErrorCodes{
        Success,
        Warning,
        Error,

    }


}
