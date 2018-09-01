package com.adach.scrumote.data.service;

import com.adach.scrumote.data.entity.AbstractEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public abstract class AbstractService<E extends AbstractEntity> {

  @Autowired
  private JpaRepository<E, Long> repository;

  public List<E> findAll() {
    return repository.findAll();
  }

  public Optional<E> findById(Long id) {
    return repository.findById(id);
  }

  public void save(E entity) {
    repository.save(entity);
  }

  public void delete(E entity) {
    repository.delete(entity);
  }
}
