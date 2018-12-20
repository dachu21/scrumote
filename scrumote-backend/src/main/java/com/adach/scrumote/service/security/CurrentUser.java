package com.adach.scrumote.service.security;

import com.adach.scrumote.entity.User;
import com.adach.scrumote.service.internal.UserInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUser {

  private static UserInternalService userInternalService;

  @Autowired
  public CurrentUser(UserInternalService userInternalService) {
    CurrentUser.userInternalService = userInternalService;
  }

  public static User get() {
    User detachedUser = (User) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    return userInternalService.findById(detachedUser.getId());
  }

  public static boolean hasAuthority(String authority) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      return authentication.getAuthorities()
          .stream()
          .anyMatch(granted -> granted.getAuthority().equals(authority));
    } else {
      return false;
    }
  }
}
