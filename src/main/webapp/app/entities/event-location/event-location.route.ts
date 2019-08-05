import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EventLocation, IEventLocation } from 'app/shared/model/event-location.model';
import { EventLocationService } from './event-location.service';
import { EventLocationComponent } from './event-location.component';
import { EventLocationDetailComponent } from './event-location-detail.component';
import { EventLocationUpdateComponent } from './event-location-update.component';
import { EventLocationDeletePopupComponent } from './event-location-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class EventLocationResolve implements Resolve<IEventLocation> {
  constructor(private service: EventLocationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEventLocation> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EventLocation>) => response.ok),
        map((eventLocation: HttpResponse<EventLocation>) => eventLocation.body)
      );
    }
    return of(new EventLocation());
  }
}

export const eventLocationRoute: Routes = [
  {
    path: '',
    component: EventLocationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'EventLocations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EventLocationDetailComponent,
    resolve: {
      eventLocation: EventLocationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'EventLocations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EventLocationUpdateComponent,
    resolve: {
      eventLocation: EventLocationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'EventLocations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EventLocationUpdateComponent,
    resolve: {
      eventLocation: EventLocationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'EventLocations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const eventLocationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EventLocationDeletePopupComponent,
    resolve: {
      eventLocation: EventLocationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'EventLocations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
