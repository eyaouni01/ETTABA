import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {User} from "../../model/user/user";
import {map, Observable} from "rxjs";
import {ImageService} from "../image/image.service";
import {DomSanitizer} from "@angular/platform-browser";
import {UserService} from "../user/user.service";
import {AuthService, Log} from "../auth/auth.service";

@Injectable({
  providedIn: 'root'
})
export class UserResolverService implements Resolve<User>{
  currentUser: Log;

  constructor(private authService:AuthService,
              private imageService:ImageService,
              private userService:UserService,) {
    this.authService.user.subscribe(user => this.currentUser = user);

  }

  resolve(route: ActivatedRouteSnapshot,
          state: RouterStateSnapshot): Observable<User> {
    return this.userService.getUserByEmail(this.currentUser.email)
      .pipe(
        map((e:User)=>this.imageService.createImageForUser(e))
      )
  }
}
