package com.adach.scrumote.configuration.dto;

import com.adach.scrumote.dto.simple.UserSimpleDto;
import com.adach.scrumote.entity.User;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.lang.reflect.Type;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DtoConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public SimpleDtoTypeMap simpleDtoTypeMap() {
    BiMap<Type, Type> typesMap = HashBiMap.create();
    typesMap.put(User.class, UserSimpleDto.class);
    return new SimpleDtoTypeMap(typesMap);
  }
}
