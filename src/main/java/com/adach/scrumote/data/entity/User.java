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
@Table(name = "\"user\"")
@SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity {

  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
  private Long id;

  @Column(nullable = false, unique = true)
  private String login;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private boolean active;

  @OneToMany(mappedBy = "user")
  private Collection<UserAccessLevel> userAccessLevels;
}
