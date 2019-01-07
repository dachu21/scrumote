package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.dto.SimpleDtoTypeMap;
import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.dto.simple.PlanningSimpleDto;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.entity.User;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@MandatoryTransactions
public class PlanningMapper extends AbstractSimpleDtoMapper<Planning, PlanningSimpleDto> {

  @Autowired
  public PlanningMapper(ModelMapper modelMapper, SimpleDtoTypeMap simpleDtoTypeMap) {
    super(modelMapper, simpleDtoTypeMap);
  }

  @Override
  public PlanningSimpleDto mapToSimpleDto(Planning planning) {
    PlanningSimpleDto planningSimpleDto = super.mapToSimpleDto(planning);
    planningSimpleDto.getUsersUsernames().addAll(
        planning.getUsers().stream().map(User::getUsername).collect(Collectors.toList())
    );
    return planningSimpleDto;
  }
}
