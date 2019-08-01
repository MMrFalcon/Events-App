import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEventUser } from 'app/shared/model/event-user.model';

type EntityResponseType = HttpResponse<IEventUser>;
type EntityArrayResponseType = HttpResponse<IEventUser[]>;

@Injectable({ providedIn: 'root' })
export class EventUserService {
  public resourceUrl = SERVER_API_URL + 'api/event-users';

  constructor(protected http: HttpClient) {}

  create(eventUser: IEventUser): Observable<EntityResponseType> {
    return this.http.post<IEventUser>(this.resourceUrl, eventUser, { observe: 'response' });
  }

  update(eventUser: IEventUser): Observable<EntityResponseType> {
    return this.http.put<IEventUser>(this.resourceUrl, eventUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEventUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEventUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
