package com.adach.scrumote.repository;

import com.adach.scrumote.entity.Planning;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
public interface PlanningRepository extends JpaRepository<Planning, Long> {

  //region Overriden
  @Override
  List<Planning> findAll();

  @Override
  <S extends Planning> S save(S s);

  @Override
  Optional<Planning> findById(Long id);

  @Override
  void delete(Planning planning);
  //endregion
}
