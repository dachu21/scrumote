package com.adach.scrumote.exception.deck;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DeckUsedInPlanningsException extends ApplicationException {

  private static final String CODE = "exception.deck.usedInPlannings";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public DeckUsedInPlanningsException(String message) {
    super(message, CODE, STATUS);
  }

  public DeckUsedInPlanningsException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
