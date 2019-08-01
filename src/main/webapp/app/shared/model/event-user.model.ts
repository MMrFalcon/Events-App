import { IEventAttendance } from 'app/shared/model/event-attendance.model';

export interface IEventUser {
  id?: number;
  username?: string;
  homeLocationId?: number;
  eventAttendances?: IEventAttendance[];
}

export class EventUser implements IEventUser {
  constructor(public id?: number, public username?: string, public homeLocationId?: number, public eventAttendances?: IEventAttendance[]) {}
}
