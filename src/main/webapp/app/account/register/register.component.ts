import { AfterViewInit, Component, ElementRef, OnInit, Renderer } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/shared';
import { LoginModalService } from 'app/core';
import { Register } from './register.service';
import { EventLocation, IEventLocation } from 'app/shared/model/event-location.model';
import { EventLocationService } from 'app/entities/event-location';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit, AfterViewInit {
  doNotMatch: string;
  error: string;
  errorEmailExists: string;
  errorUserExists: string;
  success: boolean;
  modalRef: NgbModalRef;
  locations: EventLocation[];

  registerForm = this.fb.group({
    login: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50), Validators.pattern('^[_.@A-Za-z0-9-]*$')]],
    email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    homeLocation: ['', [Validators.required]]
  });

  constructor(
    private loginModalService: LoginModalService,
    private registerService: Register,
    private elementRef: ElementRef,
    private renderer: Renderer,
    private fb: FormBuilder,
    private eventLocationService: EventLocationService,
    private jhiAlertService: JhiAlertService
  ) {}

  ngOnInit() {
    this.success = false;
    this.eventLocationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEventLocation[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEventLocation[]>) => response.body)
      )
      .subscribe((res: IEventLocation[]) => (this.locations = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  ngAfterViewInit() {
    this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#login'), 'focus', []);
  }

  register() {
    let registerAccount = {};
    const login = this.registerForm.get(['login']).value;
    const email = this.registerForm.get(['email']).value;
    const password = this.registerForm.get(['password']).value;
    const homeLocation = this.registerForm.get(['homeLocation']).value;

    if (password !== this.registerForm.get(['confirmPassword']).value) {
      this.doNotMatch = 'ERROR';
    } else {
      registerAccount = { ...registerAccount, login, email, password, homeLocation };
      this.doNotMatch = null;
      this.error = null;
      this.errorUserExists = null;
      this.errorEmailExists = null;
      registerAccount = { ...registerAccount, langKey: 'en' };

      this.registerService.save(registerAccount).subscribe(
        () => {
          this.success = true;
        },
        response => this.processError(response)
      );
    }
  }

  openLogin() {
    this.modalRef = this.loginModalService.open();
  }

  private processError(response: HttpErrorResponse) {
    this.success = null;
    if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
      this.errorUserExists = 'ERROR';
    } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
      this.errorEmailExists = 'ERROR';
    } else {
      this.error = 'ERROR';
    }
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
