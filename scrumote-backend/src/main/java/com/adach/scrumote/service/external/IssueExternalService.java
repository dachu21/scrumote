package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.mapper.IssueMapper;
import com.adach.scrumote.service.internal.IssueInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IssueExternalService {

  private final IssueInternalService internalService;
  private final IssueMapper mapper;
}
