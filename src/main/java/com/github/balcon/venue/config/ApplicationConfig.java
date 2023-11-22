package com.github.balcon.venue.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.github.balcon.venue")
@PropertySource("classpath:application.properties")
public class ApplicationConfig {
}
