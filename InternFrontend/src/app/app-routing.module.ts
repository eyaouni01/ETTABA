import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {FarmComponent} from "./components/farm/farm.component";
import {EttabaComponent} from "./components/ettaba/ettaba.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";
import {HomeComponent} from "./components/home/home.component";
import {FarmDetailsComponent} from "./components/farm-details/farm-details.component";
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {AuthGuard} from "./shared/auth.guard";
import {ProfileComponent} from "./components/profile/profile.component";
import {EventComponent} from "./components/event/event.component";
import {AnimalComponent} from "./components/animal/animal.component";
import {ProductComponent} from "./components/product/product.component";
import {UserComponent} from "./components/user/user.component";
import {EventDetailsComponent} from "./components/event-details/event-details.component";
import {FarmResolverService} from "./services/resolvers/farm-resolver.service";
import {UserResolverService} from "./services/resolvers/user-resolver.service";
import {EttabaDetailsComponent} from "./components/ettaba-details/ettaba-details.component";
import {EttabaResolverService} from "./services/resolvers/ettaba-resolver.service";
import {MapComponent} from "./components/map/map.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {RecordsComponent} from "./components/dashboard/records/records.component";
import {MonthlyComponent} from "./components/dashboard/monthly/monthly.component";
import {CategoryComponent} from "./components/dashboard/category/category.component";
import {ResetComponent} from "./components/reset/reset.component";
import {SidebarComponent} from "./components/home/sidebar/sidebar.component";

const routes: Routes = [
  {path: '',redirectTo: '/login',pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'reset', component: ResetComponent},
  {path: 'home', component: HomeComponent,resolve:{user:UserResolverService},
    children: [

      {path: '',redirectTo: '/home/dashboard',pathMatch: 'full'},
      {path: 'profile',component: ProfileComponent,resolve:{user:UserResolverService}},
      {path: 'dashboard',component: DashboardComponent},
      {path: 'users',component: UserComponent},
      {path: 'farms',component: FarmComponent},
      {path: 'farms/:id',component: FarmDetailsComponent,resolve:{farm:FarmResolverService},
        children:[
          {path: 'ettabas',component: EttabaComponent},
          {path: 'events',component: EventComponent},
          {path: 'animals',component: AnimalComponent},
        ]},

      {path: 'ettabas',component: EttabaComponent},
      {path: 'ettabas/:id',component: EttabaDetailsComponent,resolve:{ettaba:EttabaResolverService},
        children:[
          {path: 'products',component: ProductComponent},
        ]},
      {path: 'maps',component: MapComponent},
      {path: 'events',component: EventComponent},
      {path: 'animals',component: AnimalComponent},
      {path: 'products',component: ProductComponent},
    ], canActivate:[AuthGuard]},
  {path: "**",component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { enableTracing: false, scrollPositionRestoration: 'enabled' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [
  HomeComponent,
  FarmComponent,
  FarmDetailsComponent,
  EttabaComponent,
  PageNotFoundComponent,
  LoginComponent,
  RegisterComponent,
  ProfileComponent,
  EventComponent,
  AnimalComponent,
  ProductComponent,
  UserComponent,
  EventDetailsComponent,
  EttabaDetailsComponent,
  MapComponent,
  ResetComponent,
  DashboardComponent,
  RecordsComponent,
  MonthlyComponent,
  CategoryComponent,
  SidebarComponent,
]
