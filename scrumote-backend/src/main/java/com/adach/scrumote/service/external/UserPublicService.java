package com.adach.scrumote.service.external;

import com.adach.scrumote.dto.simple.UserSimpleDto;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.mapper.UserMapper;
import com.adach.scrumote.repository.UserRepository;
import com.adach.scrumote.service.internal.RoleInternalService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserPublicService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  private final RoleInternalService roleInternalService;
  private final PasswordEncoder passwordEncoder;

  public void register(UserSimpleDto userSimpleDto) {
    User user = userMapper.mapToEntity(userSimpleDto);
    user.getRoles().add(roleInternalService.findStandardUserRole());
    user.setActive(true); //TODO jakis mechanizm
    user.setPassword(passwordEncoder.encode(user.getPassword())); //TODO walidacja hasla
    userRepository.save(user);
  }

  public List<UserSimpleDto> findAll() {
    return userRepository.findAll().stream().map(userMapper::mapToSimpleDto)
        .collect(Collectors.toList());
  }
}
