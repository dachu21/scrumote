package com.adach.scrumote.exception.optimisticlock;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class OutdatedEntityVersionException extends ApplicationException {

  private static final String CODE = "exception.optimisticLock.outdatedVersion";
  private static final HttpStatus STATUS = HttpStatus.PRECONDITION_FAILED;

  public OutdatedEntityVersionException(String message) {
    super(message, CODE, STATUS);
  }

  public OutdatedEntityVersionException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
