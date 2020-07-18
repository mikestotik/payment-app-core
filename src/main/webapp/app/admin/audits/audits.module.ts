import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PaymentAppCoreSharedModule } from 'app/shared/shared.module';

import { AuditsComponent } from './audits.component';

import { auditsRoute } from './audits.route';

@NgModule({
  imports: [PaymentAppCoreSharedModule, RouterModule.forChild([auditsRoute])],
  declarations: [AuditsComponent],
})
export class AuditsModule {}
