package com.adach.scrumote.exception.planning;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class PlanningCodeAlreadyExistsException extends ApplicationException {

  private static final String CODE = "exception.planning.codeExists";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public PlanningCodeAlreadyExistsException(String message) {
    super(message, CODE, STATUS);
  }

  public PlanningCodeAlreadyExistsException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
