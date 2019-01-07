package com.adach.scrumote.dto.complex;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesWithActiveDto {

  private boolean active;

  @NotNull
  private Set<Long> roles = new LinkedHashSet<>();
}
