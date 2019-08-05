import { Moment } from 'moment';
import { IEventAttendance } from 'app/shared/model/event-attendance.model';
import { EventLocation } from 'app/shared/model/event-location.model';

export interface IEvent {
  id?: number;
  eventDate?: Moment;
  eventCode?: string;
  eventLocationDTO?: EventLocation;
  eventAttendances?: IEventAttendance[];
}

export class Event implements IEvent {
  constructor(
    public id?: number,
    public eventDate?: Moment,
    public eventCode?: string,
    public eventLocationDTO?: EventLocation,
    public eventAttendances?: IEventAttendance[]
  ) {}
}
