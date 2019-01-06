package com.adach.scrumote.dto.simple;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
  @Size(max = 32)
  private String code;

  @NotNull
  @Size(min = 3, max = 32)
  private String name;

  @NotNull
  private Set<Long> users = new LinkedHashSet<>();

  @Size(max = 255)
  private String description;

  private boolean finished;

  private Long moderatorId;

  private String deckName;

  private String moderatorUsername;

  private Set<String> usersUsernames = new LinkedHashSet<>();
}
