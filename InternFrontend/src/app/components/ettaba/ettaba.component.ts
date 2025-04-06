import { Component, OnInit } from '@angular/core';
import {Ettaba} from "../../model/ettaba/ettaba";
import {EttabaService} from "../../services/ettaba/ettaba.service";
import {HttpErrorResponse} from "@angular/common/http";
import {FormBuilder, FormGroup, NgForm, Validators} from "@angular/forms";
import {DomSanitizer, Title} from "@angular/platform-browser";
import {Farm} from "../../model/farm/farm";
import {Event} from "../../model/event/event";
import {MatDialog} from "@angular/material/dialog";
import {ActivatedRoute, Router} from "@angular/router";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-ettaba',
  templateUrl: './ettaba.component.html',
  styleUrls: ['./ettaba.component.css']
})
export class EttabaComponent implements OnInit {

  length = 50;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [5, 10, 25];

  showPageSizeOptions = true;
  showFirstLastButtons = true;

  pageEvent: PageEvent;

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
  }

  public ettabas: Ettaba[]=[];
  public count: number;

  public ettaba:Ettaba={
    id:null,
    price: 0,
    plantationDate: null,
    readyDate: null,
    height: 0,
    width: 0,

    boughtDate:new Date(),
    creationDate:new Date()
  }

  public farmId:number;

  farm:Farm

  displayedColumns: string[] = ['no','price', 'plantationDate', 'readyDate','heightwidth','details','edit','delete'];
  dataSource:Ettaba[]=[];

  formGroupEttaba: FormGroup
  editing: boolean=false;
  deletingAll: boolean=false;

  constructor(private ettabaService: EttabaService,
              private sanitizer:DomSanitizer,
              private fb:FormBuilder,

              private router:Router,
              private route:ActivatedRoute,
              private titleService: Title) { }

  ngOnInit(): void {

    this.titleService.setTitle('Ettabas | ETTABA');

    this.route.parent.data
      .subscribe((data) => {
          if(data['farm'] !== undefined) {
            this.farm = data['farm'];
            this.farmId = this.farm.id
            this.getEttabasByFarmId(this.farmId)
          }
          else {
            this.getEttabas();
          }
        },
        error => {
          alert(error)
        });

    this.initForm()
  }

  initForm(){
    this.formGroupEttaba=this.fb.group(
      {
        id: [null],
        price: [0, [Validators.required,]],

        plantationDate: [null, [Validators.required]],

        readyDate: [null, [Validators.required]],

        height: [0, [Validators.required]],

        width: [0, [Validators.required]],

      })
  }

  public getEttabas():void{
    this.ettabaService.getEttabas().subscribe(
      (response: Ettaba[])=>{
        this.ettabas=response;

        this.dataSource=response
      },
      (error: HttpErrorResponse)=>{
        alert(error.message);
      }
    )
  }

  isFarmLinked():boolean{
    return this.farmId===undefined
  }

  onSelect(id: number) {
    //this.router.navigate(['/farms',farm.id]);
    this.router.navigate([id],{relativeTo:this.route});

  }

  addEttabaToFarmById(id:number){

    document.getElementById('close-add-form').click();
    this.ettabaService.addEttabaToFarmById(id, this.formGroupEttaba.value).subscribe(
      (response:Ettaba)=>{
        console.log(response)
        this.getEttabasByFarmId(id)
      },
      (error:HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  getEttabasByFarmId(id:number){
    this.ettabaService.getEttabasByFarmId(id).subscribe(
      (response:Ettaba[])=>{
        this.ettabas=response;

        this.dataSource=response
      },
      (error:HttpErrorResponse)=>{
        alert(error.message)
      }
    )
  }

  public onAddEttaba():void{
    document.getElementById('close-add-form').click();
    this.ettabaService.addEttaba(this.formGroupEttaba.value).subscribe(
      (response:Ettaba)=>{
        this.getEttabasByFarmId(this.farmId);
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public onUpdateEttaba():void{
    document.getElementById('close-add-form').click();
    this.ettabaService.updateEttaba(this.formGroupEttaba.get('id').value,this.formGroupEttaba.value).subscribe(
      (response:Ettaba)=>{
        this.getEttabasByFarmId(this.farmId);
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public onDeleteEttaba():void{
    document.getElementById('close-delete-form').click();
    this.ettabaService.deleteEttaba(this.formGroupEttaba.get('id').value).subscribe(
      (response:Ettaba)=>{
        this.getEttabasByFarmId(this.formGroupEttaba.get('id').value);
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public onDeleteAllEttabasFromFarm(id:number):void{
    document.getElementById('close-delete-form').click();
    this.ettabaService.deleteEttabasFromFarmById(id).subscribe(
      (response:Ettaba)=>{
        console.log(response)
        this.getEttabasByFarmId(id);
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }
  public onOpenModal(ettaba: Ettaba, mode:string, count:number):void{
    const container=document.getElementById('main-container');
    const button=document.createElement('button');
    button.type='button';
    button.style.display='none';
    button.setAttribute('data-bs-toggle','modal');
    if(mode==='add'){
      this.editing=false
      this.formGroupEttaba.reset()
      button.setAttribute('data-bs-target','#addModal');
    }
    if(mode==='edit'){
      this.count=count;
      this.editing=true
      this.formGroupEttaba.setValue(ettaba)
      button.setAttribute('data-bs-target','#addModal');
    }
    if(mode==='delete'){
      this.count=count;
      this.deletingAll=false

      this.formGroupEttaba.setValue(ettaba)
      button.setAttribute('data-bs-target','#deleteModal');
    }
    if(mode==='deleteAll'){
      this.deletingAll=true
      this.formGroupEttaba.reset()
      button.setAttribute('data-bs-target','#deleteModal');
    }
    // @ts-ignore
    container.appendChild(button);
    button.click();
  }

  public searchEttabas(value: string) {
    const results: Ettaba[]=[];
    for(const ettaba of this.ettabas){
      if(ettaba.price.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || ettaba.height.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || ettaba.width.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || ettaba.readyDate.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || ettaba.plantationDate.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1){
        results.push(ettaba);
      }
    }
    this.ettabas=results;
    if(results.length === 0 || !value){
      if(isNaN(this.farmId)) this.getEttabas();
      else this.getEttabasByFarmId(this.farmId)
    }
  }

}
