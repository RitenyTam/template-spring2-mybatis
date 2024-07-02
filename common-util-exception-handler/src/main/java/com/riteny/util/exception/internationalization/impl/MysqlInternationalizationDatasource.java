package com.riteny.util.exception.internationalization.impl;

import com.riteny.logger.CommonLoggerFactory;
import com.riteny.util.exception.internationalization.InternationalizationDatasource;
import org.slf4j.Logger;

import javax.sql.DataSource;

public class MysqlInternationalizationDatasource implements InternationalizationDatasource {

    private static final Logger logger = CommonLoggerFactory.getLogger("exception-properties");

    private DataSource dataSource;

    public MysqlInternationalizationDatasource(DataSource dataSource) {

    }

    @Override
    public String getValue(String index, String lang) {
        return "";
    }
}
