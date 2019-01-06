package com.adach.scrumote.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@EqualsAndHashCode(callSuper = true, of = "value")
public class Card extends AbstractEntity {

  //region Data
  @Column(nullable = false)
  @NotNull
  private Integer level;

  @Column(nullable = false, length = 32)
  @NotNull
  @Size(max = 32)
  private String value;
  //endregion
}
