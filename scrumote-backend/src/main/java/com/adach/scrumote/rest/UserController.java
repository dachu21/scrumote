package com.adach.scrumote.rest;

import com.adach.scrumote.dto.simple.UserSimpleDto;
import com.adach.scrumote.service.external.UserExternalService;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController extends AbstractController {

  private final UserExternalService userExternalService;

  // Not @Secured - used by Spring Security.
  @GetMapping("/login")
  public Principal loginUser(Principal user) {
    return user;
  }

  @Secured({"ROLE_ANONYMOUS", "swagger"})
  @PostMapping("/register")
  public void registerUser(@Valid @RequestBody UserSimpleDto userSimpleDto) {
    userExternalService.register(userSimpleDto);
  }

  @Secured("admin")
  @GetMapping("/users")
  public List<UserSimpleDto> getUsers() {
    return userExternalService.findAll();
  }
}
