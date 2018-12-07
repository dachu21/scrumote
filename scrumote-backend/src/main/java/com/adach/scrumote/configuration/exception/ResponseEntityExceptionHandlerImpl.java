package com.adach.scrumote.configuration.exception;

import com.adach.scrumote.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ResponseEntityExceptionHandlerImpl extends ResponseEntityExceptionHandler {

  private static final String INTEGRITY_VIOLATION_CODE = "exception.integrityViolation";
  private static final String VALIDATION_ERROR_CODE = "exception.validation";
  private static final String ACCESS_DENIED_CODE = "exception.accessDenied";
  private static final String INTERNAL_SERVER_ERROR_CODE = "exception.unknown";

  @ExceptionHandler(ApplicationException.class)
  public final ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e,
      WebRequest request) {
    log.error(e.getMessage(), e);
    ErrorResponse errorResponse = new ErrorResponse(e.getCode());
    return new ResponseEntity<>(errorResponse, e.getHttpStatus());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public final ResponseEntity<ErrorResponse> handleDIVException(DataIntegrityViolationException e,
      WebRequest request) {
    log.error(e.getMessage(), e);
    ErrorResponse errorResponse = new ErrorResponse(INTEGRITY_VIOLATION_CODE);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public final ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e,
      WebRequest request) {
    log.error(e.getMessage(), e);
    ErrorResponse errorResponse = new ErrorResponse(ACCESS_DENIED_CODE);
    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
  }

  @Override
  @SuppressWarnings("NullableProblems")
  public final ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    log.error(e.getMessage(), e);
    ErrorResponse errorResponse = new ErrorResponse(VALIDATION_ERROR_CODE);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception e, WebRequest request) {
    log.error(e.getMessage(), e);
    ErrorResponse errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR_CODE);
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
