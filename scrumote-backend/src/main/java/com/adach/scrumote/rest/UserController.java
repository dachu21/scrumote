package com.adach.scrumote.rest;

import com.adach.scrumote.dto.simple.UserSimpleDto;
import com.adach.scrumote.mapper.UserMapper;
import com.adach.scrumote.service.UserService;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  @GetMapping("/login")
  public Principal user(Principal user) {
    return user;
  }

  @GetMapping("/users")
  public List<UserSimpleDto> getUsers() {
    return userService.findAll().stream().map(userMapper::mapToSimpleDto).collect(Collectors.toList());
  }

  @PostMapping("/users")
  public void addUser(@RequestBody UserSimpleDto userSimpleDto) {
    userService.save(userMapper.mapToEntity(userSimpleDto));
  }
}
