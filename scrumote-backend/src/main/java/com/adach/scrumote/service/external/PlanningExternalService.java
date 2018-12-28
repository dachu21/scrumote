package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.simple.PlanningSimpleDto;
import com.adach.scrumote.entity.Deck;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.mapper.PlanningMapper;
import com.adach.scrumote.service.internal.DeckInternalService;
import com.adach.scrumote.service.internal.PlanningInternalService;
import com.adach.scrumote.service.internal.UserHistoryInternalService;
import com.adach.scrumote.service.internal.UserInternalService;
import com.adach.scrumote.service.security.SessionService;
import java.util.List;
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

  private final SessionService sessionService;

  @PreAuthorize("hasAnyAuthority('createPlanning')")
  public Long createPlanning(PlanningSimpleDto dto) {
    Planning planning = mapper.mapToEntity(dto);

    User moderator = sessionService.getCurrentUser();
    Deck deck = deckInternalService.findById(dto.getDeckId());
    List<User> users = userInternalService.findByIds(dto.getUsers());

    planning.setFinished(false);
    planning.setModerator(moderator);
    planning.setDeck(deck);
    planning.getUsers().addAll(users);
    setDescriptionToNullIfBlank(planning);

    return internalService.save(planning).getId();
  }

  @PreAuthorize("hasAnyAuthority('getAnyPlanning', 'getMyPlanning')")
  public PlanningSimpleDto getPlanning(Long planningId) {
    Planning planning = internalService.findById(planningId);
    internalService.validateContainsCurrentUserIfNotAuthorized(planning);

    return mapper.mapToSimpleDto(planning);
  }

  @PreAuthorize("hasAnyAuthority('getAllPlannings')")
  public List<PlanningSimpleDto> getAllPlannings() {
    return internalService.findAll().stream().map(mapper::mapToSimpleDto)
        .collect(Collectors.toList());
  }

  @PreAuthorize("hasAnyAuthority('getMyPlannings')")
  public List<PlanningSimpleDto> getMyPlannings() {
    return internalService.findAllByUser(sessionService.getCurrentUser()).stream()
        .map(mapper::mapToSimpleDto)
        .collect(Collectors.toList());
  }

  @PreAuthorize("hasAnyAuthority('updatePlanning')")
  public void updatePlanning(Long planningId, Long version, PlanningSimpleDto dto) {
    Planning planning = internalService.findById(planningId);
    internalService.validateVersion(planning, version);
    validatePlanningForUpdateOrFinish(planning);

    Deck deck = deckInternalService.findById(dto.getDeckId());
    List<User> users = userInternalService.findByIds(dto.getUsers());

    planning.setCode(dto.getCode());
    planning.setName(dto.getName());
    planning.setDeck(deck);
    planning.removeAllUsers();
    planning.getUsers().addAll(users);
    planning.setDescription(dto.getDescription());
    setDescriptionToNullIfBlank(planning);
  }

  @PreAuthorize("hasAnyAuthority('finishPlanning')")
  public void finishPlanning(Long planningId, Long version) {
    Planning planning = internalService.findById(planningId);
    internalService.validateVersion(planning, version);
    validatePlanningForUpdateOrFinish(planning);

    planning.setFinished(true);
    userHistoryInternalService.updateUsersHistoryForPlanning(planning);
  }

  @PreAuthorize("hasAnyAuthority('deletePlanning')")
  public void deletePlanning(Long planningId, Long version) {
    Planning planning = internalService.findById(planningId);
    internalService.validateVersion(planning, version);
    internalService.validateFinished(planning);

    internalService.delete(planning);
  }

  private void validatePlanningForUpdateOrFinish(Planning planning) {
    internalService.validateHasModerator(planning, sessionService.getCurrentUser());
    internalService.validateHasZeroActiveIssues(planning);
    internalService.validateNotFinished(planning);
  }

  private void setDescriptionToNullIfBlank(Planning planning) {
    if (planning.getDescription().isPresent() && planning.getDescription().get().isBlank()) {
      planning.setDescription(null);
    }
  }
}
