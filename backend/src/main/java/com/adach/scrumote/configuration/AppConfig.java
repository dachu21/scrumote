package com.adach.scrumote.configuration;

import com.adach.scrumote.dto.UserDto;
import com.adach.scrumote.entity.User;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.lang.reflect.Type;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public DtoTypeMap dtoTypeMap() {
    BiMap<Type, Type> typesMap = HashBiMap.create();
    typesMap.put(User.class, UserDto.class);
    return new DtoTypeMap(typesMap);
  }
}
