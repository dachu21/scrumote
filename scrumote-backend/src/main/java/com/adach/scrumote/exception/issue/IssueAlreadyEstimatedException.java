package com.adach.scrumote.exception.issue;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class IssueAlreadyEstimatedException extends ApplicationException {

  private static final String CODE = "exception.issue.alreadyEstimated";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public IssueAlreadyEstimatedException(String message) {
    super(message, CODE, STATUS);
  }

  public IssueAlreadyEstimatedException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
