package com.github.balcon.logger;

import com.github.balcon.config.ServiceLoggerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect for logging in service.
 *
 * @author Konstantin Balykov
 */

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

  /**
   * Method description.
   */

  @AfterReturning("isService()")
  public void logAfterReturning(JoinPoint joinPoint) {
    String method = joinPoint.getSignature().toShortString();
    log.info("Successful call {}", method);
  }

  /**
   * Method description.
   */

  @AfterThrowing(pointcut = "isService()", throwing = "exception")
  public void logAfterException(JoinPoint joinPoint, Exception exception) {
    String method = joinPoint.getSignature().toShortString();
    log.warn("{} throw {}", method, properties.isExceptionsTypeOnly()
            ? exception.getClass().getSimpleName()
            : exception.toString());
  }
}
