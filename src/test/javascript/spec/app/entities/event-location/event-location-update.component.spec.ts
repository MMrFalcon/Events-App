/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EventsTestModule } from '../../../test.module';
import { EventLocationUpdateComponent } from 'app/entities/event-location/event-location-update.component';
import { EventLocationService } from 'app/entities/event-location/event-location.service';
import { EventLocation } from 'app/shared/model/event-location.model';

describe('Component Tests', () => {
  describe('EventLocation Management Update Component', () => {
    let comp: EventLocationUpdateComponent;
    let fixture: ComponentFixture<EventLocationUpdateComponent>;
    let service: EventLocationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EventsTestModule],
        declarations: [EventLocationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EventLocationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventLocationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventLocationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EventLocation(123);
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
        const entity = new EventLocation();
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
