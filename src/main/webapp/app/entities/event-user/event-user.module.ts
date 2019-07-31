import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EventsSharedModule } from 'app/shared';
import {
  EventUserComponent,
  EventUserDeleteDialogComponent,
  EventUserDeletePopupComponent,
  EventUserDetailComponent,
  eventUserPopupRoute,
  eventUserRoute,
  EventUserUpdateComponent
} from './';

const ENTITY_STATES = [...eventUserRoute, ...eventUserPopupRoute];

@NgModule({
  imports: [EventsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EventUserComponent,
    EventUserDetailComponent,
    EventUserUpdateComponent,
    EventUserDeleteDialogComponent,
    EventUserDeletePopupComponent
  ],
  entryComponents: [EventUserComponent, EventUserUpdateComponent, EventUserDeleteDialogComponent, EventUserDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EventsEventUserModule {}
