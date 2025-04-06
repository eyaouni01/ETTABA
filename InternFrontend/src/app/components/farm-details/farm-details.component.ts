import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {Farm} from "../../model/farm/farm";
import {FarmService} from "../../services/farm/farm.service";
import {HttpErrorResponse} from "@angular/common/http";
import {EttabaService} from "../../services/ettaba/ettaba.service";
import {Ettaba} from "../../model/ettaba/ettaba";
import {EttabaComponent} from "../ettaba/ettaba.component";
import {map} from "rxjs";
import {ImageService} from "../../services/image/image.service";
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-farm-details',
  templateUrl: './farm-details.component.html',
  styleUrls: ['./farm-details.component.css']
})
export class FarmDetailsComponent implements OnInit {

  public farmId:number;
  public farm: Farm;
  public selectedFarm=0;
  public ettabaslength: number;

  constructor(private farmService:FarmService,
              private ettabaService: EttabaService,
              private route:ActivatedRoute,
              private router:Router, private imageService:ImageService,
              private titleService: Title) { }

  ngOnInit(): void {
    /*let id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.farmId=id;
    this.route.paramMap.subscribe((params:ParamMap)=>{
      this.farmId=parseInt(params.get('farm'));
    })*/

    this.farm=this.route.snapshot.data['farm']
    console.log(this.farm)
    this.titleService.setTitle('Farm Details | ETTABA');
    this.farmId=this.farm.id
    this.getFarmById(this.farm.id);
    this.getEttabasByFarmId(this.farm.id)
  }

  getFarmById(id:number){
    this.farmService.getFarmById(id)
      .pipe(
        map((f:Farm)=> this.imageService.createImagesForFarm(f))
      )
      .subscribe(
      (response:Farm)=>{
        this.farm=response;
      },
    (error:HttpErrorResponse)=>{
        alert(error.message)
    }
    )
  }


  getEttabasByFarmId(id:number){
    this.ettabaService.getEttabasByFarmId(id).subscribe(
      (response:Ettaba[])=>{
        this.ettabaslength=response.length;
      },
      (error:HttpErrorResponse)=>{
        alert(error.message)
      }
    )
  }

  previousFarm() {
    let previousId = this.farmId-1;
    this.router.navigate(['/farms',previousId])
  }

  nextFarm() {
    let nextId = this.farmId+1;
    this.router.navigate(['/farms',nextId])
  }

  goBack() {
    this.router.navigate(['../'],{relativeTo: this.route})
  }

  goToEttabas() {
    this.router.navigate(['ettabas'],{relativeTo:this.route})
  }

  goToEvents() {
    this.router.navigate(['events'],{relativeTo:this.route})
  }
  goToAnimals() {
    this.router.navigate(['animals'],{relativeTo:this.route})
  }

  /*onOutletLoaded($event: EttabaComponent) {
    console.log(this.farmId)
    $event.farmId=this.farmId
  }*/

  changeSelection(i: number) {
    this.selectedFarm=i;
  }

}
