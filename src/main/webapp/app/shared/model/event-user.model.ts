export interface IEventUser {
  id?: number;
  username?: string;
  homeLocationId?: number;
}

export class EventUser implements IEventUser {
  constructor(public id?: number, public username?: string, public homeLocationId?: number) {}
}
