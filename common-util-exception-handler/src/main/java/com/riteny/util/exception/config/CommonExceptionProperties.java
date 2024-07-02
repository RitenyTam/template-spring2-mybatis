package com.riteny.util.exception.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "exception.handler")
public class CommonExceptionProperties {

    private Boolean isNeedInternationalization;

    private Integer profileType;

    public Boolean getIsNeedInternationalization() {
        return isNeedInternationalization;
    }

    public void setIsNeedInternationalization(Boolean isNeedInternationalization) {
        this.isNeedInternationalization = isNeedInternationalization;
    }

    public Integer getProfileType() {
        return profileType;
    }

    public void setProfileType(Integer profileType) {
        this.profileType = profileType;
    }
}
