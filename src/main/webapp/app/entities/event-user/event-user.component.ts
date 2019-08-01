import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { IEventUser } from 'app/shared/model/event-user.model';
import { AccountService } from 'app/core';
import { EventUserService } from './event-user.service';

@Component({
  selector: 'jhi-event-user',
  templateUrl: './event-user.component.html'
})
export class EventUserComponent implements OnInit, OnDestroy {
  eventUsers: IEventUser[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected eventUserService: EventUserService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.eventUserService
      .query()
      .pipe(
        filter((res: HttpResponse<IEventUser[]>) => res.ok),
        map((res: HttpResponse<IEventUser[]>) => res.body)
      )
      .subscribe(
        (res: IEventUser[]) => {
          this.eventUsers = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEventUsers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEventUser) {
    return item.id;
  }

  registerChangeInEventUsers() {
    this.eventSubscriber = this.eventManager.subscribe('eventUserListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
