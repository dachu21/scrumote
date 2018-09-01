package com.adach.scrumote.data.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@EqualsAndHashCode
public abstract class AbstractEntity {

  @Version
  @Column(nullable = false, updatable = false)
  private Long version;
}
