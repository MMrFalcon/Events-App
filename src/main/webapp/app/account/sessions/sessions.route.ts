import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { SessionsComponent } from './sessions.component';

export const sessionsRoute: Route = {
  path: 'sessions',
  component: SessionsComponent,
  data: {
    authorities: ['ROLE_MEMBER', 'ROLE_ADMIN', 'ROLE_ORGANIZER'],
    pageTitle: 'Sessions'
  },
  canActivate: [UserRouteAccessService]
};
