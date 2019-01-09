package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.complex.PasswordDto;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.entity.UserToken;
import com.adach.scrumote.entity.UserToken.TokenType;
import com.adach.scrumote.service.email.EmailService;
import com.adach.scrumote.service.internal.UserInternalService;
import com.adach.scrumote.service.internal.UserTokenInternalService;
import com.adach.scrumote.service.security.PasswordService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserTokenExternalService {

  private final UserTokenInternalService internalService;

  private final UserInternalService userInternalService;

  private final PasswordService passwordService;
  private final EmailService emailService;

  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS')")
  public String getUsernameForToken(UUID tokenValue) {
    UserToken userToken = internalService.findByValue(tokenValue);
    User user = userToken.getUser();
    return user.getUsername();
  }

  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS')")
  public void createResetPasswordToken(String email, String language) {
    User user = userInternalService.findByEmail(email);
    UserToken userToken = internalService.saveOrUpdateToken(user, TokenType.RESET_PASSWORD);
    emailService.sendResetPasswordEmail(userToken, language);
  }

  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS')")
  public void resetUserPassword(UUID tokenValue, PasswordDto passwordDto) {
    UserToken userToken = internalService.findByValueAndType(tokenValue, TokenType.RESET_PASSWORD);
    User user = userToken.getUser();

    passwordService.validateSatisfiesConditions(passwordDto.getNewPassword());
    String encodedPassword = passwordService.encode(passwordDto.getNewPassword());

    user.setPassword(encodedPassword);
  }

  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS')")
  public void activateUser(UUID tokenValue) {
    UserToken userToken = internalService.findByValueAndType(tokenValue, TokenType.ACTIVATION);
    User user = userToken.getUser();
    user.setActive(true);
  }
}
