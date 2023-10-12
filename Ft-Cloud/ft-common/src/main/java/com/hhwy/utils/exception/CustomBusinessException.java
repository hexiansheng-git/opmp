package com.hhwy.utils.exception;

import com.hhwy.common.core.exception.CustomException;

/**
 * 自定义业务异常类
 * 不要用这个, 用平台的吧   CustomException
 */
@Deprecated
public class CustomBusinessException extends CustomException {

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
        Warning(204), 
        Error(500);
        private Integer code;

        ErrorCodes(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }


}
