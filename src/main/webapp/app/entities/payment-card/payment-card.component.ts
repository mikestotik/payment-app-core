import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentCard } from 'app/shared/model/payment-card.model';
import { PaymentCardService } from './payment-card.service';
import { PaymentCardDeleteDialogComponent } from './payment-card-delete-dialog.component';

@Component({
  selector: 'pa-payment-card',
  templateUrl: './payment-card.component.html',
})
export class PaymentCardComponent implements OnInit, OnDestroy {
  paymentCards?: IPaymentCard[];
  eventSubscriber?: Subscription;

  constructor(
    protected paymentCardService: PaymentCardService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.paymentCardService.query().subscribe((res: HttpResponse<IPaymentCard[]>) => (this.paymentCards = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPaymentCards();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPaymentCard): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPaymentCards(): void {
    this.eventSubscriber = this.eventManager.subscribe('paymentCardListModification', () => this.loadAll());
  }

  delete(paymentCard: IPaymentCard): void {
    const modalRef = this.modalService.open(PaymentCardDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paymentCard = paymentCard;
  }
}
