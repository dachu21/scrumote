package com.adach.scrumote.exception.issue;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class IssueIterationIsCurrentlyActiveException extends ApplicationException {

  private static final String CODE = "exception.issue.iterationIsCurrentlyActive";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public IssueIterationIsCurrentlyActiveException(String message) {
    super(message, CODE, STATUS);
  }

  public IssueIterationIsCurrentlyActiveException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
