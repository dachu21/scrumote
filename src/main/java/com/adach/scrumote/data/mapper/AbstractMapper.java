package com.adach.scrumote.data.mapper;

import com.adach.scrumote.configuration.DtoTypeMap;
import com.adach.scrumote.data.dto.AbstractDto;
import com.adach.scrumote.data.entity.AbstractEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractMapper<E extends AbstractEntity, D extends AbstractDto>
    implements Mapper<E, D> {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private DtoTypeMap dtoMap;

  @Override
  public D mapToDto(E entity) {
    return modelMapper.map(entity, dtoMap.getDtoType(entity.getClass()));
  }

  @Override
  public E mapToEntity(D dto) {
    return modelMapper.map(dto, dtoMap.getEntityType(dto.getClass()));
  }
}
