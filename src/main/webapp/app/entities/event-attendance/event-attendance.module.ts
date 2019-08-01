import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EventsSharedModule } from 'app/shared';
import {
  EventAttendanceComponent,
  EventAttendanceDeleteDialogComponent,
  EventAttendanceDeletePopupComponent,
  EventAttendanceDetailComponent,
  eventAttendancePopupRoute,
  eventAttendanceRoute,
  EventAttendanceUpdateComponent
} from './';

const ENTITY_STATES = [...eventAttendanceRoute, ...eventAttendancePopupRoute];

@NgModule({
  imports: [EventsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EventAttendanceComponent,
    EventAttendanceDetailComponent,
    EventAttendanceUpdateComponent,
    EventAttendanceDeleteDialogComponent,
    EventAttendanceDeletePopupComponent
  ],
  entryComponents: [
    EventAttendanceComponent,
    EventAttendanceUpdateComponent,
    EventAttendanceDeleteDialogComponent,
    EventAttendanceDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EventsEventAttendanceModule {}
