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
  public DeckWithCardsDto getDeckWithCards(Long id) {
    Deck deck = internalService.findById(id);
    return mapper.mapToDtoWithCards(deck);
  }

  @PreAuthorize("hasAnyAuthority('updateDeck')")
  public void updateDeck(Long id, DeckWithCardsDto dto) {
    Deck deck = internalService.findById(id);
    validateDeckForUpdateOrDelete(deck);
  }

  @PreAuthorize("hasAnyAuthority('deleteDeck')")
  public void deleteDeck(Long id) {
    Deck deck = internalService.findById(id);
    validateDeckForUpdateOrDelete(deck);

    internalService.delete(deck);
  }

  private void validateDeckForUpdateOrDelete(Deck deck) {
    internalService.validateNotUsedInAnyPlanning(deck);
  }
}
