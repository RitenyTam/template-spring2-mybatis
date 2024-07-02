package com.riteny.util.exception.internationalization.impl;


import com.alibaba.fastjson2.JSONObject;
import com.riteny.logger.CommonLoggerFactory;
import com.riteny.util.exception.internationalization.InternationalizationDatasource;
import org.slf4j.Logger;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PropertiesFileInternationalizationDatasource implements InternationalizationDatasource {

    private static final Logger logger = CommonLoggerFactory.getLogger("exception-properties");

    public PropertiesFileInternationalizationDatasource() {

        try (InputStream resourceAsStream = this.getClass().getResourceAsStream("/exception-i18n.json")) {

            if (resourceAsStream == null) {
                logger.error("Read properties for internationalization datasource failed . ");
                throw new RuntimeException("Read properties for internationalization datasource failed . ");
            }

            byte[] bytes = new byte[resourceAsStream.available()];

            int readSize = resourceAsStream.read(bytes);
            logger.info("Profile size [{}]. ", readSize);

            String jsonStr = new String(bytes);

            JSONObject propertiesJson = JSONObject.parseObject(jsonStr);

            propertiesJson.forEach((k, v) -> {

                logger.info("Internationalization profile key [ {} ]. ", k);

                JSONObject value = JSONObject.parseObject(propertiesJson.get(k).toString());

                value.forEach((k2, v2) -> {
                    Map<String, String> contextMap = internationalizationProfileMap.computeIfAbsent(k, k3 -> new ConcurrentHashMap<>());
                    contextMap.put(k2, value.getString(k2));
                    logger.info("Internationalization profile value [ key = {} , value = {} ]. ", k2, value.getString(k2));
                });
            });

        } catch (Exception e) {
            logger.error("Internationalization datasource init failed . ");
            throw new RuntimeException("Internationalization datasource init failed . ", e);
        }
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
