import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPayment, Payment } from 'app/shared/model/payment.model';
import { PaymentService } from './payment.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from 'app/entities/contact/contact.service';

type SelectableEntity = IUser | IContact;

@Component({
  selector: 'pa-payment-update',
  templateUrl: './payment-update.component.html',
})
export class PaymentUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  contacts: IContact[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [],
    methodType: [],
    methodId: [],
    created: [],
    ownerId: [],
    contactId: [],
  });

  constructor(
    protected paymentService: PaymentService,
    protected userService: UserService,
    protected contactService: ContactService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payment }) => {
      if (!payment.id) {
        const today = moment().startOf('day');
        payment.created = today;
      }

      this.updateForm(payment);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.contactService.query().subscribe((res: HttpResponse<IContact[]>) => (this.contacts = res.body || []));
    });
  }

  updateForm(payment: IPayment): void {
    this.editForm.patchValue({
      id: payment.id,
      amount: payment.amount,
      methodType: payment.methodType,
      methodId: payment.methodId,
      created: payment.created ? payment.created.format(DATE_TIME_FORMAT) : null,
      ownerId: payment.ownerId,
      contactId: payment.contactId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payment = this.createFromForm();
    if (payment.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentService.update(payment));
    } else {
      this.subscribeToSaveResponse(this.paymentService.create(payment));
    }
  }

  private createFromForm(): IPayment {
    return {
      ...new Payment(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      methodType: this.editForm.get(['methodType'])!.value,
      methodId: this.editForm.get(['methodId'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      ownerId: this.editForm.get(['ownerId'])!.value,
      contactId: this.editForm.get(['contactId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayment>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
