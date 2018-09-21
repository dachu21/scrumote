package com.adach.scrumote.configuration.security;

import com.adach.scrumote.entity.Permission;
import com.adach.scrumote.entity.Role;
import com.adach.scrumote.entity.User;
import java.util.Collection;
import java.util.HashSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

class LoggedUser extends User implements UserDetails {

  private static final String ROLE_PREFIX = "ROLE_";

  private final Collection<GrantedAuthority> authorities;

  LoggedUser(User user) {
    super(user);
    authorities = initializeAuthorities();
  }

  private Collection<GrantedAuthority> initializeAuthorities() {
    Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();

    getRoles()
        .stream()
        .map(r -> new SimpleGrantedAuthority(ROLE_PREFIX + r.getName()))
        .forEach(grantedAuthorities::add);

    Collection<Permission> allAuthorities = new HashSet<>(this.getPermissions());
    getRoles()
        .stream()
        .map(Role::getPermissions)
        .forEach(allAuthorities::addAll);

    allAuthorities
        .stream()
        .map(p -> new SimpleGrantedAuthority(p.getName()))
        .forEach(grantedAuthorities::add);

    return grantedAuthorities;
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
