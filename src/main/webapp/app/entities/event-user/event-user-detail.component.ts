import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventUser } from 'app/shared/model/event-user.model';

@Component({
  selector: 'jhi-event-user-detail',
  templateUrl: './event-user-detail.component.html'
})
export class EventUserDetailComponent implements OnInit {
  eventUser: IEventUser;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ eventUser }) => {
      this.eventUser = eventUser;
    });
  }

  previousState() {
    window.history.back();
  }
}
