package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.dto.SimpleDtoTypeMap;
import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.dto.simple.CardSimpleDto;
import com.adach.scrumote.entity.Card;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@MandatoryTransactions
public class CardMapper extends AbstractSimpleDtoMapper<Card, CardSimpleDto> {

  public CardMapper(ModelMapper modelMapper, SimpleDtoTypeMap simpleDtoTypeMap) {
    super(modelMapper, simpleDtoTypeMap);
  }
}
