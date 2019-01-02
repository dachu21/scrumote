package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.Issue;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.entity.UserStats;
import com.adach.scrumote.entity.Vote;
import com.adach.scrumote.repository.UserStatsRepository;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserStatsInternalService extends AbstractInternalService<UserStats> {

  private final UserStatsRepository repository;

  private final DeckInternalService deckInternalService;

  public void updateUsersStatsForPlanning(Planning planning) {

    Set<UserStats> planningUserStatsSet = new HashSet<>();
    Set<Issue> issues = planning.getIssues();

    for (Issue issue : issues) {
      planningUserStatsSet.addAll(updateUsersStatsForIssue(issue));
    }

    for (UserStats planningUserStats : planningUserStatsSet) {
      planningUserStats.incrementPlannings();
    }
  }

  private Set<UserStats> updateUsersStatsForIssue(Issue issue) {

    if (issue.getEstimate().isEmpty()) {
      log.debug(String.format("Issue with id %d is not estimated. Skipping...", issue.getId()));
      return Collections.emptySet();
    }

    Set<UserStats> issueUserStatsSet = new HashSet<>();
    Set<Vote> votes = issue.getVotes();
    Map<String, Integer> cardLevelsMap = deckInternalService
        .getCardLevelsMapForDeck(issue.getPlanning().getDeck());
    Integer estimateLevel = cardLevelsMap.get(issue.getEstimate().get());

    for (Vote vote : votes) {
      issueUserStatsSet.add(updateUsersStatsForVote(vote, cardLevelsMap, estimateLevel));
    }

    for (UserStats issueUserStats : issueUserStatsSet) {
      issueUserStats.incrementIssues();
    }

    return issueUserStatsSet;
  }

  private UserStats updateUsersStatsForVote(Vote vote, Map<String, Integer> cardLevelsMap,
      Integer estimateLevel) {
    UserStats userStats = vote.getUser().getUserStats();
    userStats.incrementVotes();
    if (vote.getIteration().equals(1)) {
      Integer voteLevel = cardLevelsMap.get(vote.getValue());
      userStats.updateFirstVoteStatistics(voteLevel, estimateLevel);
    }
    return userStats;
  }
}
