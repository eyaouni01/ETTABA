import { Component, OnInit } from '@angular/core';
import {Farm} from "../../model/farm/farm";
import {FarmService} from "../../services/farm/farm.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ImageService} from "../../services/image/image.service";
import {Title} from "@angular/platform-browser";
import {EttabaService} from "../../services/ettaba/ettaba.service";
import {Ettaba} from "../../model/ettaba/ettaba";
import {map} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-ettaba-details',
  templateUrl: './ettaba-details.component.html',
  styleUrls: ['./ettaba-details.component.css']
})
export class EttabaDetailsComponent implements OnInit {


  public ettaba: Ettaba;

  constructor(private ettabaService:EttabaService,
              private route:ActivatedRoute,
              private router:Router,
              private titleService: Title) { }

  ngOnInit(): void {
    this.titleService.setTitle('Ettaba Details | ETTABA');
    this.ettaba=this.route.snapshot.data['ettaba']
    this.getFarmById(this.ettaba.id);
  }

  getFarmById(id:number){
    this.ettabaService.getEttabaById(id)
      .subscribe(
        (response:Ettaba)=>{
          this.ettaba=response;
        },
        (error:HttpErrorResponse)=>{
          alert(error.message)
        }
      )
  }

  goBack() {
    this.router.navigate(['../'],{relativeTo: this.route})
  }
}
