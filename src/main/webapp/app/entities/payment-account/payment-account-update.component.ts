import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPaymentAccount, PaymentAccount } from 'app/shared/model/payment-account.model';
import { PaymentAccountService } from './payment-account.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'pa-payment-account-update',
  templateUrl: './payment-account-update.component.html',
})
export class PaymentAccountUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    accountName: [],
    accountNumber: [],
    bsb: [],
    ownerId: [],
  });

  constructor(
    protected paymentAccountService: PaymentAccountService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentAccount }) => {
      this.updateForm(paymentAccount);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(paymentAccount: IPaymentAccount): void {
    this.editForm.patchValue({
      id: paymentAccount.id,
      accountName: paymentAccount.accountName,
      accountNumber: paymentAccount.accountNumber,
      bsb: paymentAccount.bsb,
      ownerId: paymentAccount.ownerId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentAccount = this.createFromForm();
    if (paymentAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentAccountService.update(paymentAccount));
    } else {
      this.subscribeToSaveResponse(this.paymentAccountService.create(paymentAccount));
    }
  }

  private createFromForm(): IPaymentAccount {
    return {
      ...new PaymentAccount(),
      id: this.editForm.get(['id'])!.value,
      accountName: this.editForm.get(['accountName'])!.value,
      accountNumber: this.editForm.get(['accountNumber'])!.value,
      bsb: this.editForm.get(['bsb'])!.value,
      ownerId: this.editForm.get(['ownerId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentAccount>>): void {
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
