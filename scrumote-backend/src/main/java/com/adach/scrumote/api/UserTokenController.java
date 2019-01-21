package com.adach.scrumote.api;

import com.adach.scrumote.configuration.api.PrefixedRestController;
import com.adach.scrumote.dto.complex.PasswordDto;
import com.adach.scrumote.dto.complex.UsernameWithEmailDto;
import com.adach.scrumote.dto.simple.UserSimpleDto;
import com.adach.scrumote.service.external.UserTokenExternalService;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserTokenController extends AbstractController {

  private final UserTokenExternalService userTokenExternalService;

  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS')")
  @GetMapping("/user-tokens/{tokenValue}/username")
  public UserSimpleDto getUsernameForToken(@PathVariable UUID tokenValue) {
    return userTokenExternalService.getUsernameForToken(tokenValue);
  }

  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS')")
  @PostMapping("/user-tokens/reset-password")
  public ResponseEntity<?> createResetPasswordToken(@RequestBody @Valid UsernameWithEmailDto dto,
      @RequestParam @NotNull String language) {
    userTokenExternalService.createResetPasswordToken(dto, language);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS')")
  @PutMapping("/user-tokens/reset-password/{tokenValue}")
  public ResponseEntity<?> resetUserPassword(@PathVariable UUID tokenValue,
      @RequestBody @Valid PasswordDto passwordDto) {
    userTokenExternalService.resetUserPassword(tokenValue, passwordDto);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS')")
  @PutMapping("/user-tokens/activation/{tokenValue}")
  public ResponseEntity<?> activateUser(@PathVariable UUID tokenValue) {
    userTokenExternalService.activateUser(tokenValue);
    return ResponseEntity.noContent().build();
  }
}
