package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.simple.IssueSimpleDto;
import com.adach.scrumote.entity.Deck;
import com.adach.scrumote.entity.Issue;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.mapper.IssueMapper;
import com.adach.scrumote.service.internal.DeckInternalService;
import com.adach.scrumote.service.internal.IssueInternalService;
import com.adach.scrumote.service.internal.PlanningInternalService;
import com.adach.scrumote.service.security.CurrentUser;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IssueExternalService {

  private final IssueInternalService internalService;
  private final IssueMapper mapper;

  private final PlanningInternalService planningInternalService;
  private final DeckInternalService deckInternalService;

  @PreAuthorize("hasAnyAuthority('createIssue')")
  public Long createIssue(Long planningId, IssueSimpleDto dto) {
    Planning planning = planningInternalService.findById(planningId);
    planningInternalService.validateHasModerator(planning, CurrentUser.get());

    Issue issue = mapper.mapToEntity(dto);
    issue.setIterations(0);
    issue.setEstimate(null);
    issue.setPlanning(planning);

    return internalService.save(issue).getId();
  }

  @PreAuthorize("hasAnyAuthority('getIssue')")
  @SuppressWarnings("Duplicates")
  public IssueSimpleDto getIssue(Long planningId, Long id) {
    Issue issue = internalService.findById(id);
    Planning planning = issue.getPlanning();
    internalService.validateBelongsToPlanningWithId(issue, planningId);
    planningInternalService.validateContainsCurrentUserIfNotAuthorized(planning);

    return mapper.mapToSimpleDto(issue);
  }

  @PreAuthorize("hasAnyAuthority('getIssuesForPlanning')")
  public List<IssueSimpleDto> getIssuesForPlanning(Long planningId) {
    Planning planning = planningInternalService.findById(planningId);
    planningInternalService.validateContainsCurrentUserIfNotAuthorized(planning);

    return internalService.findAllByPlanning(planning).stream().map(mapper::mapToSimpleDto)
        .collect(Collectors.toList());
  }

  @PreAuthorize("hasAnyAuthority('updateIssue')")
  public void updateIssue(Long planningId, Long id, IssueSimpleDto dto) {
    Issue issue = internalService.findById(id);
    Planning planning = issue.getPlanning();
    internalService.validateBelongsToPlanningWithId(issue, planningId);
    validateIssueAndPlanningForUpdateOrDelete(issue, planning);

    issue.setCode(dto.getCode());
    issue.setName(dto.getName());
    issue.setDescription(dto.getDescription());
  }

  @PreAuthorize("hasAnyAuthority('activateIssue')")
  public void activateIssue(Long planningId, Long id) {
    Issue issue = internalService.findById(id);
    Planning planning = issue.getPlanning();
    internalService.validateBelongsToPlanningWithId(issue, planningId);
    validateIssueAndPlanningForUpdateOrDelete(issue, planning);

    internalService.validateNotActive(issue);
    issue.setActive(true);
  }

  @PreAuthorize("hasAnyAuthority('estimateIssue')")
  public void estimateIssue(Long planningId, Long id, String cardValue) {
    Issue issue = internalService.findById(id);
    Planning planning = issue.getPlanning();
    internalService.validateBelongsToPlanningWithId(issue, planningId);
    validateIssueAndPlanningForUpdateOrDelete(issue, planning);

    Deck deck = planning.getDeck();
    deckInternalService.validateContainsCardWithValue(deck, cardValue);
    issue.setEstimate(cardValue);
  }

  @PreAuthorize("hasAnyAuthority('deleteIssue')")
  public void deleteIssue(Long planningId, Long id) {
    Issue issue = internalService.findById(id);
    Planning planning = issue.getPlanning();
    internalService.validateBelongsToPlanningWithId(issue, planningId);
    validateIssueAndPlanningForUpdateOrDelete(issue, planning);

    internalService.delete(issue);
  }

  private void validateIssueAndPlanningForUpdateOrDelete(Issue issue, Planning planning) {
    planningInternalService.validateHasModerator(planning, CurrentUser.get());
    internalService.validateNotEstimated(issue);
    planningInternalService.validateHasModerator(planning, CurrentUser.get());
  }
}
