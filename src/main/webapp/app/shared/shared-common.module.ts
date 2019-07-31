import { NgModule } from '@angular/core';

import { EventsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [EventsSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [EventsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class EventsSharedCommonModule {}
