package com.adach.scrumote.exception.planning;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class PlanningNameAlreadyExistsException extends ApplicationException {

  private static final String CODE = "exception.planning.nameExists";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public PlanningNameAlreadyExistsException(String message) {
    super(message, CODE, STATUS);
  }

  public PlanningNameAlreadyExistsException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
