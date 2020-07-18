export interface IContact {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  ownerLogin?: string;
  ownerId?: number;
}

export class Contact implements IContact {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public ownerLogin?: string,
    public ownerId?: number
  ) {}
}
