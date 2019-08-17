import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { User, UserService } from 'app/core';
import { EventLocationService } from 'app/entities/event-location';
import { JhiAlertService } from 'ng-jhipster';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IEventLocation } from 'app/shared/model/event-location.model';

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './user-management-update.component.html'
})
export class UserMgmtUpdateComponent implements OnInit {
  user: User;
  languages: any[];
  authorities: any[];
  isSaving: boolean;
  locations: IEventLocation[];

  editForm = this.fb.group({
    id: [null],
    login: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50), Validators.pattern('^[_.@A-Za-z0-9-]*')]],
    firstName: ['', [Validators.maxLength(50)]],
    lastName: ['', [Validators.maxLength(50)]],
    email: ['', [Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    activated: [true],
    langKey: [],
    authorities: [],
    homeLocation: []
  });

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private eventLocationService: EventLocationService,
    private jhiAlertService: JhiAlertService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.eventLocationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEventLocation[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEventLocation[]>) => response.body)
      )
      .subscribe((res: IEventLocation[]) => (this.locations = res), (res: HttpErrorResponse) => this.onError(res.message));

    this.route.data.subscribe(({ user }) => {
      this.user = user.body ? user.body : user;
      this.updateForm(this.user);
    });
    this.authorities = [];
    this.userService.authorities().subscribe(authorities => {
      this.authorities = authorities;
    });
  }

  private updateForm(user: User): void {
    this.editForm.patchValue({
      id: user.id,
      login: user.login,
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      activated: user.activated,
      langKey: user.langKey,
      authorities: user.authorities,
      homeLocation: user.homeLocation
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.updateUser(this.user);
    if (this.user.id !== null) {
      this.userService.update(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
    } else {
      this.user.langKey = 'en';
      this.userService.create(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
    }
  }

  private updateUser(user: User): void {
    user.login = this.editForm.get(['login']).value;
    user.firstName = this.editForm.get(['firstName']).value;
    user.lastName = this.editForm.get(['lastName']).value;
    user.email = this.editForm.get(['email']).value;
    user.activated = this.editForm.get(['activated']).value;
    user.langKey = this.editForm.get(['langKey']).value;
    user.authorities = this.editForm.get(['authorities']).value;
    user.homeLocation = this.editForm.get(['homeLocation']).value;
  }

  private onSaveSuccess(result) {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
