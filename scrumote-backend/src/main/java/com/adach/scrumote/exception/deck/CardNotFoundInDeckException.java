package com.adach.scrumote.exception.deck;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class CardNotFoundInDeckException extends ApplicationException {

  private static final String CODE = "exception.deck.cardNotFound";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public CardNotFoundInDeckException(String message) {
    super(message, CODE, STATUS);
  }

  public CardNotFoundInDeckException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
