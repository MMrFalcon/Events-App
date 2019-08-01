/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EventsTestModule } from '../../../test.module';
import { EventUserUpdateComponent } from 'app/entities/event-user/event-user-update.component';
import { EventUserService } from 'app/entities/event-user/event-user.service';
import { EventUser } from 'app/shared/model/event-user.model';

describe('Component Tests', () => {
  describe('EventUser Management Update Component', () => {
    let comp: EventUserUpdateComponent;
    let fixture: ComponentFixture<EventUserUpdateComponent>;
    let service: EventUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EventsTestModule],
        declarations: [EventUserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EventUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EventUser(123);
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
        const entity = new EventUser();
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
