package com.riteny.config;

import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Riteny
 * 2020/1/13  12:02
 */
@Configuration
public class ApiLogAspectConfig {

    @Bean
    public AspectJExpressionPointcutAdvisor apiAdviceAdvisor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression("execution(public * com.riteny.*.controller..*.*(..))");
        advisor.setAdvice(new ApiLogAspect());
        return advisor;
    }
}
