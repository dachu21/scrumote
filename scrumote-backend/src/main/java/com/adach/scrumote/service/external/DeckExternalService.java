package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.complex.DeckWithCardsDto;
import com.adach.scrumote.entity.Deck;
import com.adach.scrumote.mapper.DeckMapper;
import com.adach.scrumote.service.internal.DeckInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeckExternalService {

  private final DeckInternalService internalService;
  private final DeckMapper mapper;

  @PreAuthorize("hasAnyAuthority('createDeck')")
  public Long createDeck(DeckWithCardsDto dto) {
    Deck deck = mapper.mapToEntity(dto);
    internalService.validateCardsHaveValidLevels(deck);
    return internalService.save(deck).getId();
  }

  @PreAuthorize("hasAnyAuthority('getDeck')")
  public DeckWithCardsDto getDeckWithCards(Long deckId) {
    Deck deck = internalService.findById(deckId);
    return mapper.mapToDtoWithCards(deck);
  }

  @PreAuthorize("hasAnyAuthority('updateDeck')")
  public void updateDeck(Long deckId, Long version, DeckWithCardsDto dto) {
    Deck deck = internalService.findById(deckId);
    internalService.validateVersion(deck, version);
    validateDeckForUpdateOrDelete(deck);

    deck.setName(dto.getName());
    deck.removeAllCards();
    mapper.addNewCardsToDeck(deck, dto);
    internalService.validateCardsHaveValidLevels(deck);
  }

  @PreAuthorize("hasAnyAuthority('deleteDeck')")
  public void deleteDeck(Long deckId, Long version) {
    Deck deck = internalService.findById(deckId);
    internalService.validateVersion(deck, version);
    validateDeckForUpdateOrDelete(deck);

    internalService.delete(deck);
  }

  private void validateDeckForUpdateOrDelete(Deck deck) {
    internalService.validateNotUsedInAnyPlanning(deck);
  }
}
