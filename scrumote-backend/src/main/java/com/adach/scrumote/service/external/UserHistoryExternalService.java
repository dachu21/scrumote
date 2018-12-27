package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.simple.UserHistorySimpleDto;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.mapper.UserHistoryMapper;
import com.adach.scrumote.service.internal.UserHistoryInternalService;
import com.adach.scrumote.service.internal.UserInternalService;
import com.adach.scrumote.service.security.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserHistoryExternalService {

  private final UserHistoryInternalService internalService;
  private final UserHistoryMapper mapper;

  private final UserInternalService userInternalService;

  private final SessionService sessionService;

  @PreAuthorize("hasAnyAuthority('getMyUserHistory')")
  public UserHistorySimpleDto getMyUserHistory() {
    User currentUser = sessionService.getCurrentUser();
    return mapper.mapToSimpleDto(currentUser.getUserHistory());
  }

  @PreAuthorize("hasAnyAuthority('getAnyUserHistory')")
  public UserHistorySimpleDto getAnyUserHistory(Long userId) {
    User user = userInternalService.findById(userId);
    return mapper.mapToSimpleDto(user.getUserHistory());
  }
}
