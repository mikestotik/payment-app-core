import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { PaymentAppCoreSharedModule } from 'app/shared/shared.module';
import { PaymentAppCoreCoreModule } from 'app/core/core.module';
import { PaymentAppCoreAppRoutingModule } from './app-routing.module';
import { PaymentAppCoreHomeModule } from './home/home.module';
import { PaymentAppCoreEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    PaymentAppCoreSharedModule,
    PaymentAppCoreCoreModule,
    PaymentAppCoreHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    PaymentAppCoreEntityModule,
    PaymentAppCoreAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class PaymentAppCoreAppModule {}
