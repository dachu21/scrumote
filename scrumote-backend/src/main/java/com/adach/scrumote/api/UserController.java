package com.adach.scrumote.api;

import com.adach.scrumote.configuration.api.PrefixedRestController;
import com.adach.scrumote.dto.complex.PasswordDto;
import com.adach.scrumote.dto.complex.UserWithPasswordDto;
import com.adach.scrumote.dto.simple.UserSimpleDto;
import com.adach.scrumote.service.external.UserExternalService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController extends AbstractController {

  private final UserExternalService userExternalService;

  @Override
  URI buildLocationUri(Long id) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/../{id}")
        .buildAndExpand(id)
        .normalize()
        .toUri();
  }

  @PreAuthorize("hasAnyAuthority('ROLE_ANONYMOUS', 'swagger')")
  @PostMapping("/users/register")
  public ResponseEntity<?> registerUser(@RequestBody @Valid UserWithPasswordDto dto) {
    Long newUserId = userExternalService.registerUser(dto);
    URI location = buildLocationUri(newUserId);

    return ResponseEntity.created(location).build();
  }

  @PreAuthorize("hasAnyAuthority('createUser')")
  @PostMapping("/users/create")
  public ResponseEntity<?> createUser(@RequestBody @Valid UserWithPasswordDto dto) {
    Long newUserId = userExternalService.createUser(dto);
    URI location = buildLocationUri(newUserId);
    return ResponseEntity.created(location).build();
  }

  @PreAuthorize("hasAnyAuthority('getMyUser')")
  @GetMapping("/users/my")
  public UserSimpleDto getMyUser() {
    return userExternalService.getMyUser();
  }

  @PreAuthorize("hasAnyAuthority('getAnyUser')")
  @GetMapping("/users/{userId}")
  public UserSimpleDto getAnyUser(@PathVariable Long userId) {
    return userExternalService.getAnyUser(userId);
  }

  @PreAuthorize("hasAnyAuthority('getAllUsers')")
  @GetMapping("/users")
  public List<UserSimpleDto> getAllUsers() {
    return userExternalService.getAllUsers();
  }

  @PreAuthorize("hasAnyAuthority('getUsersForPlanning')")
  @GetMapping("/plannings/{planningId}/users")
  public List<UserSimpleDto> getUsersForPlanning(@PathVariable Long planningId) {
    return userExternalService.getUsersForPlanning(planningId);
  }

  @PreAuthorize("hasAnyAuthority('updateMyUser')")
  @PutMapping("/users/my")
  public ResponseEntity<?> updateMyUser(@RequestBody @Valid UserSimpleDto dto,
      @RequestHeader(value = VERSION_HEADER) String versionHeader) {
    Long version = extractVersion(versionHeader);
    userExternalService.updateMyUser(version, dto);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyAuthority('updateAnyUser')")
  @PutMapping("/users/{userId}")
  public ResponseEntity<?> updateAnyUser(@PathVariable Long userId,
      @RequestBody @Valid UserSimpleDto dto,
      @RequestHeader(value = VERSION_HEADER) String versionHeader) {
    Long version = extractVersion(versionHeader);
    userExternalService.updateAnyUser(userId, version, dto);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyAuthority('updateMyUsersPassword')")
  @PutMapping("/users/my/password")
  public ResponseEntity<?> updateMyUserPassword(@RequestBody @Valid PasswordDto dto,
      @RequestHeader(value = VERSION_HEADER) String versionHeader) {
    Long version = extractVersion(versionHeader);
    userExternalService.updateMyUsersPassword(version, dto);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyAuthority('updateAnyUsersPassword')")
  @PutMapping("/users/{userId}/password")
  public ResponseEntity<?> updateAnyUsersPassword(@PathVariable Long userId,
      @RequestBody @Valid PasswordDto dto,
      @RequestHeader(value = VERSION_HEADER) String versionHeader) {
    Long version = extractVersion(versionHeader);
    userExternalService.updateAnyUsersPassword(userId, version, dto);
    return ResponseEntity.noContent().build();
  }
}
