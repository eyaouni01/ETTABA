import { Component, OnInit } from '@angular/core';
import {Event} from "../../model/event/event";
import {EventService} from "../../services/event/event.service";
import {HttpErrorResponse} from "@angular/common/http";
import {FormBuilder, FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {FileHandle} from "../../model/file/file";
import {DomSanitizer, Title} from "@angular/platform-browser";
import {MatDialog} from "@angular/material/dialog";
import {ImageService} from "../../services/image/image.service";
import {map} from "rxjs";
import {EventDetailsComponent} from "../event-details/event-details.component";
import {Farm} from "../../model/farm/farm";
import {ActivatedRoute} from "@angular/router";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {

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


  public events: Event[]=[];
  public count: number;
  public event:Event={
    id:null,
    name:"",
    price: 0,
    description: "",
    startDate: null,
    endDate: null,
    numberTickets: 0,
    numberAvailableTickets: 0,
    eventImages:[],
    boughtDate:new Date(),
    creationDate:new Date()
  }

  public farmId:number;

  farm:Farm

  displayedColumns: string[] = ['no','name','price', 'startDate', 'endDate','numberTickets','details','edit','delete'];
  dataSource:Event[]=[];

  formGroupEvent: FormGroup
  editing: boolean=false;
  deletingAll: boolean=false;

  constructor(private eventService: EventService,
              private sanitizer:DomSanitizer,
              private dialog:MatDialog,
              private fb:FormBuilder,
              private route:ActivatedRoute,
              private imageProcessService:ImageService,
              private titleService: Title) { }

  ngOnInit(): void {

    this.titleService.setTitle('Events | ETTABA');

    //this.farm=this.route.snapshot.data['farm']
    this.route.parent.data
      .subscribe((data) => {
        if(data['farm'] !== undefined) {
          this.farm = data['farm'];
          this.farmId = this.farm.id
          this.getEventsByFarmId(this.farmId)
        }
        else {
          this.getEvents();

        }
      },
        error => {
        alert(error)
        });
    this.initForm()
  }

  initForm(){
    this.formGroupEvent=this.fb.group(
      {
        id: [null],
        name: ['', [Validators.required,Validators.minLength(3)]],
        price: [0, [Validators.required,]],
        description: [''],

        startDate: [null, [Validators.required]],

        endDate: [null, [Validators.required]],

        numberTickets: [0, [Validators.required]],

        numberAvailableTickets: [0, [Validators.required]],

      })
  }

  loadEventFields(){
    this.event.name=this.formGroupEvent.value.name
    this.event.price=this.formGroupEvent.value.price
    this.event.description=this.formGroupEvent.value.description
    this.event.startDate=this.formGroupEvent.value.startDate

    this.event.endDate=this.formGroupEvent.value.endDate

    this.event.numberTickets=this.formGroupEvent.value.numberTickets

    this.event.numberAvailableTickets=this.formGroupEvent.value.numberAvailableTickets

  }
  patchEventData(elt){
    this.formGroupEvent.patchValue({
      id: elt.id,
      name:elt.name,
      price:elt.price,
      description:elt.description,
      startDate:elt.startDate,

      endDate:elt.endDate,

      numberTickets:elt.numberTickets,

      numberAvailableTickets:elt.numberAvailableTickets,

    })
  }

  noPatchEventData(){
    this.formGroupEvent.patchValue({
      id:null,
      name:"",
      price: 0,
      description: "",
      startDate: null,
      endDate: null,
      numberTickets: 0,
      numberAvailableTickets: 0,

    })
  }

  public getEvents():void{
    this.eventService.getEvents()
      .pipe(
        map((e:Event[])=> e.map((evt:Event)=>this.imageProcessService.createImagesForEvent(evt)))
      )
      .subscribe(
      (response: Event[])=>{
        this.events=response;
        this.dataSource=response
      },
      (error: HttpErrorResponse)=>{
        alert(error.message);
      }
    )
  }


  getEventsByFarmId(id:number){
    this.eventService.getEventsByFarmId(id)
      .pipe(
        map((e:Event[],i)=> e.map((evt:Event)=>this.imageProcessService.createImagesForEvent(evt)))
      )
      .subscribe(
        (response:Event[])=>{
          this.events=response;
          this.dataSource=response
        },
        (error:HttpErrorResponse)=>{
          alert(error.message)
        }
      )
  }

  isFarmLinked():boolean{
    return this.farmId===undefined
  }


  addEventToFarmById(id:number){

    this.loadEventFields()
    document.getElementById('close-add-form').click();
    this.eventService.addEventToFarmById(id, this.prepareFormData(this.event)).subscribe(
      (response:Event)=>{
        this.getEventsByFarmId(id)
        this.formGroupEvent.reset()
        this.event.eventImages=[]
      },
      (error:HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }


  public onAddEvent(addForm: NgForm):void{
    document.getElementById('close-add-form').click();
    this.eventService.addEvent(this.prepareFormData(addForm.value)).subscribe(
      (response:Event)=>{
        console.log(response)
        this.getEvents();
        addForm.resetForm();
        this.event.eventImages=[]
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
        addForm.resetForm();
      }
    )
  }

  prepareFormData(event:Event):FormData{
    const formData=new FormData();
    formData.append(
      'event',
      new Blob([JSON.stringify(event)],
        {type:'application/json'})
    );

    for (let i=0; i<event.eventImages.length; i++){
      formData.append(
        'imageFile',
        event.eventImages[i].file,
        event.eventImages[i].file.name,
      )
    }

    return formData
  }

  public onUpdateEvent():void{
    document.getElementById('close-add-form').click();
    this.eventService.updateEvent(this.formGroupEvent.get('id').value,this.formGroupEvent.value).subscribe(
      (response:Event)=>{
        if(this.farmId) this.getEventsByFarmId(this.formGroupEvent.get('id').value)
        else this.getEvents();
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public onDeleteEvent():void{
    document.getElementById('close-delete-form').click();
    this.eventService.deleteEvent(this.formGroupEvent.get('id').value).subscribe(
      (response:Event)=>{
        console.log(response)
        this.getEventsByFarmId(this.formGroupEvent.get('id').value);
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public onDeleteAllEventsFromFarm(id:number):void{
    document.getElementById('close-delete-form').click();
    this.eventService.deleteEventsFromFarmById(id).subscribe(
      (response:Event)=>{
        console.log(response)
        this.getEventsByFarmId(id);
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }
  public onOpenModal(event: Event, mode:string, count:number):void{
    const container=document.getElementById('main-container');
    const button=document.createElement('button');
    button.type='button';
    button.style.display='none';
    button.setAttribute('data-bs-toggle','modal');
    if(mode==='add'){

      this.editing=false
      this.noPatchEventData()
      this.event.eventImages=[]
      button.setAttribute('data-bs-target','#addModal');
    }
    if(mode==='edit'){
      this.count=count;

      this.editing=true
      this.patchEventData(event)
      this.event.eventImages=[]

      this.event.eventImages=event.eventImages

      button.setAttribute('data-bs-target','#addModal');
    }
    if(mode==='delete'){
      this.count=count;
      this.deletingAll=false
      this.patchEventData(event)
      button.setAttribute('data-bs-target','#deleteModal');
    }
    if(mode==='deleteAll'){
      this.count=count;

      this.noPatchEventData()
      this.deletingAll=true
      button.setAttribute('data-bs-target','#deleteModal');
    }
    // @ts-ignore
    container.appendChild(button);
    button.click();
  }

  public searchEvents(value: string) {
    const results: Event[]=[];
    for(const event of this.events){
      if(event.price.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1

        || event.name.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || event.description.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || event.startDate.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || event.endDate.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || event.numberTickets.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || event.numberAvailableTickets.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1){
        results.push(event);
      }
    }
    this.events=results;
    if(results.length === 0 || !value){
      if(isNaN(this.farmId)) {
        this.getEvents();
      }
      else this.getEventsByFarmId(this.farmId)
    }
  }

  onFileSelect(event) {
    if(event.target.files){
      const file=event.target.files[0];

      const fileHandle:FileHandle={
        file: file,
        url: this.sanitizer.bypassSecurityTrustUrl(
          window.URL.createObjectURL(file))
      }
      this.event.eventImages.push(fileHandle)
    }

  }

  removeItem(i: number) {
    this.event.eventImages.splice(i,1)
  }

  fileDrop(fileHandel) {
    this.event.eventImages.push(fileHandel)
  }

  showDetails(event:Event) {
    this.dialog.open(EventDetailsComponent,{
      height: '500px',
    width:'800px',
    data:{
        images:event.eventImages,
      description:event.description
    }})
  }
}
