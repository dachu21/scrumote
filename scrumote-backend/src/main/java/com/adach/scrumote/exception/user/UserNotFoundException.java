package com.adach.scrumote.exception.user;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApplicationException {

  private static final String CODE = "exception.user.notFound";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public UserNotFoundException(String message) {
    super(message, CODE, STATUS);
  }

  public UserNotFoundException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
