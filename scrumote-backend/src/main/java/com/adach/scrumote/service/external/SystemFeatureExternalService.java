package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.simple.SystemFeatureSimpleDto;
import com.adach.scrumote.entity.SystemFeature;
import com.adach.scrumote.mapper.SystemFeatureMapper;
import com.adach.scrumote.service.internal.SystemFeatureInternalService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SystemFeatureExternalService {

  private final SystemFeatureInternalService internalService;
  private final SystemFeatureMapper mapper;

  public SystemFeatureSimpleDto getSystemFeature(String code) {
    SystemFeature systemFeature = internalService.findByCode(code);
    return mapper.mapToSimpleDto(systemFeature);
  }

  @PreAuthorize("hasAnyAuthority('getAllSystemFeatures')")
  public List<SystemFeatureSimpleDto> getAllSystemFeatures() {
    return internalService.findAll().stream().map(mapper::mapToSimpleDto)
        .collect(Collectors.toList());
  }

  @PreAuthorize("hasAnyAuthority('updateSystemFeature')")
  public void updateSystemFeature(Long featureId, Long version, SystemFeatureSimpleDto dto) {
    SystemFeature systemFeature = internalService.findById(featureId);
    internalService.validateVersion(systemFeature, version);

    systemFeature.setEnabled(dto.isEnabled());
  }
}
