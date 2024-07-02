package com.riteny.util.exception.core.api;

import com.riteny.util.exception.config.CommonExceptionProperties;
import com.riteny.util.exception.core.exception.CommonException;
import com.riteny.util.exception.internationalization.InternationalizationDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
public class CommonApiExceptionHandlerAgent {

    private static final Logger logger = LoggerFactory.getLogger(CommonApiExceptionHandlerAgent.class);

    private final CommonExceptionProperties commonExceptionProperties;

    private final List<CommonApiExceptionHandler<?, ?>> commonApiExceptionHandlers;

    private final InternationalizationDatasource internationalizationDatasource;

    @Autowired
    public CommonApiExceptionHandlerAgent(CommonExceptionProperties commonExceptionProperties, List<CommonApiExceptionHandler<?, ?>> commonApiExceptionHandlers, InternationalizationDatasource internationalizationDatasource) {
        this.commonApiExceptionHandlers = commonApiExceptionHandlers;
        this.commonExceptionProperties = commonExceptionProperties;
        this.internationalizationDatasource = internationalizationDatasource;
    }

    public Object handleException(CommonException e, HttpServletRequest request, HttpServletResponse response) {

        if (CollectionUtils.isEmpty(commonApiExceptionHandlers)) {
            logger.warn("List of handler is empty .");
            return null;
        }

        Optional<CommonApiExceptionHandler<?, ?>> optional = commonApiExceptionHandlers.stream().filter(exceptionHandler ->
                exceptionHandler.getExceptionClass().equals(e.getClass().getTypeName())).findFirst();

        if (!optional.isPresent()) {
            logger.warn("Cannot find handler for exception type [{}] .", e.getClass().getTypeName());
            return null;
        }

        CommonApiExceptionHandler commonApiExceptionHandler = optional.get();

        if (commonExceptionProperties.getIsNeedInternationalization()) {
            String errorMessage = internationalizationDatasource.getValue(e.getErrorMsg(), e.getLangType());
            if (StringUtils.hasLength(errorMessage)) {
                e.setErrorMsg(errorMessage);
            }
        }

        return commonApiExceptionHandler.handler(e, request, response);
    }

}
