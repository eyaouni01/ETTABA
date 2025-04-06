import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import * as L from 'leaflet';
import {Farm} from "../../model/farm/farm";

@Injectable({
  providedIn: 'root'
})
export class MarkerService {
  private apiServerUrl=environment.apiBaseUrl+'/api';

  constructor(private http:HttpClient) { }

  makeCapitalMarkers(map: L.map): void {
    this.http.get<Farm[]>(`${this.apiServerUrl}/farm`)
      .subscribe((res: Farm[]) => {
        res.forEach(elt=>{
          const lon = elt.longitude
          const lat = elt.latitude
          const marker = L.marker([lat, lon]);

          marker.bindPopup(this.makeCapitalPopup(elt))
          marker.addTo(map);
        })
      });
  }

  makeCapitalPopup(data: Farm): string {
    return `` +
      `<div>Nom Ferme: ${ data.name }</div>` +
      `<div>Region: ${ data.address.region }</div>` +
      `<div>Gouvernorat: ${ data.address.state }</div>` +
      `<div>Cité: ${ data.address.city }</div>`
  }

}
