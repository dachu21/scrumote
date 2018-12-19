package com.adach.scrumote.entity;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "deck_t")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Deck extends AbstractEntity {

  //region Data
  @Column(nullable = false)
  @NotNull
  private String name;
  //endregion

  //region Mappings
  @OneToMany(mappedBy = "deck")
  @OrderBy("level asc")
  private Set<Card> cards = new LinkedHashSet<>();
  //endregion

  //region Methods
  public boolean containsCardWithValue(String cardValue) {
    return cards.stream()
        .map(Card::getValue)
        .collect(Collectors.toSet())
        .contains(cardValue);
  }
  //endregion
}
