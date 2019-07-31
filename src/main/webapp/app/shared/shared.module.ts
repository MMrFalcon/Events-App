import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { EventsSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [EventsSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [EventsSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EventsSharedModule {
  static forRoot() {
    return {
      ngModule: EventsSharedModule
    };
  }
}
