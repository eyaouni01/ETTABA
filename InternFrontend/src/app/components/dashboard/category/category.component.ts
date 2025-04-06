import { Component, OnInit } from '@angular/core';
import {Chart} from "angular-highcharts";
import {ProductService} from "../../../services/product/product.service";
import {Months} from "../monthly/monthly.component";
import {Product} from "../../../model/product/product";

export class Category {
  SEED:number=0
  INPROGRESS:number=0
  READY : number=0
}

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {


  public chart;

  public listCategory : Category = new Category();

  constructor(private productService:ProductService) { }

  ngOnInit(): void {
    this.proceed().then(() => {

      this.chart = new Chart({
        chart: {
          type: 'pie',
          height: 325
        },
        title: {
          text: 'Progrés des produits par Categorie'
        },
        xAxis: {
          categories: [
            'Seed',
            'In progress',
            'Ready',
          ]
        },
        yAxis: {
          title: {
            text: 'Progrés en %'
          }
        },
        series: [
          {
            type: 'pie',
            data: [
              {
                name: 'Seed',
                y: this.listCategory.SEED,
                color: '#9dc08a',
              },
              {
                name: 'In progress',
                y: this.listCategory.INPROGRESS,
                color: '#609966',
              },
              {
                name: 'Ready',
                y: this.listCategory.READY,
                color: '#3f513b',
              }
            ]
          }
        ],
        credits: {
          enabled: false
        }
      })
    })
  }

  async proceed(){
    await this.getProducts();
    console.log("finished");
  }

  async getProducts(){
    let response = await this.productService.getProductsPromise();
    if(response){
      this.listCategory.SEED = this.getProductByCategory(response,'SEED');
      this.listCategory.INPROGRESS = this.getProductByCategory(response,'INPROGRESS');
      this.listCategory.READY = this.getProductByCategory(response,'READY');
    }
  }

  private getProductByCategory(evt: Product[],opt:string):number {
    let list:Product[]=[]
    evt.forEach(value => {
      if (value.etat==opt){
        list.push(value);
      }
    });
    return list.length ;
  }
}
