package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.DtoTypeMap;
import com.adach.scrumote.dto.AbstractDto;
import com.adach.scrumote.entity.AbstractEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class AbstractMapper<E extends AbstractEntity, D extends AbstractDto>
    implements Mapper<E, D> {

  private final ModelMapper modelMapper;
  private final DtoTypeMap dtoTypeMap;

  @Override
  public D mapToDto(E entity) {
    return modelMapper.map(entity, dtoTypeMap.getDtoType(entity.getClass()));
  }

  @Override
  public E mapToEntity(D dto) {
    return modelMapper.map(dto, dtoTypeMap.getEntityType(dto.getClass()));
  }
}
