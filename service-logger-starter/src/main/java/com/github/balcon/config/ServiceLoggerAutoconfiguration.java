package com.github.balcon.config;

import com.github.balcon.logger.ServiceLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ServiceLoggerProperties.class)
@RequiredArgsConstructor
@ConditionalOnClass(ServiceLoggerProperties.class)
@ConditionalOnProperty(prefix = ServiceLoggerProperties.PREFIX,
        name = ServiceLoggerProperties.Fields.enabled, havingValue = "true")
public class ServiceLoggerAutoconfiguration {
    private final ServiceLoggerProperties properties;

    @Bean
    public ServiceLogger serviceLogger() {
        return new ServiceLogger(properties);
    }
}
