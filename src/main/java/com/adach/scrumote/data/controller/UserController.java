package com.adach.scrumote.data.controller;

import com.adach.scrumote.data.dto.user.UserDto;
import com.adach.scrumote.data.mapper.user.UserMapper;
import com.adach.scrumote.data.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  UserMapper userMapper;

  @GetMapping("/users")
  public List<UserDto> getUsers() {
    return userService.findAll().stream().map(userMapper::mapToDto).collect(Collectors.toList());
  }

  @PostMapping("/users")
  public void addUser(@RequestBody UserDto userDto) {
    userService.save(userMapper.mapToEntity(userDto));
  }
}
