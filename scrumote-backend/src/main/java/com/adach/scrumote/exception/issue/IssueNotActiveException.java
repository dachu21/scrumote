package com.adach.scrumote.exception.issue;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class IssueNotActiveException extends ApplicationException {

  private static final String CODE = "exception.issue.notActive";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public IssueNotActiveException(String message) {
    super(message, CODE, STATUS);
  }

  public IssueNotActiveException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
