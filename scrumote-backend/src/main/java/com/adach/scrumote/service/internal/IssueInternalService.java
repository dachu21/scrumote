package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IssueInternalService {

  private final IssueRepository repository;
}
