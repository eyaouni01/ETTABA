import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Animal} from "../../model/animal/animal";

@Injectable({
  providedIn: 'root'
})
export class AnimalService {

  private apiServerUrl=environment.apiBaseUrl+'/api';

  constructor(private http:HttpClient) { }

  public getAnimals(): Observable<Animal[]>{
    return this.http.get<Animal[]>(`${this.apiServerUrl}/animal`);
  }

  public getAnimalsPromise(): Promise<Animal[] | undefined>{
    return this.http.get<Animal[]>(`${this.apiServerUrl}/animal`).toPromise();
  }

  public getAnimalsByFarmId(farmId:number): Observable<Animal[]>{
    return this.http.get<Animal[]>(`${this.apiServerUrl}/farm/${farmId}/animal`);
  }

  public addAnimal(animal:FormData): Observable<Animal>{
    return this.http.post<Animal>(`${this.apiServerUrl}/animal`,animal);
  }

  public addAnimalToFarmById(farmId:number,animal:FormData): Observable<Animal>{
    return this.http.post<Animal>(`${this.apiServerUrl}/farm/${farmId}/animal`,animal);
  }

  public updateAnimal(animalId:number, animal:Animal): Observable<Animal>{
    return this.http.put<Animal>(`${this.apiServerUrl}/animal/${animalId}`,animal);
  }

  public deleteAnimal(animalId:number): Observable<Animal>{
    return this.http.delete<Animal>(`${this.apiServerUrl}/animal/${animalId}`);
  }

  public deleteAnimalsFromFarmById(farmId:number): Observable<Animal>{
    return this.http.delete<Animal>(`${this.apiServerUrl}/farm/${farmId}/animal`);
  }
}
