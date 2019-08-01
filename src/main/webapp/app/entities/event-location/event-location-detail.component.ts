import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventLocation } from 'app/shared/model/event-location.model';

@Component({
  selector: 'jhi-event-location-detail',
  templateUrl: './event-location-detail.component.html'
})
export class EventLocationDetailComponent implements OnInit {
  eventLocation: IEventLocation;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ eventLocation }) => {
      this.eventLocation = eventLocation;
    });
  }

  previousState() {
    window.history.back();
  }
}
