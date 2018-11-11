package com.adach.scrumote.configuration.exception;

import com.adach.scrumote.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@Slf4j
public class ResponseEntityExceptionHandlerImpl extends ResponseEntityExceptionHandler {

  private static final String INTERNAL_SERVER_ERROR_CODE = "exception.unknown";

  @ExceptionHandler(ApplicationException.class)
  public final ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e,
      WebRequest request) {
    log.error(e.getMessage(), e);
    ErrorResponse errorResponse = new ErrorResponse(e.getCode());
    return new ResponseEntity<>(errorResponse, e.getHttpStatus());
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception e, WebRequest request) {
    log.error(e.getMessage(), e);
    ErrorResponse errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR_CODE);
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
