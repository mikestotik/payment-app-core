export interface IPaymentCard {
  id?: number;
  fullName?: string;
  cardNumber?: number;
  expiryMonth?: number;
  expiryYear?: number;
  ownerLogin?: string;
  ownerId?: number;
}

export class PaymentCard implements IPaymentCard {
  constructor(
    public id?: number,
    public fullName?: string,
    public cardNumber?: number,
    public expiryMonth?: number,
    public expiryYear?: number,
    public ownerLogin?: string,
    public ownerId?: number
  ) {}
}
