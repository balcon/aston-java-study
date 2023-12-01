package com.github.balcon.logger;

import com.github.balcon.config.ServiceLoggerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;


@Aspect
@Slf4j
@RequiredArgsConstructor
public class ServiceLogger {
    private final ServiceLoggerProperties properties;

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isService() {
    }

    @Before("isService()")
    public void logBefore(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        log.info("Attempt to call {}", method);
    }

    @AfterReturning("isService()")
    public void logAfterReturning(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        log.info("Successful call {}", method);
    }

    @AfterThrowing(pointcut = "isService()", throwing = "exception")
    public void logAfterException(JoinPoint joinPoint, Exception exception) {
        String method = joinPoint.getSignature().toShortString();
        log.warn("{} throw {}", method, properties.isExceptionsTypeOnly()
                ? exception.getClass().getSimpleName()
                : exception.toString());
    }
}
