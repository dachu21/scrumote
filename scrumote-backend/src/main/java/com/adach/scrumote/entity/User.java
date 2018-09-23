package com.adach.scrumote.entity;

import java.util.Collection;
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@EqualsAndHashCode(callSuper = true)
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
  }

  @Column(nullable = false, unique = true)
  @NotNull
  @Size(min = 3, max = 32)
  private String username;

  @Column(nullable = false)
  @NotNull
  @Size(min = 8, max = 64)
  private String password;

  @Column(nullable = false, unique = true)
  @NotNull
  @Email
  private String email;

  @Column
  @Size(max = 32)
  private String firstName;

  @Column
  @Size(max = 32)
  private String lastName;

  @Column(nullable = false)
  private boolean active;

  @ManyToMany
  @JoinTable(
      name = "user_permissions_t",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"),
      uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "permission_id"}))
  private Collection<Permission> permissions = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "user_roles_t",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"),
      uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
  private Collection<Role> roles = new HashSet<>();
}
