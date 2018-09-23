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
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role_t")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends AbstractEntity {

  @Column(nullable = false, unique = true)
  @NotNull
  private String name;

  @ManyToMany
  @JoinTable(
      name = "role_permissions_t",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"),
      uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "permission_id"}))
  private Collection<Permission> permissions = new HashSet<>();
}
