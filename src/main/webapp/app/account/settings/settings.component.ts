import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { AccountService } from 'app/core';
import { EventLocationService } from 'app/entities/event-location';
import { JhiAlertService } from 'ng-jhipster';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { EventLocation, IEventLocation } from 'app/shared/model/event-location.model';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html'
})
export class SettingsComponent implements OnInit {
  error: string;
  success: string;
  languages: any[];
  locations: EventLocation[];

  settingsForm = this.fb.group({
    firstName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    lastName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    email: [undefined, [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    activated: [false],
    authorities: [[]],
    langKey: ['en'],
    login: [],
    imageUrl: [],
    homeLocation: []
  });

  constructor(
    private accountService: AccountService,
    private fb: FormBuilder,
    private eventLocationService: EventLocationService,
    private jhiAlertService: JhiAlertService
  ) {}

  ngOnInit() {
    this.eventLocationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEventLocation[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEventLocation[]>) => response.body)
      )
      .subscribe((res: IEventLocation[]) => (this.locations = res), (res: HttpErrorResponse) => this.onError(res.message));

    this.accountService.identity().then(account => {
      this.updateForm(account);
    });
  }

  save() {
    const settingsAccount = this.accountFromForm();
    this.accountService.save(settingsAccount).subscribe(
      () => {
        this.error = null;
        this.success = 'OK';
        this.accountService.identity(true).then(account => {
          this.updateForm(account);
        });
      },
      () => {
        this.success = null;
        this.error = 'ERROR';
      }
    );
  }

  private accountFromForm(): any {
    const account = {};
    return {
      ...account,
      firstName: this.settingsForm.get('firstName').value,
      lastName: this.settingsForm.get('lastName').value,
      email: this.settingsForm.get('email').value,
      activated: this.settingsForm.get('activated').value,
      authorities: this.settingsForm.get('authorities').value,
      langKey: this.settingsForm.get('langKey').value,
      login: this.settingsForm.get('login').value,
      imageUrl: this.settingsForm.get('imageUrl').value,
      homeLocation: this.settingsForm.get('homeLocation').value
    };
  }

  updateForm(account: any): void {
    this.settingsForm.patchValue({
      firstName: account.firstName,
      lastName: account.lastName,
      email: account.email,
      activated: account.activated,
      authorities: account.authorities,
      langKey: account.langKey,
      login: account.login,
      imageUrl: account.imageUrl,
      homeLocation: account.homeLocation
    });
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
