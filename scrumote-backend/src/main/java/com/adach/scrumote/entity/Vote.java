package com.adach.scrumote.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "vote_t",
    uniqueConstraints = @UniqueConstraint(columnNames = {"issue_id", "user_id", "iteration"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Vote extends AbstractEntity {

  //region Data
  @ManyToOne
  @JoinColumn(nullable = false)
  @NotNull
  private Issue issue;

  @ManyToOne
  @JoinColumn(nullable = false)
  @NotNull
  private User user;

  @Column(nullable = false)
  @NotNull
  private Integer iteration;

  @Column(nullable = false)
  @NotNull
  private String value;
  //endregion
}
