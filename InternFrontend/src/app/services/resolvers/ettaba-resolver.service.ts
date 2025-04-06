import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Ettaba} from "../../model/ettaba/ettaba";
import {map, Observable, of} from "rxjs";
import {FarmService} from "../farm/farm.service";
import {ImageService} from "../image/image.service";
import {EttabaService} from "../ettaba/ettaba.service";

@Injectable({
  providedIn: 'root'
})
export class EttabaResolverService implements Resolve<Ettaba>{

  constructor(private ettabaservice:EttabaService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Ettaba> {
    const id=parseInt(route.paramMap.get('id'));
    console.log('from resolver'+id)
    if(id){
      return this.ettabaservice.getEttabaById(id);
    }
    else {
      return of(this.getEttabas());
    }
  }

  getEttabas() {
    return {
      id: null,
      plantationDate:null,
      readyDate:null,
      height:0,
      width:0,
      price:0,
      creationDate:null,
      boughtDate: null,
    }
  }
}
