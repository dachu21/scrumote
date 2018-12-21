package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.complex.PlanningWithUserIdsDto;
import com.adach.scrumote.dto.simple.PlanningSimpleDto;
import com.adach.scrumote.entity.Deck;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.mapper.PlanningMapper;
import com.adach.scrumote.service.internal.DeckInternalService;
import com.adach.scrumote.service.internal.PlanningInternalService;
import com.adach.scrumote.service.internal.UserHistoryInternalService;
import com.adach.scrumote.service.internal.UserInternalService;
import com.adach.scrumote.service.security.CurrentUser;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanningExternalService {

  private final PlanningInternalService internalService;
  private final PlanningMapper mapper;

  private final UserHistoryInternalService userHistoryInternalService;
  private final DeckInternalService deckInternalService;
  private final UserInternalService userInternalService;

  @PreAuthorize("hasAnyAuthority('createPlanning')")
  public Long createPlanningWithUsers(PlanningWithUserIdsDto dto) {
    User moderator = CurrentUser.get();

    Planning planning = mapper.mapToEntity(dto);
    planning.setFinished(false);
    planning.setModerator(moderator);

    return internalService.save(planning).getId();
  }

  @PreAuthorize("hasAnyAuthority('getAnyPlanning', 'getMyPlanning')")
  public PlanningWithUserIdsDto getPlanningWithUsers(Long id) {
    Planning planning = internalService.findById(id);
    internalService.validateContainsCurrentUserIfNotAuthorized(planning);

    return mapper.mapToDtoWithUsers(planning);
  }

  @PreAuthorize("hasAnyAuthority('getAllPlannings')")
  public List<PlanningSimpleDto> getAllPlannings() {
    return internalService.findAll().stream().map(mapper::mapToSimpleDto)
        .collect(Collectors.toList());
  }

  @PreAuthorize("hasAnyAuthority('getMyPlannings')")
  public List<PlanningSimpleDto> getMyPlannings() {
    User currentUser = CurrentUser.get();
    return internalService.findAllByUser(currentUser).stream().map(mapper::mapToSimpleDto)
        .collect(Collectors.toList());
  }

  @PreAuthorize("hasAnyAuthority('updatePlanning')")
  public void updatePlanning(Long id, PlanningWithUserIdsDto dto) {
    Planning planning = internalService.findById(id);
    validatePlanningForUpdateOrFinish(planning);

    Deck deck = deckInternalService.findById(dto.getDeckId());
    Set<User> users = new HashSet<>(userInternalService.findByIds(dto.getUserIds()));

    planning.setCode(dto.getCode());
    planning.setName(dto.getName());
    planning.setDescription(dto.getDescription());
    planning.setDeck(deck);
    planning.setUsers(users);
  }

  @PreAuthorize("hasAnyAuthority('finishPlanning')")
  public void finishPlanning(Long id) {
    Planning planning = internalService.findById(id);
    validatePlanningForUpdateOrFinish(planning);

    planning.setFinished(true);
    userHistoryInternalService.updateUsersHistoryForPlanning(planning);
  }

  @PreAuthorize("hasAnyAuthority('deletePlanning')")
  public void deletePlanning(Long id) {
    Planning planning = internalService.findById(id);
    internalService.validateFinished(planning);

    internalService.delete(planning);
  }

  private void validatePlanningForUpdateOrFinish(Planning planning) {
    internalService.validateHasModerator(planning, CurrentUser.get());
    internalService.validateHasZeroActiveIssues(planning);
    internalService.validateNotFinished(planning);
  }
}
