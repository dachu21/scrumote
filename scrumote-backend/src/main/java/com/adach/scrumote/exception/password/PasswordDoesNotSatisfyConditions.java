package com.adach.scrumote.exception.password;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class PasswordDoesNotSatisfyConditions extends ApplicationException {

  private static final String CODE = "exception.password.doesNotSatisfyConditions";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public PasswordDoesNotSatisfyConditions(String message) {
    super(message, CODE, STATUS);
  }

  public PasswordDoesNotSatisfyConditions(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
