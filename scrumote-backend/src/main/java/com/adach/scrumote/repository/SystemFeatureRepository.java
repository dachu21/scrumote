package com.adach.scrumote.repository;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.SystemFeature;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
@MandatoryTransactions
public interface SystemFeatureRepository extends CustomJpaRepository<SystemFeature, Long> {

  Optional<SystemFeature> findByCode(String code);
}
