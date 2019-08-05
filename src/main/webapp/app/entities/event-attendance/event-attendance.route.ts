import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EventAttendance, IEventAttendance } from 'app/shared/model/event-attendance.model';
import { EventAttendanceService } from './event-attendance.service';
import { EventAttendanceComponent } from './event-attendance.component';
import { EventAttendanceDetailComponent } from './event-attendance-detail.component';
import { EventAttendanceUpdateComponent } from './event-attendance-update.component';
import { EventAttendanceDeletePopupComponent } from './event-attendance-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class EventAttendanceResolve implements Resolve<IEventAttendance> {
  constructor(private service: EventAttendanceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEventAttendance> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EventAttendance>) => response.ok),
        map((eventAttendance: HttpResponse<EventAttendance>) => eventAttendance.body)
      );
    }
    return of(new EventAttendance());
  }
}

export const eventAttendanceRoute: Routes = [
  {
    path: '',
    component: EventAttendanceComponent,
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'EventAttendances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EventAttendanceDetailComponent,
    resolve: {
      eventAttendance: EventAttendanceResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'EventAttendances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EventAttendanceUpdateComponent,
    resolve: {
      eventAttendance: EventAttendanceResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'EventAttendances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EventAttendanceUpdateComponent,
    resolve: {
      eventAttendance: EventAttendanceResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'EventAttendances'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const eventAttendancePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EventAttendanceDeletePopupComponent,
    resolve: {
      eventAttendance: EventAttendanceResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'EventAttendances'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
