package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.dto.SimpleDtoTypeMap;
import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.dto.simple.UserSimpleDto;
import com.adach.scrumote.entity.Role;
import com.adach.scrumote.entity.User;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@MandatoryTransactions
public class UserMapper extends AbstractSimpleDtoMapper<User, UserSimpleDto> {

  @Autowired
  public UserMapper(ModelMapper modelMapper, SimpleDtoTypeMap simpleDtoTypeMap) {
    super(modelMapper, simpleDtoTypeMap);
  }

  @Override
  public UserSimpleDto mapToSimpleDto(User user) {
    UserSimpleDto userSimpleDto = super.mapToSimpleDto(user);
    userSimpleDto.getRoleNames().addAll(
        user.getRoles().stream().map(Role::getName).collect(Collectors.toList())
    );
    return userSimpleDto;
  }
}
