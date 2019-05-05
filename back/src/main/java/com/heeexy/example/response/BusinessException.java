package com.heeexy.example.response;

/**
 *
 */
public class BusinessException extends Exception {

    private int errCode;
    private String errMsg;

    public BusinessException() {
    }

    public BusinessException(int errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

}
