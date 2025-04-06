import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {User} from "../../model/user/user";
import {Observable} from "rxjs";
import {Farm} from "../../model/farm/farm";
import {Animal} from "../../model/animal/animal";

@Injectable({
  providedIn: 'root'
})
export class UserService {


  private apiServerUrl=environment.apiBaseUrl+'/api';

  constructor(private http:HttpClient) { }

  public getUsers(): Observable<User[]>{
    return this.http.get<User[]>(`${this.apiServerUrl}/user`);
  }

  public getUserById(userId:number): Observable<User>{
    return this.http.get<User>(`${this.apiServerUrl}/user/${userId}`);
  }

  public getUserByEmail(email:string): Observable<User>{
    return this.http.get<User>(`${this.apiServerUrl}/user/email?email=${email}`);
  }

  public updateUser(id:number,user: User): Observable<any>{
    return this.http.put<any>(`${this.apiServerUrl}/user/${id}`,user);
  }

  public updateUserwithImg(id:number,imgId:number, user: User): Observable<User> {
    return this.http.put<User>(`${this.apiServerUrl}/user/${id}/image/${imgId}`, user);
  }

  public deleteUser(userId:number): Observable<User>{
    return this.http.delete<User>(`${this.apiServerUrl}/user/${userId}`);
  }
  public addImgToUser(user: FormData): Observable<User> {
    return this.http.post<User>(`${this.apiServerUrl}/user/image/`, user);
  }

}
