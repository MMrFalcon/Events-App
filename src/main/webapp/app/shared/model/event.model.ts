import { Moment } from 'moment';
import { IEventAttendance } from 'app/shared/model/event-attendance.model';

export interface IEvent {
  id?: number;
  eventDate?: Moment;
  eventCode?: string;
  eventLocationId?: number;
  eventAttendances?: IEventAttendance[];
}

export class Event implements IEvent {
  constructor(
    public id?: number,
    public eventDate?: Moment,
    public eventCode?: string,
    public eventLocationId?: number,
    public eventAttendances?: IEventAttendance[]
  ) {}
}
