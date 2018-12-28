package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.complex.PasswordDto;
import com.adach.scrumote.dto.complex.UserWithPasswordDto;
import com.adach.scrumote.dto.simple.UserSimpleDto;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.entity.UserHistory;
import com.adach.scrumote.exception.systemfeature.RegistrationDisabledException;
import com.adach.scrumote.mapper.UserMapper;
import com.adach.scrumote.service.internal.PlanningInternalService;
import com.adach.scrumote.service.internal.RoleInternalService;
import com.adach.scrumote.service.internal.SystemFeatureInternalService;
import com.adach.scrumote.service.internal.UserInternalService;
import com.adach.scrumote.service.security.PasswordService;
import com.adach.scrumote.service.security.SessionService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
  private final PlanningInternalService planningInternalService;

  private final PasswordService passwordService;
  private final SessionService sessionService;

  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS', 'swagger')")
  public Long registerUser(UserWithPasswordDto dto) {
    validateRegistrationEnabled();
    return registerOrCreateUser(dto);
  }

  @PreAuthorize("hasAnyAuthority('createUser')")
  public Long createUser(UserWithPasswordDto dto) {
    return registerOrCreateUser(dto);
  }

  private Long registerOrCreateUser(UserWithPasswordDto dto) {
    User user = mapper.mapToEntity(dto.getUser());

    String textPassword = dto.getPassword().getNewPassword();
    passwordService.validateSatisfiesConditions(textPassword);
    String encodedPassword = passwordService.encode(textPassword);
    user.setPassword(encodedPassword);

    user.getRoles().add(roleInternalService.findDeveloperRole());

    UserHistory userHistory = UserHistory.createEmpty(user);
    user.setUserHistory(userHistory);

    user.setActive(true);

    return internalService.save(user).getId();
  }

  @PreAuthorize("hasAnyAuthority('getMyUser')")
  public UserSimpleDto getMyUser() {
    User currentUser = sessionService.getCurrentUser();
    return mapper.mapToSimpleDto(currentUser);
  }

  @PreAuthorize("hasAnyAuthority('getAnyUser')")
  public UserSimpleDto getAnyUser(Long userId) {
    User user = internalService.findById(userId);
    return mapper.mapToSimpleDto(user);
  }

  @PreAuthorize("hasAnyAuthority('getAllUsers')")
  public List<UserSimpleDto> getAllUsers() {
    return internalService.findAll().stream().map(mapper::mapToSimpleDto)
        .collect(Collectors.toList());
  }

  @PreAuthorize("hasAnyAuthority('getUsersForPlanning')")
  public List<UserSimpleDto> getUsersForPlanning(Long planningId) {
    Planning planning = planningInternalService.findById(planningId);
    planningInternalService.validateContainsCurrentUserIfNotAuthorized(planning);

    return planning.getUsers().stream().map(mapper::mapToSimpleDto)
        .collect(Collectors.toList());
  }

  @PreAuthorize("hasAnyAuthority('updateMyUser')")
  public void updateMyUser(Long version, UserSimpleDto dto) {
    User currentUser = sessionService.getCurrentUser();
    internalService.validateVersion(currentUser, version);
    updateUserFields(currentUser, dto);
  }

  @PreAuthorize("hasAnyAuthority('updateAnyUser')")
  public void updateAnyUser(Long userId, Long version, UserSimpleDto dto) {
    User user = internalService.findById(userId);
    internalService.validateVersion(user, version);
    updateUserFields(user, dto);
  }

  private void updateUserFields(User user, UserSimpleDto dto) {
    user.setUsername(dto.getUsername());
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setEmail(dto.getEmail());
  }

  @PreAuthorize("hasAnyAuthority('updateMyUsersPassword')")
  public void updateMyUsersPassword(Long version, PasswordDto dto) {
    User currentUser = sessionService.getCurrentUser();
    internalService.validateVersion(currentUser, version);

    passwordService.validateEqualToOldPassword(dto.getOldPassword(), currentUser);
    passwordService.validateNotEqualToOldPassword(dto.getNewPassword(), currentUser);
    passwordService.validateSatisfiesConditions(dto.getNewPassword());

    String encodedPassword = passwordService.encode(dto.getNewPassword());
    currentUser.setPassword(encodedPassword);
  }

  @PreAuthorize("hasAnyAuthority('updateAnyUsersPassword')")
  public void updateAnyUsersPassword(Long userId, Long version, PasswordDto dto) {
    User user = internalService.findById(userId);
    internalService.validateVersion(user, version);

    passwordService.validateSatisfiesConditions(dto.getNewPassword());

    String encodedPassword = passwordService.encode(dto.getNewPassword());
    user.setPassword(encodedPassword);
  }

  private void validateRegistrationEnabled() {
    if (!systemFeatureInternalService.findByCode(REGISTRATION_FEATURE_CODE).isEnabled()) {
      throw new RegistrationDisabledException("Registration is disabled.");
    }
  }
}
