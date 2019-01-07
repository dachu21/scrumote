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
@Table(name = "system_feature_t")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class SystemFeature extends AbstractEntity {

  //region Data
  @Column(nullable = false, unique = true, length = 32)
  @NotNull
  @Size(max = 32)
  private String code;

  @Column(nullable = false)
  @NotNull
  private boolean enabled;
  //endregion
}
