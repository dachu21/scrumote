package com.adach.scrumote.dto.complex;

import com.adach.scrumote.dto.simple.AbstractSimpleDto;
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
public class PlanningWithUsersDto extends AbstractSimpleDto {

  @NotNull
  private Long deckId;

  @NotNull
  private String code;

  @NotNull
  private String name;

  private String description;

  private boolean finished;

  private Long moderatorId;

  private Set<Long> userIds;
}
