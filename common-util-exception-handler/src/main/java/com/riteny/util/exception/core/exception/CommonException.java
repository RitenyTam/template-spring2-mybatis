package com.riteny.util.exception.core.exception;

public class CommonException extends RuntimeException {

    private String errorCode;

    private String errorMsg;

    private String langType;

    public CommonException(String errorCode, String errorMsg, String langType) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.langType = langType;
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getLangType() {
        return langType;
    }

    public void setLangType(String langType) {
        this.langType = langType;
    }
}
