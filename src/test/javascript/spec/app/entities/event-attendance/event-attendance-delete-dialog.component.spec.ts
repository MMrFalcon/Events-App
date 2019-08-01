/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EventsTestModule } from '../../../test.module';
import { EventAttendanceDeleteDialogComponent } from 'app/entities/event-attendance/event-attendance-delete-dialog.component';
import { EventAttendanceService } from 'app/entities/event-attendance/event-attendance.service';

describe('Component Tests', () => {
  describe('EventAttendance Management Delete Component', () => {
    let comp: EventAttendanceDeleteDialogComponent;
    let fixture: ComponentFixture<EventAttendanceDeleteDialogComponent>;
    let service: EventAttendanceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EventsTestModule],
        declarations: [EventAttendanceDeleteDialogComponent]
      })
        .overrideTemplate(EventAttendanceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EventAttendanceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventAttendanceService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
