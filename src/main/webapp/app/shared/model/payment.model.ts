import { Moment } from 'moment';
import { MethodType } from 'app/shared/model/enumerations/method-type.model';

export interface IPayment {
  id?: number;
  amount?: number;
  methodType?: MethodType;
  methodId?: number;
  created?: Moment;
  ownerLogin?: string;
  ownerId?: number;
  contactId?: number;
}

export class Payment implements IPayment {
  constructor(
    public id?: number,
    public amount?: number,
    public methodType?: MethodType,
    public methodId?: number,
    public created?: Moment,
    public ownerLogin?: string,
    public ownerId?: number,
    public contactId?: number
  ) {}
}
