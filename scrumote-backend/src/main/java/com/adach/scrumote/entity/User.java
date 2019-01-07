package com.adach.scrumote.entity;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_t")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class User extends AbstractEntity {

  public User(User user) {
    this.id = user.id;
    this.username = user.username;
    this.password = user.password;
    this.email = user.email;
    this.firstName = user.firstName;
    this.lastName = user.lastName;
    this.active = user.active;
    this.permissions = user.permissions;
    this.roles = user.roles;
    this.userStats = user.userStats;
  }

  @Column(nullable = false, unique = true, length = 32)
  @NotNull
  @Size(min = 3, max = 32)
  private String username;

  @Column(nullable = false, length = 60)
  @NotNull
  @Size(min = 60, max = 60)
  private String password;

  @Column(nullable = false, unique = true, length = 64)
  @NotNull
  @Email
  @Size(max = 64)
  private String email;

  @Column(length = 32)
  @Size(max = 32)
  private String firstName;

  @Column(length = 32)
  @Size(max = 32)
  private String lastName;

  @Column(nullable = false)
  private boolean active;

  @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST)
  private UserStats userStats;

  @ManyToMany
  @JoinTable(
      name = "user_permissions_t",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"),
      uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "permission_id"}))
  private Set<Permission> permissions = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "user_roles_t",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"),
      uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
  @OrderBy("name asc")
  private Set<Role> roles = new LinkedHashSet<>();
}
