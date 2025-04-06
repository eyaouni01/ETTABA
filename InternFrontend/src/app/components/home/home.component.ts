import { Component, OnInit } from '@angular/core';
import {AuthService, Log} from "../../services/auth/auth.service";
import {
  ActivatedRoute, NavigationCancel,
  NavigationEnd,
  NavigationError,
  NavigationStart,
  Router
} from "@angular/router";
import {map, Observable, startWith} from "rxjs";
import {FormControl} from "@angular/forms";
import {Title} from "@angular/platform-browser";
import {User} from "../../model/user/user";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  streets: string[] = ['Dashboard','Users','Farms','Ettabas','Events','Animals','Products','Profile'];
  filteredStreets: Observable<string[]>;
  control = new FormControl('');
  currentUser: Log;
  loading = false;

  user:User;

  sidebarExpanded = true;

  constructor(private authService:AuthService,
              private router:Router,
              private activatedRoute: ActivatedRoute) {

    this.authService.user.subscribe(user => this.currentUser = user);

    this.router.events.subscribe(ev => {
      if (ev instanceof NavigationStart) {
        this.loading = true;
      }
      if (
        ev instanceof NavigationEnd ||
        ev instanceof NavigationCancel ||
        ev instanceof NavigationError
      ) {
        this.loading = false;
      }
    });
  }

  ngOnInit(): void {

    this.activatedRoute.data.subscribe((response: any) => {
      this.user = response.user;

    });

    this.filteredStreets = this.control.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value || '')),
    );
  }

  private _filter(value: string): string[] {
    const filterValue = HomeComponent._normalizeValue(value);
    return this.streets.filter(street => HomeComponent._normalizeValue(street).includes(filterValue));
  }

  private static _normalizeValue(value: string): string {
    return value.toLowerCase().replace(/\s/g, '');
  }

  logout(){
    this.authService.logout()
    this.router.navigate(['login'])
  }
}
