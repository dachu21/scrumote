package com.adach.scrumote.exception.deck;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class InvalidCardsLevelsInDeckException extends ApplicationException {

  private static final String CODE = "exception.deck.invalidCardsLevels";
  private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

  public InvalidCardsLevelsInDeckException(String message) {
    super(message, CODE, STATUS);
  }

  public InvalidCardsLevelsInDeckException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
