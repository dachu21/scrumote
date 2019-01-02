package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.dto.SimpleDtoTypeMap;
import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.dto.simple.UserStatsSimpleDto;
import com.adach.scrumote.entity.UserStats;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@MandatoryTransactions
public class UserStatsMapper extends AbstractSimpleDtoMapper<UserStats, UserStatsSimpleDto> {

  @Autowired
  public UserStatsMapper(ModelMapper modelMapper, SimpleDtoTypeMap simpleDtoTypeMap) {
    super(modelMapper, simpleDtoTypeMap);
  }
}