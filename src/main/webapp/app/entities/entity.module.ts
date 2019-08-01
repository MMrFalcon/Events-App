import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'event-location',
        loadChildren: './event-location/event-location.module#EventsEventLocationModule'
      },
      {
        path: 'event',
        loadChildren: './event/event.module#EventsEventModule'
      },
      {
        path: 'event-attendance',
        loadChildren: './event-attendance/event-attendance.module#EventsEventAttendanceModule'
      },
      {
        path: 'event-user',
        loadChildren: './event-user/event-user.module#EventsEventUserModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EventsEntityModule {}
