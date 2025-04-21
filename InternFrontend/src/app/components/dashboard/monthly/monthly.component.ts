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
import { DashboardStatsHelper } from "../../../services/DashboardPurFabrication/dashboard-stats.helper";

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
  chart: Chart;

  constructor(
    private eventService: EventService,
    private productService: ProductService,
    private animalService: AnimalService,
    private statsHelper: DashboardStatsHelper
  ) {}


async ngOnInit() {
  const [products, events, animals] = await Promise.all([
    this.productService.getProductsPromise(),
    this.eventService.getEventsPromise(),
    this.animalService.getAnimalsPromise()
  ]);

   this.createChart(
     // Produits: utilise boughtPrice et boughtDate
     this.statsHelper.calculateMonthlySums(products, 'boughtPrice', 'bought'),

     // Événements: utilise price et boughtDate
     this.statsHelper.calculateMonthlySums(events, 'price', 'bought'),

     // Animaux: utilise price et boughtDate
     this.statsHelper.calculateMonthlySums(animals, 'price', 'bought')
   );
}

  private createChart(productData: number[], eventData: number[], animalData: number[]) {
    this.chart = new Chart({
      chart: { type: 'line', height: 325 },
      title: { text: `Ventes par Mois pour l'année ${new Date().getFullYear()}` },
      xAxis: {
        categories: ['Jan', 'Fev', 'Mar', 'Avr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
      },
      yAxis: { title: { text: 'Revenus en DT' } },
      series: [
        {
          name: "Produit",
          type: "line",
          color: '#3f513b',
          data: productData
        },
        {
          name: 'Evénement',
          type: 'line',
          color: '#7e0505',
          data: eventData
        },
        {
          name: 'Animal',
          type: 'line',
          color: '#ed9e20',
          data: animalData
        }
      ],
      credits: { enabled: false }
    });
  }
}
