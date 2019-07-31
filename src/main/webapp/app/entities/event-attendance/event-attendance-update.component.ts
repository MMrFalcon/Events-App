import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { EventAttendance, IEventAttendance } from 'app/shared/model/event-attendance.model';
import { EventAttendanceService } from './event-attendance.service';
import { IEvent } from 'app/shared/model/event.model';
import { EventService } from 'app/entities/event';
import { IEventUser } from 'app/shared/model/event-user.model';
import { EventUserService } from 'app/entities/event-user';

@Component({
  selector: 'jhi-event-attendance-update',
  templateUrl: './event-attendance-update.component.html'
})
export class EventAttendanceUpdateComponent implements OnInit {
  isSaving: boolean;

  events: IEvent[];

  eventusers: IEventUser[];
  attendanceDateDp: any;

  editForm = this.fb.group({
    id: [],
    attendanceDate: [],
    eventId: [],
    eventUserId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected eventAttendanceService: EventAttendanceService,
    protected eventService: EventService,
    protected eventUserService: EventUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ eventAttendance }) => {
      this.updateForm(eventAttendance);
    });
    this.eventService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEvent[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEvent[]>) => response.body)
      )
      .subscribe((res: IEvent[]) => (this.events = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.eventUserService
      .query({ filter: 'eventattendance-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IEventUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEventUser[]>) => response.body)
      )
      .subscribe(
        (res: IEventUser[]) => {
          if (!!this.editForm.get('eventUserId').value) {
            this.eventusers = res;
          } else {
            this.eventUserService
              .find(this.editForm.get('eventUserId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IEventUser>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IEventUser>) => subResponse.body)
              )
              .subscribe(
                (subRes: IEventUser) => (this.eventusers = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(eventAttendance: IEventAttendance) {
    this.editForm.patchValue({
      id: eventAttendance.id,
      attendanceDate: eventAttendance.attendanceDate,
      eventId: eventAttendance.eventId,
      eventUserId: eventAttendance.eventUserId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const eventAttendance = this.createFromForm();
    if (eventAttendance.id !== undefined) {
      this.subscribeToSaveResponse(this.eventAttendanceService.update(eventAttendance));
    } else {
      this.subscribeToSaveResponse(this.eventAttendanceService.create(eventAttendance));
    }
  }

  private createFromForm(): IEventAttendance {
    return {
      ...new EventAttendance(),
      id: this.editForm.get(['id']).value,
      attendanceDate: this.editForm.get(['attendanceDate']).value,
      eventId: this.editForm.get(['eventId']).value,
      eventUserId: this.editForm.get(['eventUserId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventAttendance>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackEventById(index: number, item: IEvent) {
    return item.id;
  }

  trackEventUserById(index: number, item: IEventUser) {
    return item.id;
  }
}
