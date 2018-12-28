package com.adach.scrumote.dto.simple;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class UserHistorySimpleDto extends AbstractSimpleDto {

  @NotNull
  private Long userId;

  @NotNull
  private Integer plannings;

  @NotNull
  private Integer issues;

  @NotNull
  private Integer votes;

  @NotNull
  private Integer firstVotesBelowEstimate;

  @NotNull
  private Integer firstVotesAboveEstimate;

  @NotNull
  private Integer firstVotesEqualEstimate;

  @NotNull
  private Double averageFirstVoteLevelDifference;
}
