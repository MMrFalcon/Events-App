import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventAttendance } from 'app/shared/model/event-attendance.model';

@Component({
  selector: 'jhi-event-attendance-detail',
  templateUrl: './event-attendance-detail.component.html'
})
export class EventAttendanceDetailComponent implements OnInit {
  eventAttendance: IEventAttendance;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ eventAttendance }) => {
      this.eventAttendance = eventAttendance;
    });
  }

  previousState() {
    window.history.back();
  }
}
