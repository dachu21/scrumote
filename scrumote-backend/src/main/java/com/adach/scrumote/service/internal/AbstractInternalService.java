package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.AbstractEntity;
import com.adach.scrumote.exception.optimisticlock.ManipulatedEntityVersionException;
import com.adach.scrumote.exception.optimisticlock.OutdatedEntityVersionException;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
public abstract class AbstractInternalService<T extends AbstractEntity> {

  public void validateVersion(T entity, Long version) {
    if (version < entity.getVersion()) {
      throw new OutdatedEntityVersionException(String.format(
          "Provided version of entity with id %d is outdated. Outdated version: %d. Current version: %d",
          entity.getId(), version, entity.getVersion()));
    }
    if (version > entity.getVersion()) {
      throw new ManipulatedEntityVersionException(String.format(
          "Provided version of entity with id %d is manipulated (too high). Manipulated version: %d. Current version: %d",
          entity.getId(), version, entity.getVersion()));
    }
  }
}
