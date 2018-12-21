package com.adach.scrumote.exception.optimisticlock;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class MissingVersionHeaderException extends ApplicationException {

  private static final String CODE = "exception.optimisticLock.missingVersionHeader";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public MissingVersionHeaderException(String message) {
    super(message, CODE, STATUS);
  }

  public MissingVersionHeaderException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
