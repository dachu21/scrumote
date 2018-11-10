package com.adach.scrumote.repository;

import com.adach.scrumote.entity.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {

}
