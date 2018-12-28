package com.adach.scrumote.configuration.security;

import com.adach.scrumote.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class SessionInfoDto {

  private final Long id;
  private final String username;
  private final List<String> authorities;

  static SessionInfoDto get() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    Long id = user.getId();
    String username = user.getUsername();
    List<String> authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    return new SessionInfoDto(id, username, authorities);
  }
}
