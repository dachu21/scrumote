package com.adach.scrumote.exception.planning;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class PlanningForbiddenException extends ApplicationException {

  private static final String CODE = "exception.planning.forbidden";
  private static final HttpStatus STATUS = HttpStatus.FORBIDDEN;

  public PlanningForbiddenException(String message) {
    super(message, CODE, STATUS);
  }

  public PlanningForbiddenException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
