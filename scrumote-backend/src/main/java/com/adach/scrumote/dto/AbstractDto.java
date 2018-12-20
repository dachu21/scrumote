package com.adach.scrumote.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode
public abstract class AbstractDto {

  @Getter
  @Setter
  protected Long id;
}
