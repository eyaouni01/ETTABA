import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {Event} from "../../model/event/event";

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private apiServerUrl=environment.apiBaseUrl+'/api';

  constructor(private http:HttpClient) { }

  public getEventById(id:number): Observable<Event>{
    return this.http.get<Event>(`${this.apiServerUrl}/event/${id}`);
  }

  public getEvents(): Observable<Event[]>{
    return this.http.get<Event[]>(`${this.apiServerUrl}/event`);
  }

  public getEventsPromise(): Promise<Event[] | undefined>{
    return this.http.get<Event[]>(`${this.apiServerUrl}/event`).toPromise();
  }

  public getEventsByFarmId(farmId:number): Observable<Event[]>{
    return this.http.get<Event[]>(`${this.apiServerUrl}/farm/${farmId}/event`);
  }

  public addEvent(event:FormData): Observable<Event>{
    return this.http.post<Event>(`${this.apiServerUrl}/event`,event);
  }

  public addEventToFarmById(farmId:number,event:FormData): Observable<any>{
    return this.http.post<any>(`${this.apiServerUrl}/farm/${farmId}/event`,event);
  }

  public updateEvent(eventId:number, event:Event): Observable<Event>{
    return this.http.put<Event>(`${this.apiServerUrl}/event/${eventId}`,event);
  }

  public deleteEvent(eventId:number): Observable<Event>{
    return this.http.delete<Event>(`${this.apiServerUrl}/event/${eventId}`);
  }

  public deleteEventsFromFarmById(farmId:number): Observable<Event>{
    return this.http.delete<Event>(`${this.apiServerUrl}/farm/${farmId}/event`);
  }
}
