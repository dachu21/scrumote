package com.adach.scrumote.rest;

import com.adach.scrumote.configuration.rest.PrefixedRestController;
import com.adach.scrumote.dto.simple.UserSimpleDto;
import com.adach.scrumote.service.external.UserExternalService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController extends AbstractController {

  private final UserExternalService userExternalService;

  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS', 'swagger')")
  @PostMapping("/register")
  public void registerUser(@Valid @RequestBody UserSimpleDto userSimpleDto) {
    userExternalService.registerUser(userSimpleDto);
  }

  @PreAuthorize("hasAnyAuthority('createUser')")
  @PostMapping("/users")
  public void createUser(@Valid @RequestBody UserSimpleDto userSimpleDto) {
    userExternalService.createUser(userSimpleDto);
  }
}
