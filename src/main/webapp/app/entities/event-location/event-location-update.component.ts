import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { EventLocation, IEventLocation } from 'app/shared/model/event-location.model';
import { EventLocationService } from './event-location.service';

@Component({
  selector: 'jhi-event-location-update',
  templateUrl: './event-location-update.component.html'
})
export class EventLocationUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    locationName: [null, [Validators.required]],
    eventDayOfWeek: []
  });

  constructor(protected eventLocationService: EventLocationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ eventLocation }) => {
      this.updateForm(eventLocation);
    });
  }

  updateForm(eventLocation: IEventLocation) {
    this.editForm.patchValue({
      id: eventLocation.id,
      locationName: eventLocation.locationName,
      eventDayOfWeek: eventLocation.eventDayOfWeek
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const eventLocation = this.createFromForm();
    if (eventLocation.id !== undefined) {
      this.subscribeToSaveResponse(this.eventLocationService.update(eventLocation));
    } else {
      this.subscribeToSaveResponse(this.eventLocationService.create(eventLocation));
    }
  }

  private createFromForm(): IEventLocation {
    return {
      ...new EventLocation(),
      id: this.editForm.get(['id']).value,
      locationName: this.editForm.get(['locationName']).value,
      eventDayOfWeek: this.editForm.get(['eventDayOfWeek']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventLocation>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
