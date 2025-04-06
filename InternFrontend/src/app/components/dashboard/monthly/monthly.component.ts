import { Component, OnInit } from '@angular/core';
import {Chart} from "angular-highcharts";
import {FarmService} from "../../../services/farm/farm.service";
import {UserService} from "../../../services/user/user.service";
import {EttabaService} from "../../../services/ettaba/ettaba.service";
import {EventService} from "../../../services/event/event.service";
import {ProductService} from "../../../services/product/product.service";
import {AnimalService} from "../../../services/animal/animal.service";
import {Event} from "../../../model/event/event";
import {Farm} from "../../../model/farm/farm";
import {Ettaba} from "../../../model/ettaba/ettaba";
import {Product} from "../../../model/product/product";
import {Animal} from "../../../model/animal/animal";
import {User} from "../../../model/user/user";
import {delay} from "rxjs";

export class Months {
  Janvier:number=0
  Fevrier:number=0
  Mars : number=0
  Avril : number=0
  Mai : number=0
  Juin : number=0
  Juillet : number=0
  Aout : number=0
  Septembre : number=0
  Octobre : number=0
  Novembre : number=0
  Decembre : number=0
}
@Component({
  selector: 'app-monthly',
  templateUrl: './monthly.component.html',
  styleUrls: ['./monthly.component.css']
})
export class MonthlyComponent implements OnInit {


  public farms:Farm[]=[];
  public ettabas: Ettaba[]=[];
  public products: Product[]=[];
  public events: Event[]=[];
  public animals: Animal[]=[];
  public users: User[]=[];


  public listEventMonths : Months = new Months();

  public listAnimalMonths : Months = new Months();

  public listProductMonths : Months = new Months();

  public chart


  constructor(private eventService: EventService,
              private productService: ProductService,
              private animalService: AnimalService,) { }

  ngOnInit(): void {
    this.proceed().then(() => {
      this.chart = new Chart({
        chart: {
          type: 'line',
          height: 325
        },
        title: {
          text: "Ventes par Mois pour l'année 2023"
        },
        xAxis: {
          categories: [
            'Jan',
            'Fev',
            'Mar',
            'Avr',
            'Mai',
            'Jun',
            'Jul',
            'Aug',
            'Sep',
            'Oct',
            'Nov',
            'Dec'
          ]
        },
        yAxis: {
          title: {
            text: 'Revenus en DT'
          }
        },
        series: [
          {
            name: "Produit",
            type: "line",
            color: '#3f513b',
            data: [this.listProductMonths.Janvier, this.listProductMonths.Fevrier, this.listProductMonths.Mars,
              this.listProductMonths.Avril, this.listProductMonths.Mai, this.listProductMonths.Juin, this.listProductMonths.Juillet,
              this.listProductMonths.Aout, this.listProductMonths.Septembre, this.listProductMonths.Octobre, this.listProductMonths.Novembre,
              this.listProductMonths.Decembre]
          },
          {
            name: 'Evénement',
            type: 'line',
            color: '#7e0505',
            data: [this.listEventMonths.Janvier, this.listEventMonths.Fevrier, this.listEventMonths.Mars,
              this.listEventMonths.Avril, this.listEventMonths.Mai, this.listEventMonths.Juin, this.listEventMonths.Juillet,
              this.listEventMonths.Aout, this.listEventMonths.Septembre, this.listEventMonths.Octobre, this.listEventMonths.Novembre,
              this.listEventMonths.Decembre]
          },
          {
            name: 'Animal',
            type: 'line',
            color: '#ed9e20',
            data: [this.listAnimalMonths.Janvier, this.listAnimalMonths.Fevrier, this.listAnimalMonths.Mars,
              this.listAnimalMonths.Avril, this.listAnimalMonths.Mai, this.listAnimalMonths.Juin, this.listAnimalMonths.Juillet,
              this.listAnimalMonths.Aout, this.listAnimalMonths.Septembre, this.listAnimalMonths.Octobre,
              this.listAnimalMonths.Novembre, this.listAnimalMonths.Decembre]
          },
        ],
        credits: {
          enabled: false
        }
      })

    })

    console.log(this.listEventMonths.Aout)
  }


  async proceed(){
    await this.getEvents();
    await this.getAnimals();
    await this.getProducts();
    console.log("finished");
  }

