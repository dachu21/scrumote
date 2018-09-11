package com.adach.scrumote.mapper;

public interface Mapper<E, D> {

  D mapToDto(E entity);

  E mapToEntity(D dto);
}
