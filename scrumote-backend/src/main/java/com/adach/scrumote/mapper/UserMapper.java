package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.dto.SimpleDtoTypeMap;
import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.dto.simple.UserSimpleDto;
import com.adach.scrumote.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@MandatoryTransactions
public class UserMapper extends AbstractSimpleDtoMapper<User, UserSimpleDto> {

  public UserMapper(ModelMapper modelMapper, SimpleDtoTypeMap simpleDtoTypeMap) {
    super(modelMapper, simpleDtoTypeMap);
  }
}
