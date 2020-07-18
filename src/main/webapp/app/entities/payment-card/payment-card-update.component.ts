import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPaymentCard, PaymentCard } from 'app/shared/model/payment-card.model';
import { PaymentCardService } from './payment-card.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'pa-payment-card-update',
  templateUrl: './payment-card-update.component.html',
})
export class PaymentCardUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    fullName: [],
    cardNumber: [],
    expiryMonth: [],
    expiryYear: [],
    ownerId: [],
  });

  constructor(
    protected paymentCardService: PaymentCardService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentCard }) => {
      this.updateForm(paymentCard);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(paymentCard: IPaymentCard): void {
    this.editForm.patchValue({
      id: paymentCard.id,
      fullName: paymentCard.fullName,
      cardNumber: paymentCard.cardNumber,
      expiryMonth: paymentCard.expiryMonth,
      expiryYear: paymentCard.expiryYear,
      ownerId: paymentCard.ownerId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentCard = this.createFromForm();
    if (paymentCard.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentCardService.update(paymentCard));
    } else {
      this.subscribeToSaveResponse(this.paymentCardService.create(paymentCard));
    }
  }

  private createFromForm(): IPaymentCard {
    return {
      ...new PaymentCard(),
      id: this.editForm.get(['id'])!.value,
      fullName: this.editForm.get(['fullName'])!.value,
      cardNumber: this.editForm.get(['cardNumber'])!.value,
      expiryMonth: this.editForm.get(['expiryMonth'])!.value,
      expiryYear: this.editForm.get(['expiryYear'])!.value,
      ownerId: this.editForm.get(['ownerId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentCard>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
