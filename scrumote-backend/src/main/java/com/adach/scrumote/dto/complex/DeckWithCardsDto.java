package com.adach.scrumote.dto.complex;

import com.adach.scrumote.dto.AbstractDto;
import com.adach.scrumote.dto.simple.CardSimpleDto;
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
public class DeckWithCardsDto extends AbstractDto {

  @NotNull
  @Size(min = 3, max = 32)
  private String name;

  @NotNull
  private Set<CardSimpleDto> cards = new LinkedHashSet<>();
}
