package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.simple.UserSimpleDto;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.entity.UserHistory;
import com.adach.scrumote.exception.systemfeature.RegistrationDisabledException;
import com.adach.scrumote.mapper.UserMapper;
import com.adach.scrumote.service.internal.RoleInternalService;
import com.adach.scrumote.service.internal.SystemFeatureInternalService;
import com.adach.scrumote.service.internal.UserInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserExternalService {

  private static final String REGISTRATION_FEATURE_CODE = "REGISTRATION";

  private final UserInternalService internalService;
  private final UserMapper mapper;

  private final RoleInternalService roleInternalService;
  private final SystemFeatureInternalService systemFeatureInternalService;
  private final PasswordEncoder passwordEncoder;

  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS', 'swagger')")
  public void registerUser(UserSimpleDto userSimpleDto) {
    validateRegistrationEnabled();
    registerOrCreateUser(userSimpleDto);
  }

  @PreAuthorize("hasAnyAuthority('createUser')")
  public void createUser(UserSimpleDto userSimpleDto) {
    registerOrCreateUser(userSimpleDto);
  }

  private void registerOrCreateUser(UserSimpleDto userSimpleDto) {
    User user = mapper.mapToEntity(userSimpleDto);
    user.getRoles().add(roleInternalService.findDeveloperRole());
    user.setActive(true);
    user.setPassword(passwordEncoder.encode(user.getPassword())); //TODO walidacja hasla

    UserHistory userHistory = UserHistory.createEmpty(user);
    user.setUserHistory(userHistory);

    internalService.save(user);
  }

  private void validateRegistrationEnabled() {
    if (!systemFeatureInternalService.findByCode(REGISTRATION_FEATURE_CODE).isEnabled()) {
      throw new RegistrationDisabledException("Registration is disabled.");
    }
  }
}
