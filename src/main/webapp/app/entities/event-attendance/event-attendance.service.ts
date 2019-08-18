import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEventAttendance } from 'app/shared/model/event-attendance.model';

type EntityResponseType = HttpResponse<IEventAttendance>;
type EntityArrayResponseType = HttpResponse<IEventAttendance[]>;

@Injectable({ providedIn: 'root' })
export class EventAttendanceService {
  public resourceUrl = SERVER_API_URL + 'api/event-attendances';

  constructor(protected http: HttpClient) {}

  create(eventAttendance: IEventAttendance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventAttendance);
    return this.http
      .post<IEventAttendance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(eventAttendance: IEventAttendance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventAttendance);
    return this.http
      .put<IEventAttendance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEventAttendance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEventAttendance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  findByUserLogin(login: string): Observable<EntityArrayResponseType> {
    return this.http
      .get<IEventAttendance[]>(`${this.resourceUrl}/byUser/${login}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(eventAttendance: IEventAttendance): IEventAttendance {
    const copy: IEventAttendance = Object.assign({}, eventAttendance, {
      attendanceDate:
        eventAttendance.attendanceDate != null && eventAttendance.attendanceDate.isValid()
          ? eventAttendance.attendanceDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.attendanceDate = res.body.attendanceDate != null ? moment(res.body.attendanceDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((eventAttendance: IEventAttendance) => {
        eventAttendance.attendanceDate = eventAttendance.attendanceDate != null ? moment(eventAttendance.attendanceDate) : null;
      });
    }
    return res;
  }
}
