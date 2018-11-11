package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.dto.SimpleDtoTypeMap;
import com.adach.scrumote.dto.simple.UserHistorySimpleDto;
import com.adach.scrumote.entity.UserHistory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserHistoryMapper extends AbstractSimpleDtoMapper<UserHistory, UserHistorySimpleDto> {

  public UserHistoryMapper(ModelMapper modelMapper, SimpleDtoTypeMap simpleDtoTypeMap) {
    super(modelMapper, simpleDtoTypeMap);
  }
}
