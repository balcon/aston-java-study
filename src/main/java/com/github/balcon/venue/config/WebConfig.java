package com.github.balcon.venue.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Profile("!test")
@Configuration
@EnableWebMvc
public class WebConfig {
    public static final String API = "/api";
}
