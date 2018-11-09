package com.adach.scrumote.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "planning_t")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Planning extends AbstractEntity {

  //region Data
  @Column(nullable = false, unique = true)
  @NotNull
  private String name;

  @Column
  private String description;

  @Column(nullable = false)
  @NotNull
  private Integer iterations;

  @Column(nullable = false)
  private boolean finished;
  //endregion

  //region Mappings
  @OneToMany(mappedBy = "planning")
  private Collection<Issue> issues = new HashSet<>();
  //endregion

  //region Optional getters
  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }
  //endregion
}
