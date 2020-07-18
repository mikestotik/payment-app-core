import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaymentAppCoreSharedModule } from 'app/shared/shared.module';
import { PaymentAccountComponent } from './payment-account.component';
import { PaymentAccountDetailComponent } from './payment-account-detail.component';
import { PaymentAccountUpdateComponent } from './payment-account-update.component';
import { PaymentAccountDeleteDialogComponent } from './payment-account-delete-dialog.component';
import { paymentAccountRoute } from './payment-account.route';

@NgModule({
  imports: [PaymentAppCoreSharedModule, RouterModule.forChild(paymentAccountRoute)],
  declarations: [
    PaymentAccountComponent,
    PaymentAccountDetailComponent,
    PaymentAccountUpdateComponent,
    PaymentAccountDeleteDialogComponent,
  ],
  entryComponents: [PaymentAccountDeleteDialogComponent],
})
export class PaymentAppCorePaymentAccountModule {}
