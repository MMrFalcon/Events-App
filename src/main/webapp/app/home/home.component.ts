import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Account, AccountService, LoginModalService } from 'app/core';
import { EventAttendanceService } from 'app/entities/event-attendance';
import { IEventAttendance } from 'app/shared/model/event-attendance.model';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
  account: Account;
  modalRef: NgbModalRef;
  eventAttendances: IEventAttendance[];

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private eventManager: JhiEventManager,
    private eventAttendanceService: EventAttendanceService,
    private jhiAlertService: JhiAlertService
  ) {}

  ngOnInit() {
    this.accountService.identity().then((account: Account) => {
      // this.subscribeEventAttendances();
      this.account = account;
    });
    this.registerAuthenticationSuccess();
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

  login() {
    this.modalRef = this.loginModalService.open();
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
