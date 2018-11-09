package com.adach.scrumote.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

  //region Data
  @ManyToOne
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
  private Double averageCardLevelDifference;
  //endregion
}
