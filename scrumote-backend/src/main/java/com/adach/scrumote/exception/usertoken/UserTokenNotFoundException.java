package com.adach.scrumote.exception.usertoken;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class UserTokenNotFoundException extends ApplicationException {

  private static final String CODE = "exception.userToken.notFound";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public UserTokenNotFoundException(String message) {
    super(message, CODE, STATUS);
  }

  public UserTokenNotFoundException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
