package com.adach.scrumote.exception.planning;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class PlanningNotFoundException extends ApplicationException {

  private static final String CODE = "exception.planningNotFound";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public PlanningNotFoundException(String message) {
    super(message, CODE, STATUS);
  }

  public PlanningNotFoundException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
