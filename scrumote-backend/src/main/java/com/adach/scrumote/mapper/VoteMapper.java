package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.dto.SimpleDtoTypeMap;
import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.dto.simple.VoteSimpleDto;
import com.adach.scrumote.entity.Vote;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@MandatoryTransactions
public class VoteMapper extends AbstractSimpleDtoMapper<Vote, VoteSimpleDto> {

  public VoteMapper(ModelMapper modelMapper, SimpleDtoTypeMap simpleDtoTypeMap) {
    super(modelMapper, simpleDtoTypeMap);
  }
}
