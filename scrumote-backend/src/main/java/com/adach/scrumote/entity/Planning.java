package com.adach.scrumote.entity;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

  @Column(nullable = false, unique = true, length = 32)
  @NotNull
  @Size(max = 32)
  private String code;

  @Column(nullable = false, length = 32)
  @NotNull
  @Size(min = 3, max = 32)
  private String name;

  @Column
  @Size(max = 255)
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
  @OrderBy("username asc")
  private Set<User> users = new LinkedHashSet<>();

  @OneToMany(mappedBy = "planning", cascade = CascadeType.REMOVE)
  @OrderBy("code asc")
  private Set<Issue> issues = new LinkedHashSet<>();
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

  public void removeAllUsers() {
    users.clear();
  }
  //endregion
}
