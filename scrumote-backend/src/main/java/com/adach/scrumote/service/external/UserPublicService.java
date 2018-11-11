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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserPublicService {

  private final UserRepository repository;
  private final UserMapper mapper;

  private final RoleInternalService roleInternalService;
  private final PasswordEncoder passwordEncoder;

  @Secured({"ROLE_ANONYMOUS", "swagger"})
  public void register(UserSimpleDto userSimpleDto) {
    User user = mapper.mapToEntity(userSimpleDto);
    user.getRoles().add(roleInternalService.findStandardUserRole());
    user.setActive(true); //TODO jakis mechanizm
    user.setPassword(passwordEncoder.encode(user.getPassword())); //TODO walidacja hasla
    repository.save(user);
  }

  @Secured("admin")
  public List<UserSimpleDto> findAll() {
    return repository.findAll().stream().map(mapper::mapToSimpleDto)
        .collect(Collectors.toList());
  }
}
