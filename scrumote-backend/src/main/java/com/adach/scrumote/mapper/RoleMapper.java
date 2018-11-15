package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.dto.SimpleDtoTypeMap;
import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.dto.simple.RoleSimpleDto;
import com.adach.scrumote.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@MandatoryTransactions
public class RoleMapper extends AbstractSimpleDtoMapper<Role, RoleSimpleDto> {

  public RoleMapper(ModelMapper modelMapper, SimpleDtoTypeMap simpleDtoTypeMap) {
    super(modelMapper, simpleDtoTypeMap);
  }
}
