package com.adach.scrumote.exception.issue;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class IssueIterationCurrentlyActiveException extends ApplicationException {

  private static final String CODE = "exception.issue.iterationCurrentlyActive";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public IssueIterationCurrentlyActiveException(String message) {
    super(message, CODE, STATUS);
  }

  public IssueIterationCurrentlyActiveException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
