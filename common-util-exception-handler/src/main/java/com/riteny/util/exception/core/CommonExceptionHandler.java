package com.riteny.util.exception.core;

import com.riteny.util.exception.core.api.CommonApiExceptionHandlerAgent;
import com.riteny.util.exception.core.view.CommonViewExceptionHandlerAgent;
import com.riteny.util.exception.core.exception.CommonApiException;
import com.riteny.util.exception.core.exception.CommonException;
import com.riteny.util.exception.core.exception.CommonViewException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Riteny
 * 2019/11/25  14:07
 */
@ControllerAdvice
public class CommonExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger("api");

    private final CommonApiExceptionHandlerAgent commonApiExceptionHandlerAgent;

    private final CommonViewExceptionHandlerAgent commonViewExceptionHandlerAgent;

    public CommonExceptionHandler(CommonApiExceptionHandlerAgent commonApiExceptionHandlerAgent, CommonViewExceptionHandlerAgent commonViewExceptionHandlerAgent) {
        this.commonApiExceptionHandlerAgent = commonApiExceptionHandlerAgent;
        this.commonViewExceptionHandlerAgent = commonViewExceptionHandlerAgent;
    }

    @ExceptionHandler(value = {CommonApiException.class})
    @ResponseBody
    public Object commonApiExceptionHandler(HttpServletRequest request, HttpServletResponse response, CommonException e) {

        logger.error("Before exception handler : {}", e.getMessage());
        Object result = commonApiExceptionHandlerAgent.handleException(e, request, response);
        logger.error("After exception handler : {}", e.getErrorMsg(), e);
        logger.error("url : {}   method : {}  IP : {} response : {}", request.getRequestURL().toString(), request.getMethod(), request.getRemoteAddr(), result);

        return result;
    }

    @ExceptionHandler(value = CommonViewException.class)
    @ResponseBody
    public void commonViewExceptionHandler(HttpServletRequest request, HttpServletResponse response, CommonException e) {
        logger.error("Before exception handler : {}", e.getMessage());
        commonViewExceptionHandlerAgent.handleException(e, request, response);
        logger.error("After exception handler : {}", e.getErrorMsg(), e);
        logger.error("url : {}   method : {}  IP : {}", request.getRequestURL().toString(), request.getMethod(), request.getRemoteAddr());
    }
}
