package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.dto.SimpleDtoTypeMap;
import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.dto.simple.SystemFeatureSimpleDto;
import com.adach.scrumote.entity.SystemFeature;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@MandatoryTransactions
public class SystemFeatureMapper extends
    AbstractSimpleDtoMapper<SystemFeature, SystemFeatureSimpleDto> {

  @Autowired
  public SystemFeatureMapper(ModelMapper modelMapper, SimpleDtoTypeMap simpleDtoTypeMap) {
    super(modelMapper, simpleDtoTypeMap);
  }
}
