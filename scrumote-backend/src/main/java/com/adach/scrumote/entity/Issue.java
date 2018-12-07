package com.adach.scrumote.entity;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "issue_t")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Issue extends AbstractEntity {

  //region Data
  @ManyToOne
  @JoinColumn(nullable = false)
  @NotNull
  private Planning planning;

  @Column(nullable = false)
  @NotNull
  private String code;

  @Column(nullable = false)
  @NotNull
  private String name;

  @Column
  private String description;

  @Column(nullable = false)
  @NotNull
  private Integer iterations;

  @Column
  private String estimate;

  @Column(nullable = false)
  private boolean active;
  //endregion

  //region Mappings
  @OneToMany(mappedBy = "issue")
  @OrderBy("iteration asc")
  private Set<Vote> votes = new LinkedHashSet<>();
  //endregion

  //region Optional getters
  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public Optional<String> getEstimate() {
    return Optional.ofNullable(estimate);
  }
  //endregion
}
