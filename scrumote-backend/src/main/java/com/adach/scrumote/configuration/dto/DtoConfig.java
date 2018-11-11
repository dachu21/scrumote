package com.adach.scrumote.configuration.dto;

import com.adach.scrumote.dto.simple.CardSimpleDto;
import com.adach.scrumote.dto.simple.DeckSimpleDto;
import com.adach.scrumote.dto.simple.IssueSimpleDto;
import com.adach.scrumote.dto.simple.PlanningSimpleDto;
import com.adach.scrumote.dto.simple.UserHistorySimpleDto;
import com.adach.scrumote.dto.simple.UserSimpleDto;
import com.adach.scrumote.dto.simple.VoteSimpleDto;
import com.adach.scrumote.entity.Card;
import com.adach.scrumote.entity.Deck;
import com.adach.scrumote.entity.Issue;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.entity.UserHistory;
import com.adach.scrumote.entity.Vote;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.lang.reflect.Type;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DtoConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public SimpleDtoTypeMap simpleDtoTypeMap() {
    BiMap<Type, Type> typesMap = HashBiMap.create();
    typesMap.put(Card.class, CardSimpleDto.class);
    typesMap.put(Deck.class, DeckSimpleDto.class);
    typesMap.put(Issue.class, IssueSimpleDto.class);
    typesMap.put(Planning.class, PlanningSimpleDto.class);
    typesMap.put(UserHistory.class, UserHistorySimpleDto.class);
    typesMap.put(User.class, UserSimpleDto.class);
    typesMap.put(Vote.class, VoteSimpleDto.class);
    return new SimpleDtoTypeMap(typesMap);
  }
}
