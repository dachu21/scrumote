package com.adach.scrumote.data.entity;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@SequenceGenerator(name = "access_level_gen", sequenceName = "access_level_seq", allocationSize = 1)
@Data
@EqualsAndHashCode(callSuper = true)
public class AccessLevel extends AbstractEntity {

  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "access_level_gen")
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "accessLevel")
  private Collection<AccessLevelRole> roles;
}
