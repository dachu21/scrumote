package com.adach.scrumote.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"role_id", "access_level_id"})})
@SequenceGenerator(name = "access_level_role_gen", sequenceName = "access_level_role_seq", allocationSize = 1)
@Data
@EqualsAndHashCode(callSuper = true)
public class AccessLevelRole extends AbstractEntity {

  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "access_level_role_gen")
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false, updatable = false)
  private Role role;

  @ManyToOne
  @JoinColumn(nullable = false, updatable = false)
  private AccessLevel accessLevel;
}
