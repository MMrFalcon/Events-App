import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { EventUser, IEventUser } from 'app/shared/model/event-user.model';
import { EventUserService } from './event-user.service';
import { IEventLocation } from 'app/shared/model/event-location.model';
import { EventLocationService } from 'app/entities/event-location';

@Component({
  selector: 'jhi-event-user-update',
  templateUrl: './event-user-update.component.html'
})
export class EventUserUpdateComponent implements OnInit {
  isSaving: boolean;

  homelocations: IEventLocation[];

  editForm = this.fb.group({
    id: [],
    username: [],
    homeLocationId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected eventUserService: EventUserService,
    protected eventLocationService: EventLocationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ eventUser }) => {
      this.updateForm(eventUser);
    });
    this.eventLocationService
      .query({ filter: 'eventuser-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IEventLocation[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEventLocation[]>) => response.body)
      )
      .subscribe(
        (res: IEventLocation[]) => {
          if (!!this.editForm.get('homeLocationId').value) {
            this.homelocations = res;
          } else {
            this.eventLocationService
              .find(this.editForm.get('homeLocationId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IEventLocation>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IEventLocation>) => subResponse.body)
              )
              .subscribe(
                (subRes: IEventLocation) => (this.homelocations = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(eventUser: IEventUser) {
    this.editForm.patchValue({
      id: eventUser.id,
      username: eventUser.username,
      homeLocationId: eventUser.homeLocationId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const eventUser = this.createFromForm();
    if (eventUser.id !== undefined) {
      this.subscribeToSaveResponse(this.eventUserService.update(eventUser));
    } else {
      this.subscribeToSaveResponse(this.eventUserService.create(eventUser));
    }
  }

  private createFromForm(): IEventUser {
    return {
      ...new EventUser(),
      id: this.editForm.get(['id']).value,
      username: this.editForm.get(['username']).value,
      homeLocationId: this.editForm.get(['homeLocationId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventUser>>) {
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
