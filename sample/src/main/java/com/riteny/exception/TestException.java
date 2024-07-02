package com.riteny.exception;

import com.riteny.util.exception.core.exception.CommonApiException;

public class TestException extends CommonApiException {
    public TestException(String errorCode, String errorMsg, String langType) {
        super(errorCode, errorMsg, langType);
    }
}
