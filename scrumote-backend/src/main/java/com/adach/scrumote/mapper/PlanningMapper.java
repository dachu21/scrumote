package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.dto.SimpleDtoTypeMap;
import com.adach.scrumote.dto.simple.PlanningSimpleDto;
import com.adach.scrumote.entity.Planning;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlanningMapper extends AbstractSimpleDtoMapper<Planning, PlanningSimpleDto> {

  public PlanningMapper(ModelMapper modelMapper, SimpleDtoTypeMap simpleDtoTypeMap) {
    super(modelMapper, simpleDtoTypeMap);
  }
}
