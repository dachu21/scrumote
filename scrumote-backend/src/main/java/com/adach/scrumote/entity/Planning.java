package com.adach.scrumote.entity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Planning extends AbstractEntity {

  //region Data
  @ManyToOne
  @JoinColumn(nullable = false)
  @NotNull
  private Deck deck;

  @Column(nullable = false, unique = true)
  @NotNull
  private String code;

  @Column(nullable = false)
  @NotNull
  private String name;

  @Column
  private String description;

  @Column(nullable = false)
  private boolean finished;

  @ManyToOne
  @JoinColumn(nullable = false)
  @NotNull
  private User moderator;
  //endregion

  //region Mappings
  @ManyToMany
  @JoinTable(
      name = "planning_users_t",
      joinColumns = @JoinColumn(name = "planning_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"),
      uniqueConstraints = @UniqueConstraint(columnNames = {"planning_id", "user_id"}))
  private Set<User> users = new HashSet<>();

  @OneToMany(mappedBy = "planning")
  private Set<Issue> issues = new HashSet<>();
  //endregion

  //region Optional getters
  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }
  //endregion

  //region Methods
  public boolean hasModerator(User user) {
    return this.getModerator().equals(user);
  }

  public boolean containsUser(User user) {
    return this.getUsers().contains(user);
  }
  //endregion
}
