package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.mapper.UserHistoryMapper;
import com.adach.scrumote.service.internal.UserHistoryInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserHistoryExternalService {

  private final UserHistoryInternalService internalService;
  private final UserHistoryMapper mapper;
}
