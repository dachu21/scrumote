package com.adach.scrumote.exception.user;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends ApplicationException {

  private static final String CODE = "exception.user.usernameExists";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public UsernameAlreadyExistsException(String message) {
    super(message, CODE, STATUS);
  }

  public UsernameAlreadyExistsException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
