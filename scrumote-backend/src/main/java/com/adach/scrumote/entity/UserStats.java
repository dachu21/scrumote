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
@Table(name = "user_stats_t")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class UserStats extends AbstractEntity {

  public static UserStats createEmpty(User user) {
    UserStats userStats = new UserStats();
    userStats.user = user;
    userStats.plannings = 0;
    userStats.issues = 0;
    userStats.votes = 0;
    userStats.firstVotesBelowEstimate = 0;
    userStats.firstVotesAboveEstimate = 0;
    userStats.firstVotesEqualEstimate = 0;
    userStats.averageFirstVoteLevelDifference = 0D;
    return userStats;
  }

  //region Data
  @OneToOne
  @JoinColumn(nullable = false, unique = true)
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

  //region Methods
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

  private Integer getFirstVotes() {
    return firstVotesBelowEstimate + firstVotesAboveEstimate + firstVotesEqualEstimate;
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
  //endregion
}
