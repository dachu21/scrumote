package com.adach.scrumote.dto.simple;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode
public abstract class AbstractSimpleDto {

  @Getter
  @Setter
  protected Long id;
}
