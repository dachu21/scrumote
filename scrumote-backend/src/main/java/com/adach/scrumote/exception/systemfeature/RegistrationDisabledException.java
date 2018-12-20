package com.adach.scrumote.exception.systemfeature;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class RegistrationDisabledException extends ApplicationException {

  private static final String CODE = "exception.systemFeature.registrationDisabled";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public RegistrationDisabledException(String message) {
    super(message, CODE, STATUS);
  }

  public RegistrationDisabledException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
