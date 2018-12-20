package com.adach.scrumote.dto.complex;

import com.adach.scrumote.dto.simple.AbstractSimpleDto;
import com.adach.scrumote.dto.simple.CardSimpleDto;
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
public class DeckWithCardsDto extends AbstractSimpleDto {

  @NotNull
  private String name;

  @NotNull
  private Set<CardSimpleDto> cards = new LinkedHashSet<>();
}