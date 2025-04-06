import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Farm} from "../../model/farm/farm";
import {FarmService} from "../farm/farm.service";
import {ImageService} from "../image/image.service";
import {map, Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FarmResolverService implements Resolve<Farm>{

  constructor(private farmService:FarmService,
              private imageProcessing:ImageService) { }

  resolve(route: ActivatedRouteSnapshot,
          state: RouterStateSnapshot): Observable<Farm> {
    const id=parseInt(route.paramMap.get('id'));
    console.log('from resolver'+id)
    if(id){
      return this.farmService.getFarmById(id).pipe(
        map(f=>this.imageProcessing.createImagesForFarm(f))
      );
    }
    else {
      return of(this.getFarms());
    }
  }

  getFarms() {
    return {
      id: null,
      name: "",
      longitude: 0,
      latitude: 0,
      numberEttabas: 0,
      numberAvailableEttabas: 0,
      location: null,
      farmImages: [],
      description: "",
      creationDate:null,
      address: {
        id:0,
        street:'',
        state:'',
        postalCode:0,
        houseNumber:'',
        city:'',
        region:''
      },
    }
  }

}
