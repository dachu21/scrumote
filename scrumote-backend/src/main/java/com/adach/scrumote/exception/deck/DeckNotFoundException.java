package com.adach.scrumote.exception.deck;

import com.adach.scrumote.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DeckNotFoundException extends ApplicationException {

  private static final String CODE = "exception.deck.notFound";
  private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

  public DeckNotFoundException(String message) {
    super(message, CODE, STATUS);
  }

  public DeckNotFoundException(String message, Throwable cause) {
    super(message, CODE, STATUS, cause);
  }
}
