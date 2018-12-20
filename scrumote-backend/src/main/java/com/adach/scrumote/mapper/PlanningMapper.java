package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.dto.SimpleDtoTypeMap;
import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.dto.complex.PlanningWithUsersDto;
import com.adach.scrumote.dto.simple.PlanningSimpleDto;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.service.internal.DeckInternalService;
import com.adach.scrumote.service.internal.UserInternalService;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@MandatoryTransactions
public class PlanningMapper extends AbstractSimpleDtoMapper<Planning, PlanningSimpleDto> {

  private final DeckInternalService deckInternalService;
  private final UserInternalService userInternalService;

  @Autowired
  public PlanningMapper(ModelMapper modelMapper, SimpleDtoTypeMap simpleDtoTypeMap,
      DeckInternalService deckInternalService,
      UserInternalService userInternalService) {
    super(modelMapper, simpleDtoTypeMap);
    this.deckInternalService = deckInternalService;
    this.userInternalService = userInternalService;
  }

  public PlanningWithUsersDto mapToDtoWithUsers(Planning planning) {
    PlanningWithUsersDto dto = new PlanningWithUsersDto();
    dto.setId(planning.getId());
    dto.setDeckId(planning.getDeck().getId());
    dto.setCode(planning.getCode());
    dto.setName(planning.getName());
    dto.setDescription(planning.getDescription().orElse(null));
    dto.setFinished(planning.isFinished());
    dto.setModeratorId(planning.getModerator().getId());
    dto.setUserIds(planning.getUsers().stream().map(User::getId).collect(Collectors.toSet()));
    return dto;
  }

  public Planning mapToEntity(PlanningWithUsersDto dto) {
    Planning planning = new Planning();
    planning.setCode(dto.getCode());
    planning.setName(dto.getName());
    planning.setDescription(dto.getDescription());
    planning.setDeck(deckInternalService.findById(dto.getDeckId()));
    planning.getUsers().addAll(userInternalService.findByIds(dto.getUserIds()));
    return planning;
  }
}
