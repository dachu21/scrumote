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
public class IssueSimpleDto extends AbstractSimpleDto {

  @NotNull
  private String code;

  @NotNull
  private String name;

  private String description;

  private Integer finishedIterations;

  private String estimate;

  private boolean active;
}
