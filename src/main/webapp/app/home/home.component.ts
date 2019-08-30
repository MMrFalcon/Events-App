import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Account, AccountService, LoginModalService } from 'app/core';
import { EventAttendanceService } from 'app/entities/event-attendance';
import { EventAttendance, IEventAttendance } from 'app/shared/model/event-attendance.model';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { IEvent } from 'app/shared/model/event.model';
import { EventService } from 'app/entities/event';
import { Observable } from 'rxjs';
import * as moment from 'moment';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
  account: Account;
  modalRef: NgbModalRef;
  eventAttendances: IEventAttendance[];
  events: IEvent[];
  isSaving: boolean;
  duplicateAttendanceError: string;
  unhandledError: string;

  attendanceForm = this.fb.group({
    eventDTO: ['', [Validators.required]]
  });

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private eventManager: JhiEventManager,
    private eventAttendanceService: EventAttendanceService,
    private jhiAlertService: JhiAlertService,
    private fb: FormBuilder,
    private eventService: EventService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.accountService.identity().then((account: Account) => {
      this.account = account;
    });
    this.registerAuthenticationSuccess();
    this.subscribeAllEvents();
  }

  registerAuthenticationSuccess() {
    this.eventManager.subscribe('authenticationSuccess', message => {
      this.accountService.identity().then(account => {
        this.account = account;
      });
    });
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  subscribeEventAttendances(login: string) {
    if (!this.eventAttendances) {
      this.eventAttendanceService
        .findByUserLogin(login)
        .pipe(
          filter((mayBeOk: HttpResponse<IEventAttendance[]>) => mayBeOk.ok),
          map((response: HttpResponse<IEventAttendance[]>) => response.body)
        )
        .subscribe((res: IEventAttendance[]) => (this.eventAttendances = res), (res: HttpErrorResponse) => this.onError(res.message));
    }
  }

  subscribeAllEvents() {
    if (this.events === undefined) {
      this.eventService
        .query()
        .pipe(
          filter((mayBeOk: HttpResponse<IEvent[]>) => mayBeOk.ok),
          map((response: HttpResponse<IEvent[]>) => response.body)
        )
        .subscribe((res: IEvent[]) => (this.events = res), (res: HttpErrorResponse) => this.onError(res.message));
    }
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  save() {
    this.isSaving = true;
    const eventAttendance = this.createFromForm();
    this.subscribeToSaveResponse(this.eventAttendanceService.create(eventAttendance));
  }

  private createFromForm(): IEventAttendance {
    return {
      ...new EventAttendance(),
      attendanceDate: moment(),
      eventDTO: this.attendanceForm.get(['eventDTO']).value,
      userDTO: this.account
    };
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackEventById(index: number, item: IEvent) {
    return item;
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    location.reload();
  }

  protected onSaveError(errorResponse: HttpErrorResponse) {
    this.isSaving = false;
    if (errorResponse.status === 400) {
      this.duplicateAttendanceError = 'Attendance already exist in system. ';
    } else {
      this.unhandledError = 'Something went wrong, that is not your fault';
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventAttendance>>) {
    result.subscribe(() => this.onSaveSuccess(), errorResponse => this.onSaveError(errorResponse));
  }
}
