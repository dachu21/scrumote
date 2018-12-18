package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.simple.VoteSimpleDto;
import com.adach.scrumote.entity.Deck;
import com.adach.scrumote.entity.Issue;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.entity.Vote;
import com.adach.scrumote.mapper.VoteMapper;
import com.adach.scrumote.service.internal.DeckInternalService;
import com.adach.scrumote.service.internal.IssueInternalService;
import com.adach.scrumote.service.internal.PlanningInternalService;
import com.adach.scrumote.service.internal.VoteInternalService;
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
public class VoteExternalService {

  private final VoteInternalService internalService;
  private final VoteMapper mapper;

  private final IssueInternalService issueInternalService;
  private final DeckInternalService deckInternalService;
  private final PlanningInternalService planningInternalService;

  @PreAuthorize("hasAnyAuthority('createVote')")
  public Long createVote(Long planningId, Long issueId, VoteSimpleDto dto) {
    Issue issue = issueInternalService.findById(issueId);
    issueInternalService.validateBelongsToPlanningWithId(issue, planningId);
    issueInternalService.validateActive(issue);
    Deck deck = issue.getPlanning().getDeck();
    deckInternalService.validateContainsCardWithValue(deck, dto.getValue());

    Vote vote = mapper.mapToEntity(dto);
    vote.setIssue(issue);
    vote.setUser(CurrentUser.get());
    vote.setValue(dto.getValue());
    vote.setIteration(issue.getFinishedIterations() + 1);

    return internalService.save(vote).getId();
  }

  @PreAuthorize("hasAnyAuthority('getVotesForIssue')")
  public List<VoteSimpleDto> getVotesForIssue(Long planningId, Long issueId, Integer iteration) {
    Issue issue = issueInternalService.findById(issueId);
    Planning planning = issue.getPlanning();
    issueInternalService.validateBelongsToPlanningWithId(issue, planningId);
    issueInternalService.validateNotCurrentlyActiveIteration(issue, iteration);
    planningInternalService.validateContainsCurrentUserIfNotAuthorized(planning);

    return internalService.findAllByIssueAndIteration(issue, iteration).stream()
        .map(mapper::mapToSimpleDto).collect(Collectors.toList());
  }
}
