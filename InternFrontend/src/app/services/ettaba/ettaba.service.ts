import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {Ettaba} from "../../model/ettaba/ettaba";
import {environment} from "../../../environments/environment";
import {throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class EttabaService {

  private apiServerUrl=environment.apiBaseUrl+'/api';

  constructor(private http:HttpClient) { }

  public getEttabas(): Observable<Ettaba[]>{
    return this.http.get<Ettaba[]>(`${this.apiServerUrl}/ettaba`);
  }

  public getEttabaById(id:number): Observable<Ettaba>{
    return this.http.get<Ettaba>(`${this.apiServerUrl}/ettaba/${id}`);
  }

  public getEttabasByFarmId(farmId:number): Observable<Ettaba[]>{
    return this.http.get<Ettaba[]>(`${this.apiServerUrl}/farm/${farmId}/ettaba`);
  }

  public addEttaba(ettaba:Ettaba): Observable<Ettaba>{
    return this.http.post<Ettaba>(`${this.apiServerUrl}/ettaba`,ettaba);
  }

  public addEttabaToFarmById(farmId:number,ettaba:Ettaba): Observable<Ettaba>{
    return this.http.post<Ettaba>(`${this.apiServerUrl}/farm/${farmId}/ettaba`,ettaba);
  }

  public updateEttaba(ettabaId:number, ettaba:Ettaba): Observable<Ettaba>{
    return this.http.put<Ettaba>(`${this.apiServerUrl}/ettaba/${ettabaId}`,ettaba);
  }

  public deleteEttaba(ettabaId:number): Observable<Ettaba>{
    return this.http.delete<Ettaba>(`${this.apiServerUrl}/ettaba/${ettabaId}`);
  }

  public deleteEttabasFromFarmById(farmId:number): Observable<Ettaba>{
    return this.http.delete<Ettaba>(`${this.apiServerUrl}/farm/${farmId}/ettaba`);
  }
}
