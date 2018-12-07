package com.adach.scrumote.exception.planning;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class PlanningNotFinishedException extends ApplicationException {

  private static final String CODE = "exception.planning.notFinished";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public PlanningNotFinishedException(String message) {
    super(message, CODE, STATUS);
  }

  public PlanningNotFinishedException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
