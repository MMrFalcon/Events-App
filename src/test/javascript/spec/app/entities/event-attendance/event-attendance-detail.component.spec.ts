/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EventsTestModule } from '../../../test.module';
import { EventAttendanceDetailComponent } from 'app/entities/event-attendance/event-attendance-detail.component';
import { EventAttendance } from 'app/shared/model/event-attendance.model';

describe('Component Tests', () => {
  describe('EventAttendance Management Detail Component', () => {
    let comp: EventAttendanceDetailComponent;
    let fixture: ComponentFixture<EventAttendanceDetailComponent>;
    const route = ({ data: of({ eventAttendance: new EventAttendance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EventsTestModule],
        declarations: [EventAttendanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EventAttendanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EventAttendanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.eventAttendance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
