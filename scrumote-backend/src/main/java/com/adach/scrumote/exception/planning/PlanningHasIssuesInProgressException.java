package com.adach.scrumote.exception.planning;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class PlanningHasIssuesInProgressException extends ApplicationException {

  private static final String CODE = "exception.planning.hasIssuesInProgress";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public PlanningHasIssuesInProgressException(String message) {
    super(message, CODE, STATUS);
  }

  public PlanningHasIssuesInProgressException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
