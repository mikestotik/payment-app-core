import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaymentAppCoreSharedModule } from 'app/shared/shared.module';
import { PaymentCardComponent } from './payment-card.component';
import { PaymentCardDetailComponent } from './payment-card-detail.component';
import { PaymentCardUpdateComponent } from './payment-card-update.component';
import { PaymentCardDeleteDialogComponent } from './payment-card-delete-dialog.component';
import { paymentCardRoute } from './payment-card.route';

@NgModule({
  imports: [PaymentAppCoreSharedModule, RouterModule.forChild(paymentCardRoute)],
  declarations: [PaymentCardComponent, PaymentCardDetailComponent, PaymentCardUpdateComponent, PaymentCardDeleteDialogComponent],
  entryComponents: [PaymentCardDeleteDialogComponent],
})
export class PaymentAppCorePaymentCardModule {}
