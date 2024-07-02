package com.riteny.util.exception.config;

import com.riteny.util.exception.internationalization.InternationalizationDatasource;
import com.riteny.util.exception.internationalization.impl.InMemoryInternationalizationDatasource;
import com.riteny.util.exception.internationalization.impl.PropertiesFileInternationalizationDatasource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionHandlerConfiguration {

    @Bean
    @ConditionalOnExpression("${exception.handler.profile-type:0} == 1 && ${exception.handler.is-need-internationalization}")
    public InternationalizationDatasource memoryInternationalizationDatasource() {
        return new InMemoryInternationalizationDatasource();
    }

    @Bean
    @ConditionalOnExpression("${exception.handler.profile-type:0} == 2 && ${exception.handler.is-need-internationalization}")
    public InternationalizationDatasource propertiesInternationalizationDatasource() {
        return new PropertiesFileInternationalizationDatasource();
    }

    @Bean
    @ConditionalOnExpression("!${exception.handler.is-need-internationalization}")
    public InternationalizationDatasource defaultInternationalizationDatasource() {
        return (index, lang) -> index;
    }
}
