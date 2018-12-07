package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Deck;
import com.adach.scrumote.exception.deck.CardNotFoundInDeckException;
import com.adach.scrumote.exception.deck.DeckNotFoundException;
import com.adach.scrumote.repository.DeckRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeckInternalService {

  private final DeckRepository repository;

  //region Repository methods calls
  public Deck findById(Long id) {
    Optional<Deck> deckOpt = repository.findById(id);
    return deckOpt.orElseThrow(
        () -> new DeckNotFoundException(String.format("Deck with id %d does not exist.", id)));
  }
  //endregion

  //region Validation methods
  public void validateContainsCardWithValue(Deck deck, String cardValue) {
    if (!deck.containsCardWithValue(cardValue)) {
      throw new CardNotFoundInDeckException(String
          .format("Deck with id %d does not contain card with value %s", deck.getId(), cardValue));
    }
  }
  //endregion
}