  async getEvents(){
    let response = await this.eventService.getEventsPromise();
    if(response){
      this.listEventMonths.Janvier = this.getOthersYearAndMonth(response,0);
      this.listEventMonths.Fevrier = this.getOthersYearAndMonth(response,1);
      this.listEventMonths.Mars = this.getOthersYearAndMonth(response,2);
      this.listEventMonths.Avril = this.getOthersYearAndMonth(response,3);
      this.listEventMonths.Mai = this.getOthersYearAndMonth(response,4);
      this.listEventMonths.Juin = this.getOthersYearAndMonth(response,5);
      this.listEventMonths.Juillet = this.getOthersYearAndMonth(response,6);
      this.listEventMonths.Aout = this.getOthersYearAndMonth(response,7);
      this.listEventMonths.Septembre = this.getOthersYearAndMonth(response,8);
      this.listEventMonths.Octobre = this.getOthersYearAndMonth(response,9);
      this.listEventMonths.Novembre = this.getOthersYearAndMonth(response,10);
      this.listEventMonths.Decembre = this.getOthersYearAndMonth(response,11);}
  }

  async getAnimals(){
    let response = await this.animalService.getAnimalsPromise();
    if(response){
      this.listAnimalMonths.Janvier = this.getOthersYearAndMonth(response,0);
      this.listAnimalMonths.Fevrier = this.getOthersYearAndMonth(response,1);
      this.listAnimalMonths.Mars = this.getOthersYearAndMonth(response,2);
      this.listAnimalMonths.Avril = this.getOthersYearAndMonth(response,3);
      this.listAnimalMonths.Mai = this.getOthersYearAndMonth(response,4);
      this.listAnimalMonths.Juin = this.getOthersYearAndMonth(response,5);
      this.listAnimalMonths.Juillet = this.getOthersYearAndMonth(response,6);
      this.listAnimalMonths.Aout = this.getOthersYearAndMonth(response,7);
      this.listAnimalMonths.Septembre = this.getOthersYearAndMonth(response,8);
      this.listAnimalMonths.Octobre = this.getOthersYearAndMonth(response,9);
      this.listAnimalMonths.Novembre = this.getOthersYearAndMonth(response,10);
      this.listAnimalMonths.Decembre = this.getOthersYearAndMonth(response,11);}
  }

  async getProducts(){
    let response = await this.productService.getProductsPromise();
    if(response){
      this.listProductMonths.Janvier = this.getProductYearAndMonth(response,0);
      this.listProductMonths.Fevrier = this.getProductYearAndMonth(response,1);
      this.listProductMonths.Mars = this.getProductYearAndMonth(response,2);
      this.listProductMonths.Avril = this.getProductYearAndMonth(response,3);
      this.listProductMonths.Mai = this.getProductYearAndMonth(response,4);
      this.listProductMonths.Juin = this.getProductYearAndMonth(response,5);
      this.listProductMonths.Juillet = this.getProductYearAndMonth(response,6);
      this.listProductMonths.Aout = this.getProductYearAndMonth(response,7);
      this.listProductMonths.Septembre = this.getProductYearAndMonth(response,8);
      this.listProductMonths.Octobre = this.getProductYearAndMonth(response,9);
      this.listProductMonths.Novembre = this.getProductYearAndMonth(response,10);
      this.listProductMonths.Decembre = this.getProductYearAndMonth(response,11);}
  }

  public getOthersYearAndMonth(evt:any[],month:number):number{
    //let list : Event[] = [] ;

    let sum:number=0;
    evt.forEach(value => {
      if ((new Date(value.boughtDate)).getFullYear() == (new Date()).getFullYear() &&
        (new Date(value.boughtDate)).getMonth() == month){
        //list.push(value);
        sum=sum+value.price
        console.log(value.boughtDate+':'+sum)
      }
    });
    return sum ;
  }

  public getProductYearAndMonth(evt:Product[],month:number):number{

    let sum:number=0;
    evt.forEach(value => {
      if ((new Date(value.boughtDate)).getFullYear() == (new Date()).getFullYear() &&
        (new Date(value.boughtDate)).getMonth() == month){
        //list.push(value);
        sum=sum+value.boughtPrice
        console.log(value.boughtDate+':'+sum)
      }
    });
    return sum ;
  }

}
