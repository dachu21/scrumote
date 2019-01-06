package com.adach.scrumote.exception.user;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends ApplicationException {

  private static final String CODE = "exception.user.emailExists";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public EmailAlreadyExistsException(String message) {
    super(message, CODE, STATUS);
  }

  public EmailAlreadyExistsException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
