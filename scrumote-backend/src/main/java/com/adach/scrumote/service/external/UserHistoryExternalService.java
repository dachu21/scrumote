package com.adach.scrumote.service.external;

import com.adach.scrumote.mapper.UserHistoryMapper;
import com.adach.scrumote.repository.UserHistoryRepository;
import com.adach.scrumote.service.internal.UserHistoryInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserHistoryExternalService {

  private final UserHistoryInternalService internalService;
  private final UserHistoryRepository repository;
  private final UserHistoryMapper mapper;
}
