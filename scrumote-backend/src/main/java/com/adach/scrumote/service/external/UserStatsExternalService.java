package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.simple.UserStatsSimpleDto;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.mapper.UserStatsMapper;
import com.adach.scrumote.service.internal.UserInternalService;
import com.adach.scrumote.service.internal.UserStatsInternalService;
import com.adach.scrumote.service.security.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserStatsExternalService {

  private final UserStatsInternalService internalService;
  private final UserStatsMapper mapper;

  private final UserInternalService userInternalService;

  private final SessionService sessionService;

  @PreAuthorize("hasAnyAuthority('getMyUserStats')")
  public UserStatsSimpleDto getMyUserStats() {
    User currentUser = sessionService.getCurrentUser();
    return mapper.mapToSimpleDto(currentUser.getUserStats());
  }

  @PreAuthorize("hasAnyAuthority('getAnyUserStats')")
  public UserStatsSimpleDto getAnyUserStats(Long userId) {
    User user = userInternalService.findById(userId);
    return mapper.mapToSimpleDto(user.getUserStats());
  }
}
