package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.mapper.DeckMapper;
import com.adach.scrumote.service.internal.DeckInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeckExternalService {

  private final DeckInternalService internalService;
  private final DeckMapper mapper;
}
