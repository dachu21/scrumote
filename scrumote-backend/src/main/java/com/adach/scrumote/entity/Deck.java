package com.adach.scrumote.entity;

import java.util.ArrayList;
import java.util.Collection;
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
@EqualsAndHashCode(callSuper = true)
public class Deck extends AbstractEntity {

  //region Data
  @Column(nullable = false)
  @NotNull
  private String name;
  //endregion

  //region Mappings
  @OneToMany(mappedBy = "deck")
  @OrderBy("level asc")
  private Collection<Card> cards = new ArrayList<>();
  //endregion
}
