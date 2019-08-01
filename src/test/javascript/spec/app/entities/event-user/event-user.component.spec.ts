/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EventsTestModule } from '../../../test.module';
import { EventUserComponent } from 'app/entities/event-user/event-user.component';
import { EventUserService } from 'app/entities/event-user/event-user.service';
import { EventUser } from 'app/shared/model/event-user.model';

describe('Component Tests', () => {
  describe('EventUser Management Component', () => {
    let comp: EventUserComponent;
    let fixture: ComponentFixture<EventUserComponent>;
    let service: EventUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EventsTestModule],
        declarations: [EventUserComponent],
        providers: []
      })
        .overrideTemplate(EventUserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventUserComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventUserService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EventUser(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.eventUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
