package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Card;
import com.adach.scrumote.entity.Deck;
import com.adach.scrumote.exception.deck.CardNotFoundInDeckException;
import com.adach.scrumote.exception.deck.DeckNotFoundException;
import com.adach.scrumote.exception.deck.DeckUsedInPlanningsException;
import com.adach.scrumote.exception.deck.InvalidCardsLevelsInDeckException;
import com.adach.scrumote.repository.DeckRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeckInternalService extends AbstractInternalService<Deck> {

  private final DeckRepository repository;

  //region Repository methods calls
  public Deck save(Deck deck) {
    return repository.save(deck);
  }

  public Deck findById(Long id) {
    Optional<Deck> deckOpt = repository.findById(id);
    return deckOpt.orElseThrow(
        () -> new DeckNotFoundException(String.format("Deck with id %d does not exist.", id)));
  }

  public void delete(Deck deck) {
    repository.delete(deck);
  }
  //endregion

  //region Validation methods
  public void validateContainsCardWithValue(Deck deck, String cardValue) {
    if (!deck.containsCardWithValue(cardValue)) {
      throw new CardNotFoundInDeckException(String
          .format("Deck with id %d does not contain card with value %s", deck.getId(), cardValue));
    }
  }

  public void validateCardsHaveValidLevels(Deck deck) {
    List<Integer> orderedLevels = deck.getCards().stream().map(Card::getLevel).sorted().collect(
        Collectors.toList());
    int nextLevel = 1;
    for (Integer level : orderedLevels) {
      if (!level.equals(nextLevel++)) {
        throw new InvalidCardsLevelsInDeckException("Cards for new deck have invalid levels.");
      }
    }
  }

  public void validateNotUsedInAnyPlanning(Deck deck) {
    if (!deck.getPlannings().isEmpty()) {
      throw new DeckUsedInPlanningsException(
          String.format("Deck with id %d is used in existing plannings.", deck.getId()));
    }
  }
  //endregion

  public Map<String, Integer> getCardLevelsMapForDeck(Deck deck) {
    Map<String, Integer> cardsMap = new HashMap<>();
    deck.getCards().forEach(card -> cardsMap.put(card.getValue(), card.getLevel()));
    return cardsMap;
  }
}
