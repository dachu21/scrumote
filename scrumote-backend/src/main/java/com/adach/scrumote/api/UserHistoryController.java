package com.adach.scrumote.api;

import com.adach.scrumote.configuration.api.PrefixedRestController;
import com.adach.scrumote.dto.simple.UserHistorySimpleDto;
import com.adach.scrumote.service.external.UserHistoryExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserHistoryController {

  private final UserHistoryExternalService userHistoryExternalService;

  @PreAuthorize("hasAnyAuthority('getMyUserHistory')")
  @GetMapping("/users/my/history")
  public UserHistorySimpleDto getMyUserHistory() {
    return userHistoryExternalService.getMyUserHistory();
  }

  @PreAuthorize("hasAnyAuthority('getAnyUserHistory')")
  @GetMapping("/users/{userId}/history")
  public UserHistorySimpleDto getAnyUserHistory(@PathVariable Long userId) {
    return userHistoryExternalService.getAnyUserHistory(userId);
  }
}
