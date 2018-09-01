package com.adach.scrumote.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@SequenceGenerator(name = "role_gen", sequenceName = "role_seq", allocationSize = 1)
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends AbstractEntity {

  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_gen")
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;
}
