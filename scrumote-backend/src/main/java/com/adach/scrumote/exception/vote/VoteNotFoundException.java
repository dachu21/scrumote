package com.adach.scrumote.exception.vote;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class VoteNotFoundException extends ApplicationException {

  private static final String CODE = "exception.vote.notFound";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public VoteNotFoundException(String message) {
    super(message, CODE, STATUS);
  }

  public VoteNotFoundException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
