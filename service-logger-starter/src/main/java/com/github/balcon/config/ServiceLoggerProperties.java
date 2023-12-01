package com.github.balcon.config;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(ServiceLoggerProperties.PREFIX)
@FieldNameConstants
@Getter
@Setter
public class ServiceLoggerProperties {
    public static final String PREFIX = "app.service.logger";

    /**
     * Enable service layer logging
     */
    private boolean enabled;

    /**
     * Log exceptions type only
     */
    private boolean exceptionsTypeOnly;
}
