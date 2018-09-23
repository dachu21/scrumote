package com.adach.scrumote.rest.controller;

import com.adach.scrumote.dto.UserDto;
import com.adach.scrumote.mapper.UserMapper;
import com.adach.scrumote.rest.PrefixedRestController;
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
  public List<UserDto> getUsers() {
    return userService.findAll().stream().map(userMapper::mapToDto).collect(Collectors.toList());
  }

  @PostMapping("/users")
  public void addUser(@RequestBody UserDto userDto) {
    userService.save(userMapper.mapToEntity(userDto));
  }
}