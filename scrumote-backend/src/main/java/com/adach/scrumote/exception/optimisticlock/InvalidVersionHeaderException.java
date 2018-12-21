package com.adach.scrumote.exception.optimisticlock;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class InvalidVersionHeaderException extends ApplicationException {

  private static final String CODE = "exception.optimisticLock.invalidVersionHeader";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public InvalidVersionHeaderException(String message) {
    super(message, CODE, STATUS);
  }

  public InvalidVersionHeaderException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
