import { Component, OnInit } from '@angular/core';
import {Title} from "@angular/platform-browser";
import {FarmService} from "../../services/farm/farm.service";
import {EttabaService} from "../../services/ettaba/ettaba.service";
import {EventService} from "../../services/event/event.service";
import {ProductService} from "../../services/product/product.service";
import {AnimalService} from "../../services/animal/animal.service";
import {map} from "rxjs";
import {Farm} from "../../model/farm/farm";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {


  public farms:Farm[]=[];

  constructor(private titleService: Title,
              private farmService:FarmService,
              private ettabaService: EttabaService,
              private eventService: EventService,
              private productService: ProductService,
              private animalService: AnimalService,) { }

  ngOnInit(): void {
    this.titleService.setTitle('Dashboard | ETTABA');
    this.getFarms()
  }

  public getFarms():void{
    this.farmService.getFarms()
      .subscribe(
        (response: Farm[])=>{
          this.farms=response;
        },
        (error: HttpErrorResponse)=>{
          alert(error.message);
        }
      )
  }
}
