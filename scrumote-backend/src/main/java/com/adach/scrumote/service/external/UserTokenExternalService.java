package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.complex.PasswordDto;
import com.adach.scrumote.dto.complex.UsernameWithEmailDto;
import com.adach.scrumote.dto.simple.UserSimpleDto;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.entity.UserToken;
import com.adach.scrumote.entity.UserToken.UserTokenType;
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
  public UserSimpleDto getUsernameForToken(UUID tokenValue) {
    UserToken userToken = internalService.findByValue(tokenValue);
    User user = userToken.getUser();
    UserSimpleDto userSimpleDto = new UserSimpleDto();
    userSimpleDto.setUsername(user.getUsername());
    return userSimpleDto;
  }

  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS')")
  public void createResetPasswordToken(UsernameWithEmailDto dto, String language) {
    User user = userInternalService.findByUsernameAndEmail(dto.getUsername(), dto.getEmail());
    UserToken userToken = internalService.saveOrUpdateToken(user, UserTokenType.RESET_PASSWORD);
    emailService.sendUserTokenEmail(userToken, language);
  }

  @SuppressWarnings("Duplicates")
  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS')")
  public void resetUserPassword(UUID tokenValue, PasswordDto passwordDto) {
    UserToken userToken = internalService
        .findByValueAndType(tokenValue, UserTokenType.RESET_PASSWORD);
    User user = userToken.getUser();

    passwordService.validateNotEqualToOldPassword(passwordDto.getNewPassword(), user);
    passwordService.validateSatisfiesConditions(passwordDto.getNewPassword());
    String encodedPassword = passwordService.encode(passwordDto.getNewPassword());

    user.setPassword(encodedPassword);
    internalService.delete(userToken);
  }

  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS')")
  public void createActivationToken(Long userId, String language) {
    User user = userInternalService.findById(userId);
    UserToken userToken = internalService.saveOrUpdateToken(user, UserTokenType.ACTIVATION);
    emailService.sendUserTokenEmail(userToken, language);
  }

  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS')")
  public void activateUser(UUID tokenValue) {
    UserToken userToken = internalService.findByValueAndType(tokenValue, UserTokenType.ACTIVATION);
    User user = userToken.getUser();

    user.setActive(true);
    internalService.delete(userToken);
  }
}
