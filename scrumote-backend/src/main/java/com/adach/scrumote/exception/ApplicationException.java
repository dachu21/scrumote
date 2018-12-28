package com.adach.scrumote.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {

  private final String code;
  private final HttpStatus httpStatus;

  public ApplicationException(String message, String code, HttpStatus httpStatus) {
    super(message);
    this.code = code;
    this.httpStatus = httpStatus;
  }

  public ApplicationException(String message, String code, HttpStatus httpStatus, Throwable cause) {
    super(message, cause);
    this.code = code;
    this.httpStatus = httpStatus;
  }
}
