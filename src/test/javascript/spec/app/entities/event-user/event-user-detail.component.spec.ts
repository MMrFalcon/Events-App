/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EventsTestModule } from '../../../test.module';
import { EventUserDetailComponent } from 'app/entities/event-user/event-user-detail.component';
import { EventUser } from 'app/shared/model/event-user.model';

describe('Component Tests', () => {
  describe('EventUser Management Detail Component', () => {
    let comp: EventUserDetailComponent;
    let fixture: ComponentFixture<EventUserDetailComponent>;
    const route = ({ data: of({ eventUser: new EventUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EventsTestModule],
        declarations: [EventUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EventUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EventUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.eventUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
