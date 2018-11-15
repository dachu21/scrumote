package com.adach.scrumote.repository;

import com.adach.scrumote.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  //region Overriden
  @Override
  List<User> findAll();

  @Override
  List<User> findAllById(Iterable<Long> iterable);

  @Override
  <S extends User> S save(S s);
  //endregion
}
