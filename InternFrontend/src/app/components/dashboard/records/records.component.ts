import { Component, OnInit } from '@angular/core';
import {FarmService} from "../../../services/farm/farm.service";
import {EttabaService} from "../../../services/ettaba/ettaba.service";
import {EventService} from "../../../services/event/event.service";
import {ProductService} from "../../../services/product/product.service";
import {AnimalService} from "../../../services/animal/animal.service";
import {Farm} from "../../../model/farm/farm";
import {HttpErrorResponse} from "@angular/common/http";
import {Ettaba} from "../../../model/ettaba/ettaba";
import {map} from "rxjs";
import {Product} from "../../../model/product/product";
import {Animal} from "../../../model/animal/animal";
import {Event} from "../../../model/event/event";
import {User} from "../../../model/user/user";
import {UserService} from "../../../services/user/user.service";

@Component({
  selector: 'app-records',
  templateUrl: './records.component.html',
  styleUrls: ['./records.component.css']
})
export class RecordsComponent implements OnInit {


  public farms:Farm[]=[];
  public ettabas: Ettaba[]=[];
  public products: Product[]=[];
  public events: Event[]=[];
  public animals: Animal[]=[];
  public users: User[]=[];

  revenues: number=0;

  constructor(private farmService:FarmService,
              private userService: UserService,
              private ettabaService: EttabaService,
              private eventService: EventService,
              private productService: ProductService,
              private animalService: AnimalService,) { }

  ngOnInit(): void {
    this.getFarms()
    this.getEttabas()
    this.getProducts()
    this.getAnimals()
    this.getEvents()
    this.getUsers()
  }

  public getUsers():void{
    this.userService.getUsers()
      .subscribe(
        (response: User[])=>{
          this.users=response;
        },
        (error: HttpErrorResponse)=>{
          alert(error.message);
        }
      )
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

  public getEttabas():void{
    this.ettabaService.getEttabas().subscribe(
      (response: Ettaba[])=>{
        this.ettabas=response;
        response.forEach(elt=>{
          this.revenues+=elt.price
        })
      },
      (error: HttpErrorResponse)=>{
        alert(error.message);
      }
    )
  }

  public getProducts():void{
    this.productService.getProducts()
      .subscribe(
        (response: Product[])=>{
          this.products=response;
          response.forEach(elt=>{
            this.revenues+=elt.boughtPrice
          })
        },
        (error: HttpErrorResponse)=>{
          alert(error.message);
        }
      )
  }

  public getAnimals():void{
    this.animalService.getAnimals()
      .subscribe(
        (response: Animal[])=>{
          this.animals=response;
          response.forEach(elt=>{
            this.revenues+=elt.price
          })
        },
        (error: HttpErrorResponse)=>{
          alert(error.message);
        }
      )
  }

  public getEvents():void{
    this.eventService.getEvents()
      .subscribe(
        (response: Event[])=>{
          this.events=response;
          response.forEach(elt=>{
            this.revenues+=elt.price
          })
        },
        (error: HttpErrorResponse)=>{
          alert(error.message);
        }
      )
  }
}
