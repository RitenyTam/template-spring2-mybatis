package com.riteny.config;

import com.riteny.logger.CommonLoggerFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author Riteny
 * 2020/1/13  13:49
 */
public class ApiLogAspect implements MethodInterceptor {

    private Logger logger = CommonLoggerFactory.getLogger("api");

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        //记录执行开始时间，访问路径，入参，日期和访问者IP信息等
        long startTime = System.currentTimeMillis();
        logger.info("#Request : " + request.getRequestURL().toString() + " #Method : " + request.getMethod()
                + " #Remote IP : " + request.getRemoteAddr() + " #Param : " + Arrays.asList(methodInvocation.getArguments()));
        try {
            Object object = methodInvocation.proceed();
            //记录执行完成后，返回的参数
            logger.info("#Response : " + request.getRequestURL().toString() + " #Method : " + request.getMethod()
                    + " #Remote IP : " + request.getRemoteAddr() + " #Result : " + object + " #Finished : "
                    + (System.currentTimeMillis() - startTime) + "ms");
            return object;
        } catch (Exception e) {
            //记录异常时的原因
            logger.info("#Response : " + request.getRequestURL().toString() + " #Method : " + request.getMethod()
                    + " #Remote IP : " + request.getRemoteAddr() + " #Result : " + e.getMessage() + " #Finished : "
                    + (System.currentTimeMillis() - startTime) + "ms");
            throw e;
        }
    }
}
