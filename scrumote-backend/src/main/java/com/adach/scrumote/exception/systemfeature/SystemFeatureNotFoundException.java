package com.adach.scrumote.exception.systemfeature;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class SystemFeatureNotFoundException extends ApplicationException {

  private static final String CODE = "exception.systemFeature.notFound";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public SystemFeatureNotFoundException(String message) {
    super(message, CODE, STATUS);
  }

  public SystemFeatureNotFoundException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
