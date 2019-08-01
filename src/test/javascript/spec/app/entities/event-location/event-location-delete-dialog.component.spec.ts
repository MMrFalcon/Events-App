/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EventsTestModule } from '../../../test.module';
import { EventLocationDeleteDialogComponent } from 'app/entities/event-location/event-location-delete-dialog.component';
import { EventLocationService } from 'app/entities/event-location/event-location.service';

describe('Component Tests', () => {
  describe('EventLocation Management Delete Component', () => {
    let comp: EventLocationDeleteDialogComponent;
    let fixture: ComponentFixture<EventLocationDeleteDialogComponent>;
    let service: EventLocationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EventsTestModule],
        declarations: [EventLocationDeleteDialogComponent]
      })
        .overrideTemplate(EventLocationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EventLocationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventLocationService);
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
