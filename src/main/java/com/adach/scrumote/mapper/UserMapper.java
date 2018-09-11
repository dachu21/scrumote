package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.DtoTypeMap;
import com.adach.scrumote.dto.UserDto;
import com.adach.scrumote.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User, UserDto> {

  @Autowired
  public UserMapper(ModelMapper modelMapper, DtoTypeMap dtoTypeMap) {
    super(modelMapper, dtoTypeMap);
  }
}
