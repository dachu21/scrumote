package com.adach.scrumote.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "card_t")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Card extends AbstractEntity {

  //region Data
  @ManyToOne
  @JoinColumn(nullable = false)
  @NotNull
  private Deck deck;

  @Column(nullable = false)
  @NotNull
  private Integer level;

  @Column(nullable = false)
  @NotNull
  private String value;
  //endregion
}
