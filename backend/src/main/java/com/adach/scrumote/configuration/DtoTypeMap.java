package com.adach.scrumote.configuration;

import com.google.common.collect.BiMap;
import java.lang.reflect.Type;

public class DtoTypeMap {

  private final BiMap<Type, Type> typesMap;

  public DtoTypeMap(BiMap<Type, Type> typesMap) {
    this.typesMap = typesMap;
  }

  public Type getDtoType(Type entityType) {
    return typesMap.get(entityType);
  }

  public Type getEntityType(Type dtoType) {
    return typesMap.inverse().get(dtoType);
  }
}
