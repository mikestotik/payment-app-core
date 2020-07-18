import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'payment',
        loadChildren: () => import('./payment/payment.module').then(m => m.PaymentAppCorePaymentModule),
      },
      {
        path: 'payment-card',
        loadChildren: () => import('./payment-card/payment-card.module').then(m => m.PaymentAppCorePaymentCardModule),
      },
      {
        path: 'payment-account',
        loadChildren: () => import('./payment-account/payment-account.module').then(m => m.PaymentAppCorePaymentAccountModule),
      },
      {
        path: 'contact',
        loadChildren: () => import('./contact/contact.module').then(m => m.PaymentAppCoreContactModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class PaymentAppCoreEntityModule {}
