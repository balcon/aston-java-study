package com.github.balcon.venue.config;

import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

// https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet.html
public class WebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(jakarta.servlet.ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ApplicationConfig.class);
        DispatcherServlet servlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = servletContext.addServlet("root", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/*");
    }
}
