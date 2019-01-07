package com.adach.scrumote.api;

import com.adach.scrumote.configuration.api.PrefixedRestController;
import com.adach.scrumote.dto.simple.UserStatsSimpleDto;
import com.adach.scrumote.service.external.UserStatsExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserStatsController {

  private final UserStatsExternalService userStatsExternalService;

  @PreAuthorize("hasAnyAuthority('getMyUserStats')")
  @GetMapping("/users/my/stats")
  public UserStatsSimpleDto getMyUserStats() {
    return userStatsExternalService.getMyUserStats();
  }

  @PreAuthorize("hasAnyAuthority('getAnyUserStats')")
  @GetMapping("/users/{userId}/stats")
  public UserStatsSimpleDto getAnyUserStats(@PathVariable Long userId) {
    return userStatsExternalService.getAnyUserStats(userId);
  }
}
