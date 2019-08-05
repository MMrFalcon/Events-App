import { Moment } from 'moment';
import { Event } from 'app/shared/model/event.model';
import { EventUser } from 'app/shared/model/event-user.model';

export interface IEventAttendance {
  id?: number;
  attendanceDate?: Moment;
  eventDTO?: Event;
  eventUserDTO?: EventUser;
}

export class EventAttendance implements IEventAttendance {
  constructor(public id?: number, public attendanceDate?: Moment, public eventDTO?: Event, public eventUserDTO?: EventUser) {}
}
