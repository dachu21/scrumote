package com.adach.scrumote.configuration.security;

import com.adach.scrumote.api.AbstractController;
import com.adach.scrumote.configuration.api.PrefixedRestController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController extends AbstractController {

  @GetMapping("/login")
  public SessionInfoDto login() {
    return SessionInfoDto.get();
  }
}
