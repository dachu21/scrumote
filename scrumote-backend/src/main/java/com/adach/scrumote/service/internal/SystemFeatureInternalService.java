package com.adach.scrumote.service.internal;

import com.adach.scrumote.configuration.transaction.MandatoryTransactions;
import com.adach.scrumote.entity.SystemFeature;
import com.adach.scrumote.exception.systemfeature.SystemFeatureNotFoundException;
import com.adach.scrumote.repository.SystemFeatureRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MandatoryTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SystemFeatureInternalService extends AbstractInternalService<SystemFeature> {

  private final SystemFeatureRepository repository;

  //region Repository methods calls
  public SystemFeature findByCode(String code) {
    Optional<SystemFeature> systemFeatureOpt = repository.findByCode(code);
    return systemFeatureOpt.orElseThrow(() -> new SystemFeatureNotFoundException(
        String.format("System feature with code %s does not exist.", code)));
  }

  public List<SystemFeature> findAll() {
    return repository.findAll();
  }

  public SystemFeature findById(Long id) {
    Optional<SystemFeature> systemFeatureOpt = repository.findById(id);
    return systemFeatureOpt.orElseThrow(() -> new SystemFeatureNotFoundException(
        String.format("System feature with id %d does not exist.", id)));
  }
  //endregion
}
