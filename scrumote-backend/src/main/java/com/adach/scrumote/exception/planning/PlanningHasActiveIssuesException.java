package com.adach.scrumote.exception.planning;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class PlanningHasActiveIssuesException extends ApplicationException {

  private static final String CODE = "exception.planning.hasActiveIssues";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public PlanningHasActiveIssuesException(String message) {
    super(message, CODE, STATUS);
  }

  public PlanningHasActiveIssuesException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
