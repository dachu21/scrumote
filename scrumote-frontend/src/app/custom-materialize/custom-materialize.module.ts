import {NgModule} from '@angular/core';

import {
  MzBadgeModule,
  MzButtonModule,
  MzCardModule,
  MzCheckboxModule,
  MzChipModule,
  MzCollapsibleModule,
  MzCollectionModule,
  MzDatepickerModule,
  MzDropdownModule,
  MzFeatureDiscoveryModule,
  MzIconMdiModule,
  MzIconModule,
  MzInjectionModule,
  MzInputModule,
  MzMediaModule,
  MzModalModule,
  MzNavbarModule,
  MzPaginationModule,
  MzParallaxModule,
  MzProgressModule,
  MzRadioButtonModule,
  MzSelectModule,
  MzSidenavModule,
  MzSpinnerModule,
  MzSwitchModule,
  MzTabModule,
  MzTextareaModule,
  MzTimepickerModule,
  MzToastModule,
  MzTooltipModule,
  MzValidationModule
} from 'ngx-materialize';

const MZ_MODULES = [
  MzBadgeModule,
  MzButtonModule,
  MzCardModule,
  MzCheckboxModule,
  MzChipModule,
  MzCollapsibleModule,
  MzCollectionModule,
  MzDatepickerModule,
  MzDropdownModule,
  MzFeatureDiscoveryModule,
  MzIconModule,
  MzIconMdiModule,
  MzInjectionModule,
  MzInputModule,
  MzMediaModule,
  MzModalModule,
  MzNavbarModule,
  MzPaginationModule,
  MzParallaxModule,
  MzProgressModule,
  MzRadioButtonModule,
  MzSelectModule,
  MzSidenavModule,
  MzSpinnerModule,
  MzSwitchModule,
  MzTabModule,
  MzTextareaModule,
  MzTimepickerModule,
  MzToastModule,
  MzTooltipModule,
  MzValidationModule,
];

@NgModule({
  imports: MZ_MODULES,
  exports: MZ_MODULES
})

export class CustomMaterializeModule {
}
