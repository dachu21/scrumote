package com.adach.scrumote.exception.password;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class OldPasswordNotValidException extends ApplicationException {

  private static final String CODE = "exception.password.oldNotValid";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public OldPasswordNotValidException(String message) {
    super(message, CODE, STATUS);
  }

  public OldPasswordNotValidException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
