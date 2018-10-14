package com.adach.scrumote.rest;

import com.adach.scrumote.dto.simple.UserSimpleDto;
import com.adach.scrumote.service.external.UserPublicService;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private final UserPublicService userPublicService;

  @GetMapping("/login")
  public Principal loginUser(Principal user) {
    return user;
  }

  @PostMapping("/register")
  public void registerUser(@Valid @RequestBody UserSimpleDto userSimpleDto) {
    userPublicService.register(userSimpleDto);
  }

  @GetMapping("/users")
  public List<UserSimpleDto> getUsers() {
    return userPublicService.findAll();
  }
}
