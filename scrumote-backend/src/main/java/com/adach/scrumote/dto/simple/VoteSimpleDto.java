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
public class VoteSimpleDto extends AbstractSimpleDto {

  @NotNull
  private Long issueId;

  @NotNull
  private Long userId;

  @NotNull
  private Integer iteration;

  @NotNull
  private String value;
}
