/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EventsTestModule } from '../../../test.module';
import { EventLocationDetailComponent } from 'app/entities/event-location/event-location-detail.component';
import { EventLocation } from 'app/shared/model/event-location.model';

describe('Component Tests', () => {
  describe('EventLocation Management Detail Component', () => {
    let comp: EventLocationDetailComponent;
    let fixture: ComponentFixture<EventLocationDetailComponent>;
    const route = ({ data: of({ eventLocation: new EventLocation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EventsTestModule],
        declarations: [EventLocationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EventLocationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EventLocationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.eventLocation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
