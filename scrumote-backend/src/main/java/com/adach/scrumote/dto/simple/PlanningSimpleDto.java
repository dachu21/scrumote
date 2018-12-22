package com.adach.scrumote.dto.simple;

import java.util.LinkedHashSet;
import java.util.Set;
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
public class PlanningSimpleDto extends AbstractSimpleDto {

  @NotNull
  private Long deckId;

  @NotNull
  private String code;

  @NotNull
  private String name;

  @NotNull
  private Set<Long> users = new LinkedHashSet<>();

  private String description;

  private boolean finished;

  private Long moderatorId;

}
