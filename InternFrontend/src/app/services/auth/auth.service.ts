import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {BehaviorSubject, map, mapTo, Observable, of, tap} from "rxjs";
import {Ettaba} from "../../model/ettaba/ettaba";
import {User} from "../../model/user/user";
import {Token} from "../../model/token/token";
import {catchError} from "rxjs/operators";

export interface Log{
  email: string;
  token?: string;
}
@Injectable({
  providedIn: 'root'
})
export class AuthService {


  private userSubject: BehaviorSubject<Log>;
  public user: Observable<Log>;

  private apiServerUrl=environment.apiBaseUrl+'/api/auth';

  private readonly JWT_TOKEN = 'JWT_TOKEN';
  private readonly REFRESH_TOKEN = 'REFRESH_TOKEN';
  public loggedUser: string;

  constructor(private http:HttpClient) {
    this.userSubject = new BehaviorSubject<Log>(
      JSON.parse(localStorage.getItem('currentUser'))
    );
    this.user = this.userSubject.asObservable();
  }

  forgetPassword(email:string):Observable<any>{
    return this.http.post(`${this.apiServerUrl}/forgot?email=${email}`, { email })
  }

  existUserByEmail(email : string) : Observable<Boolean>{
    return this.http.get<Boolean>(`${this.apiServerUrl}/existUserByEmail?email=${email}`);
  }

  getUserByEmail(email : string) : Observable<User>{
    return this.http.get<User>(`${this.apiServerUrl}/getUserByEmail?email=${email}`);
  }

  public existUserByToken(token : string) : Observable<Boolean>{
    return this.http.get<Boolean>(`${this.apiServerUrl}/existUserByToken?token=${token}`);
  }

  getUserByToken(token : string): Observable<User> {
    return this.http.get<User>(`${this.apiServerUrl}/getUserByToken?token=${token}`);
  }

  resetPassword(token : string,password:string): Observable<any> {
    return this.http.post<any>(`${this.apiServerUrl}/reset?token=${token}&password=${password}`, { token,password });
  }

  resetToken(token : string): Observable<any> {
    return this.http.post<any>(`${this.apiServerUrl}/resetToken?token=${token}`, { token });
  }


  public get userValue(): Log {
    return this.userSubject.value;
  }

  /*
  authenticate(user: {email:string, password:string}) {
    return this.http.post<any>(`${this.apiServerUrl}/authenticate`,user).pipe(
      map(
        userData => {
          sessionStorage.setItem('login',new Date().toDateString()+' '+new Date().toTimeString());
          sessionStorage.setItem('email',user.email);
          let tokenStr= 'Bearer '+userData.token;
          sessionStorage.setItem('token', tokenStr);
          return userData;
        }
      )

    );
  }


  refreshToken(matricule, password) {
    const headers= new HttpHeaders()
      .set('content-type', 'application/json')
      .set('Access-Control-Allow-Origin', '*')
      .set('isRefreshToken', 'true ');
    return this.httpClient.post<any>(`${environment.hostUrl}/refreshtoken`, {matricule, password },{'headers':headers}).pipe(
      map(
        userData => {
          sessionStorage.setItem('login',new Date().toDateString()+' '+new Date().toTimeString());
          let tokenStr= 'Bearer '+userData.token;
          sessionStorage.setItem('token', tokenStr);
          return userData;
        }
      )
    );
  }

  isUserLoggedIn():boolean {
    let user = sessionStorage.getItem('email')
    return !(user === null)
  }

  logOut() {
    sessionStorage.removeItem('login')
    sessionStorage.removeItem('email')
    sessionStorage.removeItem('token')
    this.removeTokens()
  }*/

  public register(user: User): Observable<any>{
    return this.http.post<any>(`${this.apiServerUrl}/register`,user);
  }

  /*
  public authenticate(user: User): Observable<any>{
    return this.http.post<any>(`${this.apiServerUrl}/authenticate`,user,{withCredentials:true});
  }

  public refreshToken(): Observable<any>{
    return this.http.post<any>(`${this.apiServerUrl}/refresh-token`,{},{withCredentials:true});
  }

  */
  ////////////////////////
  login(user: { email: string, password: string }): Observable<any> {
    return this.http.post<any>(`${this.apiServerUrl}/authenticate`, user,{withCredentials:true})
      .pipe(
        tap(tokens => this.doLoginUser(user.email, tokens)),
        mapTo(true),
        catchError(error => {
          alert(error.error);
          return of(false);
        }));
  }

  logout() {
    return this.http.post<any>(`${this.apiServerUrl}/logout`, {}).pipe(
      tap(() => this.doLogoutUser()),
      mapTo(true),
      catchError(error => {
        alert(error.error);
        return of(false);
      }));
  }

  isLoggedIn() {
    return !!this.getJwtToken();
  }

  doRefreshToken() {
    return this.http.post<any>(`/refresh-token`, {
      'refreshToken': this.getRefreshToken()
    }).pipe(tap((tokens: Token) => {
      this.storeJwtToken(tokens.access_token);
    }));
  }

  getJwtToken() {
    return sessionStorage.getItem(this.JWT_TOKEN);
  }

  private doLoginUser(username: string, tokens: Token) {
    this.loggedUser = username;
    let user: Log = {
      email: username,
      token: "token",
    };
    localStorage.setItem('currentUser', JSON.stringify(user));
    this.userSubject.next(user);
    this.storeTokens(tokens);
  }

  private doLogoutUser() {
    this.loggedUser = null;
    localStorage.removeItem('currentUser');
    this.userSubject.next(null);
    this.removeTokens();
  }

  private getRefreshToken() {
    return sessionStorage.getItem(this.REFRESH_TOKEN);
  }

  private storeJwtToken(jwt: string) {

    sessionStorage.setItem(this.JWT_TOKEN, jwt);
  }

  private storeTokens(tokens: Token) {

    sessionStorage.setItem(this.JWT_TOKEN, tokens.access_token);
    sessionStorage.setItem(this.REFRESH_TOKEN, tokens.refresh_token);
  }

  private removeTokens() {

    sessionStorage.setItem(this.JWT_TOKEN, null);
    sessionStorage.setItem(this.REFRESH_TOKEN, null);

    /*sessionStorage.removeItem(this.JWT_TOKEN);
    sessionStorage.removeItem(this.REFRESH_TOKEN);*/
  }
}
