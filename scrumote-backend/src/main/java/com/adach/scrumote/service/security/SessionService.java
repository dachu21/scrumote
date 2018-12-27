package com.adach.scrumote.service.security;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.service.internal.UserInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SessionService {

  private final UserInternalService userInternalService;

  public User getCurrentUser() {
    User detachedUser = (User) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    return userInternalService.findById(detachedUser.getId());
  }

  public boolean hasAuthority(String authority) {
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
