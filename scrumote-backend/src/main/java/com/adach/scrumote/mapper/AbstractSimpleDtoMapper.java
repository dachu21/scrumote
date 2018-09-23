package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.dto.SimpleDtoTypeMap;
import com.adach.scrumote.dto.simple.AbstractSimpleDto;
import com.adach.scrumote.entity.AbstractEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public abstract class AbstractSimpleDtoMapper<E extends AbstractEntity, D extends AbstractSimpleDto> {

  private final ModelMapper modelMapper;
  private final SimpleDtoTypeMap simpleDtoTypeMap;

  public D mapToSimpleDto(E entity) {
    return modelMapper.map(entity, simpleDtoTypeMap.getDtoType(entity.getClass()));
  }

  public E mapToEntity(D dto) {
    return modelMapper.map(dto, simpleDtoTypeMap.getEntityType(dto.getClass()));
  }
}
