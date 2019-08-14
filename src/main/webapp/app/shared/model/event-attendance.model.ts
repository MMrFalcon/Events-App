import { Moment } from 'moment';
import { Event } from 'app/shared/model/event.model';
import { User } from 'app/core';

export interface IEventAttendance {
  id?: number;
  attendanceDate?: Moment;
  eventDTO?: Event;
  userDTO?: User;
}

export class EventAttendance implements IEventAttendance {
  constructor(public id?: number, public attendanceDate?: Moment, public eventDTO?: Event, public userDTO?: User) {}
}
