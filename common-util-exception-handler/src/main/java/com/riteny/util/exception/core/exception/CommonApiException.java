package com.riteny.util.exception.core.exception;

public class CommonApiException extends CommonException{
    public CommonApiException(String errorCode, String errorMsg, String langType) {
        super(errorCode, errorMsg, langType);
    }
}
