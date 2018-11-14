package com.adach.scrumote.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_history_t")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserHistory extends AbstractEntity {

  public static UserHistory createEmpty(User user) {
    UserHistory userHistory = new UserHistory();
    userHistory.user = user;
    userHistory.plannings = 0;
    userHistory.issues = 0;
    userHistory.firstVotesBelowEstimate = 0;
    userHistory.firstVotesAboveEstimate = 0;
    userHistory.firstVotesEqualEstimate = 0;
    userHistory.averageFirstVoteLevelDifference = 0D;
    return userHistory;
  }

  //region Data
  @OneToOne
  @JoinColumn(nullable = false)
  @NotNull
  private User user;

  @Column(nullable = false)
  @NotNull
  private Integer plannings;

  @Column(nullable = false)
  @NotNull
  private Integer issues;

  @Column(nullable = false)
  @NotNull
  private Integer votes;

  @Column(nullable = false)
  @NotNull
  private Integer firstVotesBelowEstimate;

  @Column(nullable = false)
  @NotNull
  private Integer firstVotesAboveEstimate;

  @Column(nullable = false)
  @NotNull
  private Integer firstVotesEqualEstimate;

  @Column(nullable = false)
  @NotNull
  private Double averageFirstVoteLevelDifference;
  //endregion

  public Integer getFirstVotes() {
    return firstVotesBelowEstimate + firstVotesAboveEstimate + firstVotesEqualEstimate;
  }

  public void incrementPlannings() {
    plannings++;
  }

  public void incrementIssues() {
    issues++;
  }

  public void incrementVotes() {
    votes++;
  }

  public void updateFirstVoteStatistics(Integer firstVoteLevel, Integer estimateLevel) {
    int levelDifference = firstVoteLevel - estimateLevel;
    updateAverageFirstVoteLevelDifference(levelDifference);
    incrementFirstVotesCount(levelDifference);
  }

  private void updateAverageFirstVoteLevelDifference(int levelDifference) {
    double totalLevelDifference = getFirstVotes() * averageFirstVoteLevelDifference;
    totalLevelDifference += levelDifference;
    averageFirstVoteLevelDifference = totalLevelDifference / (getFirstVotes() + 1);
  }

  private void incrementFirstVotesCount(int levelDifference) {
    if (levelDifference < 0) {
      firstVotesBelowEstimate++;
    } else if (levelDifference > 0) {
      firstVotesAboveEstimate++;
    } else {
      firstVotesEqualEstimate++;
    }
  }
}
