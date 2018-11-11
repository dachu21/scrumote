package com.adach.scrumote.service.external;

import com.adach.scrumote.mapper.CardMapper;
import com.adach.scrumote.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CardPublicService {

  private final CardRepository cardRepository;
  private final CardMapper cardMapper;
}
