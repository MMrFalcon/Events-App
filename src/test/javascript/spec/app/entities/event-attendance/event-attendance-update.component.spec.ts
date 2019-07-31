/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EventsTestModule } from '../../../test.module';
import { EventAttendanceUpdateComponent } from 'app/entities/event-attendance/event-attendance-update.component';
import { EventAttendanceService } from 'app/entities/event-attendance/event-attendance.service';
import { EventAttendance } from 'app/shared/model/event-attendance.model';

describe('Component Tests', () => {
  describe('EventAttendance Management Update Component', () => {
    let comp: EventAttendanceUpdateComponent;
    let fixture: ComponentFixture<EventAttendanceUpdateComponent>;
    let service: EventAttendanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EventsTestModule],
        declarations: [EventAttendanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EventAttendanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventAttendanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventAttendanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EventAttendance(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new EventAttendance();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
