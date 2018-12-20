package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.dto.SimpleDtoTypeMap;
import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.dto.complex.DeckWithCardsDto;
import com.adach.scrumote.dto.simple.DeckSimpleDto;
import com.adach.scrumote.entity.Deck;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@MandatoryTransactions
public class DeckMapper extends AbstractSimpleDtoMapper<Deck, DeckSimpleDto> {

  private final CardMapper cardMapper;

  @Autowired
  public DeckMapper(ModelMapper modelMapper, SimpleDtoTypeMap simpleDtoTypeMap,
      CardMapper cardMapper) {
    super(modelMapper, simpleDtoTypeMap);
    this.cardMapper = cardMapper;
  }

  public DeckWithCardsDto mapToDtoWithCards(Deck deck) {
    DeckWithCardsDto dto = new DeckWithCardsDto();
    dto.setId(deck.getId());
    dto.setName(deck.getName());
    deck.getCards().stream()
        .map(cardMapper::mapToSimpleDto)
        .forEach(card -> dto.getCards().add(card));
    return dto;
  }

  public Deck mapToEntity(DeckWithCardsDto dto) {
    Deck deck = new Deck();
    deck.setName(dto.getName());
    dto.getCards().stream()
        .map(cardMapper::mapToEntity)
        .forEach(card -> {
          card.setDeck(deck);
          deck.getCards().add(card);
        });
    return deck;
  }
}
