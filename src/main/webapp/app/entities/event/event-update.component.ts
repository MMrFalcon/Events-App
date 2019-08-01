import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { Event, IEvent } from 'app/shared/model/event.model';
import { EventService } from './event.service';
import { IEventLocation } from 'app/shared/model/event-location.model';
import { EventLocationService } from 'app/entities/event-location';

@Component({
  selector: 'jhi-event-update',
  templateUrl: './event-update.component.html'
})
export class EventUpdateComponent implements OnInit {
  isSaving: boolean;

  eventlocations: IEventLocation[];
  eventDateDp: any;

  editForm = this.fb.group({
    id: [],
    eventDate: [null, [Validators.required]],
    eventCode: [],
    eventLocationId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected eventService: EventService,
    protected eventLocationService: EventLocationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ event }) => {
      this.updateForm(event);
    });
    this.eventLocationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEventLocation[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEventLocation[]>) => response.body)
      )
      .subscribe((res: IEventLocation[]) => (this.eventlocations = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(event: IEvent) {
    this.editForm.patchValue({
      id: event.id,
      eventDate: event.eventDate,
      eventCode: event.eventCode,
      eventLocationId: event.eventLocationId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const event = this.createFromForm();
    if (event.id !== undefined) {
      this.subscribeToSaveResponse(this.eventService.update(event));
    } else {
      this.subscribeToSaveResponse(this.eventService.create(event));
    }
  }

  private createFromForm(): IEvent {
    return {
      ...new Event(),
      id: this.editForm.get(['id']).value,
      eventDate: this.editForm.get(['eventDate']).value,
      eventCode: this.editForm.get(['eventCode']).value,
      eventLocationId: this.editForm.get(['eventLocationId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>) {
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

  trackEventLocationById(index: number, item: IEventLocation) {
    return item.id;
  }
}
