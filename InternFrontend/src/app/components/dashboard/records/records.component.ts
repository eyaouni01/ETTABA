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

  public ettabarevenue:Number=0;
  public productrevenue :Number=0;
  public eventrevenue:Number=0;
  public totalrevenue:Number=0;
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
    this.getEttabasRevenue()
    this.getEventRevenue()
    this.getProductRevenue()
  
  }


  public calculerTotal(): void {
    // Convertir les valeurs de Number à number pour l'addition
    this.totalrevenue = Number(this.ettabarevenue) + Number(this.eventrevenue) + Number(this.productrevenue);
    console.log("revuenue totlallllllll",this.totalrevenue)
   
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

  public getEttabasRevenue():void{
    this.ettabaService.getEttabasRevenue().subscribe(
      (response: Number)=>{
        this.ettabarevenue=response;
        
      },
      (error: HttpErrorResponse)=>{
        alert(error.message);
      }
    )
  }

  public getEventRevenue():void{
    this.eventService.getEventRevenue().subscribe(
      (response: Number)=>{
        this.eventrevenue=response;
        this.totalrevenue = Number(this.ettabarevenue) + Number(this.eventrevenue) + Number(this.productrevenue);
      },
      (error: HttpErrorResponse)=>{
        alert(error.message);
      }
    )
  }
  public getProductRevenue():void{
    this.productService.getProductRevenue().subscribe(
      (response: Number)=>{
        this.productrevenue=response;
        
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
