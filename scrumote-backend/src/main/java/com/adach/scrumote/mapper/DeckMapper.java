package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.dto.SimpleDtoTypeMap;
import com.adach.scrumote.dto.simple.DeckSimpleDto;
import com.adach.scrumote.entity.Deck;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DeckMapper extends AbstractSimpleDtoMapper<Deck, DeckSimpleDto> {

  public DeckMapper(ModelMapper modelMapper, SimpleDtoTypeMap simpleDtoTypeMap) {
    super(modelMapper, simpleDtoTypeMap);
  }
}
