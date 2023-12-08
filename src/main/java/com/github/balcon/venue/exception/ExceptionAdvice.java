package com.github.balcon.venue.exception;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler advice for rest controllers.
 *
 * @author Konstantin Balykov
 */

@RestControllerAdvice
public class ExceptionAdvice {
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
    return e.getMessage();
  }

  /**
   * Handle validation exceptions.
   *
   * @param e Exception
   * @return Map of validation errors
   */

  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> validationErrorHandle(BindException e) {
    return e.getFieldErrors().stream()
            .collect(Collectors.toMap(FieldError::getField,
                    fieldError -> Optional.ofNullable(fieldError.getDefaultMessage())
                            .orElse("Validation error")));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String otherExceptionsHandler(Exception e) {
    return e.toString();
  }
}
