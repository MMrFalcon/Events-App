import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EventUser, IEventUser } from 'app/shared/model/event-user.model';
import { EventUserService } from './event-user.service';
import { EventUserComponent } from './event-user.component';
import { EventUserDetailComponent } from './event-user-detail.component';
import { EventUserUpdateComponent } from './event-user-update.component';
import { EventUserDeletePopupComponent } from './event-user-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class EventUserResolve implements Resolve<IEventUser> {
  constructor(private service: EventUserService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEventUser> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EventUser>) => response.ok),
        map((eventUser: HttpResponse<EventUser>) => eventUser.body)
      );
    }
    return of(new EventUser());
  }
}

export const eventUserRoute: Routes = [
  {
    path: '',
    component: EventUserComponent,
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'EventUsers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EventUserDetailComponent,
    resolve: {
      eventUser: EventUserResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'EventUsers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EventUserUpdateComponent,
    resolve: {
      eventUser: EventUserResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'EventUsers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EventUserUpdateComponent,
    resolve: {
      eventUser: EventUserResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'EventUsers'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const eventUserPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EventUserDeletePopupComponent,
    resolve: {
      eventUser: EventUserResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'EventUsers'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
