import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Farm} from "../../model/farm/farm";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class FarmService {


  private apiServerUrl=environment.apiBaseUrl+'/api';

  constructor(private http:HttpClient) { }

  public getFarms(): Observable<Farm[]>{
    return this.http.get<Farm[]>(`${this.apiServerUrl}/farm`);
  }

  public getFarmById(farmId:number): Observable<Farm>{
    return this.http.get<Farm>(`${this.apiServerUrl}/farm/${farmId}`);
  }

  public addFarm(farm:FormData): Observable<Farm>{
    return this.http.post<Farm>(`${this.apiServerUrl}/farm`,farm);
  }

  public updateFarm(farmId:number, farm:Farm): Observable<Farm>{
    return this.http.put<Farm>(`${this.apiServerUrl}/farm/${farmId}`,farm);
  }

  public deleteFarm(farmId:number): Observable<Farm>{
    return this.http.delete<Farm>(`${this.apiServerUrl}/farm/${farmId}`);
  }
}
