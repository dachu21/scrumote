package com.adach.scrumote.configuration.security;

import com.adach.scrumote.entity.Permission;
import com.adach.scrumote.entity.Role;
import com.adach.scrumote.entity.User;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

class LoggedUser extends User implements UserDetails {

  private final Collection<GrantedAuthority> authorities;

  LoggedUser(User user) {
    super(user);
    authorities = initializeAuthorities();
  }

  private Collection<GrantedAuthority> initializeAuthorities() {
    Collection<Permission> allPermissions = new HashSet<>(getPermissions());
    getRoles()
        .stream()
        .map(Role::getPermissions)
        .forEach(allPermissions::addAll);
    return allPermissions
        .stream()
        .map(p -> new SimpleGrantedAuthority(p.getName()))
        .collect(Collectors.toSet());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return isActive();
  }
}
