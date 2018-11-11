package com.adach.scrumote.service.internal;

import com.adach.scrumote.entity.Issue;
import com.adach.scrumote.repository.CardRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CardInternalService {

  private final CardRepository repository;

  public Map<String, Integer> getCardLevelsMapForIssue(Issue issue) {
    Map<String, Integer> cardsMap = new HashMap<>();
    issue.getPlanning().getDeck().getCards()
        .forEach(card -> cardsMap.put(card.getValue(), card.getLevel()));
    return cardsMap;
  }
}
