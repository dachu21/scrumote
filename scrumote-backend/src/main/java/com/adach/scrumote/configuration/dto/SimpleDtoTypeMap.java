package com.adach.scrumote.configuration.dto;

import com.google.common.collect.BiMap;
import java.lang.reflect.Type;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleDtoTypeMap {

  private final BiMap<Type, Type> typesMap;

  public Type getDtoType(Type entityType) {
    return typesMap.get(entityType);
  }

  public Type getEntityType(Type dtoType) {
    return typesMap.inverse().get(dtoType);
  }
}
