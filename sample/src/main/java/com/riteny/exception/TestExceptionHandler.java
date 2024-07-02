package com.riteny.exception;

import com.alibaba.fastjson2.JSONObject;
import com.riteny.util.exception.core.api.CommonApiExceptionHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TestExceptionHandler implements CommonApiExceptionHandler<TestException, JSONObject> {

    @Override
    public JSONObject handler(TestException e, HttpServletRequest request, HttpServletResponse response) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", e.getErrorCode());
        jsonObject.put("resultMsg", e.getErrorMsg());

        return jsonObject;
    }
}
