package com.riteny.util.exception.internationalization.impl;

import com.riteny.logger.CommonLoggerFactory;
import com.riteny.util.exception.internationalization.InternationalizationDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryInternationalizationDatasource implements InternationalizationDatasource {

    private static final Logger logger = CommonLoggerFactory.getLogger("exception-properties");

    public void putValue(String index, String value, String lang) {
        logger.info("Put profile value [ key = {} , value = {} ] to profile key [ {} ]. ", index, value, lang);
        Map<String, String> contextMap = internationalizationProfileMap.computeIfAbsent(lang, k -> new ConcurrentHashMap<>());

        contextMap.put(index, value);
    }

    @Override
    public String getValue(String index, String lang) {

        Map<String, String> contextMap = internationalizationProfileMap.get(lang);

        if (contextMap == null) {
            return index;
        }

        return contextMap.get(index);
    }
}
