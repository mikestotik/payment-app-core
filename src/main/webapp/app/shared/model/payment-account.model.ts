export interface IPaymentAccount {
  id?: number;
  accountName?: string;
  accountNumber?: string;
  bsb?: string;
  ownerLogin?: string;
  ownerId?: number;
}

export class PaymentAccount implements IPaymentAccount {
  constructor(
    public id?: number,
    public accountName?: string,
    public accountNumber?: string,
    public bsb?: string,
    public ownerLogin?: string,
    public ownerId?: number
  ) {}
}
