import { IEvent } from 'app/shared/model/event.model';

export interface IEventLocation {
  id?: number;
  locationName?: string;
  eventDayOfWeek?: number;
  events?: IEvent[];
}

export class EventLocation implements IEventLocation {
  constructor(public id?: number, public locationName?: string, public eventDayOfWeek?: number, public events?: IEvent[]) {}
}
