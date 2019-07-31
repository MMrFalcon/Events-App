import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEventLocation } from 'app/shared/model/event-location.model';

type EntityResponseType = HttpResponse<IEventLocation>;
type EntityArrayResponseType = HttpResponse<IEventLocation[]>;

@Injectable({ providedIn: 'root' })
export class EventLocationService {
  public resourceUrl = SERVER_API_URL + 'api/event-locations';

  constructor(protected http: HttpClient) {}

  create(eventLocation: IEventLocation): Observable<EntityResponseType> {
    return this.http.post<IEventLocation>(this.resourceUrl, eventLocation, { observe: 'response' });
  }

  update(eventLocation: IEventLocation): Observable<EntityResponseType> {
    return this.http.put<IEventLocation>(this.resourceUrl, eventLocation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEventLocation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEventLocation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
