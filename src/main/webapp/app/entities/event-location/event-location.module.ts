import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EventsSharedModule } from 'app/shared';
import {
  EventLocationComponent,
  EventLocationDeleteDialogComponent,
  EventLocationDeletePopupComponent,
  EventLocationDetailComponent,
  eventLocationPopupRoute,
  eventLocationRoute,
  EventLocationUpdateComponent
} from './';

const ENTITY_STATES = [...eventLocationRoute, ...eventLocationPopupRoute];

@NgModule({
  imports: [EventsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EventLocationComponent,
    EventLocationDetailComponent,
    EventLocationUpdateComponent,
    EventLocationDeleteDialogComponent,
    EventLocationDeletePopupComponent
  ],
  entryComponents: [
    EventLocationComponent,
    EventLocationUpdateComponent,
    EventLocationDeleteDialogComponent,
    EventLocationDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EventsEventLocationModule {}
