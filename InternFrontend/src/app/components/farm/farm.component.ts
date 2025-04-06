import { Component, OnInit } from '@angular/core';
import {FarmService} from "../../services/farm/farm.service";
import {Farm} from "../../model/farm/farm";
import {HttpErrorResponse} from "@angular/common/http";
import {FormBuilder, FormGroup, NgForm, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {DomSanitizer, Title} from "@angular/platform-browser";
import {FileHandle} from "../../model/file/file";
import {ImageService} from "../../services/image/image.service";
import {map} from "rxjs";
import {EttabaService} from "../../services/ettaba/ettaba.service";
import {Ettaba} from "../../model/ettaba/ettaba";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-farm',
  templateUrl: './farm.component.html',
  styleUrls: ['./farm.component.css']
})
export class FarmComponent implements OnInit {

  length = 50;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [5, 10, 25];

  showPageSizeOptions = true;
  showFirstLastButtons = true;

  pageEvent: PageEvent;
  public farmlength: any[]=[];

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
  }


  public farms:Farm[]=[];
  public deleteFarm: Farm;
  public count: number;

  public farm:Farm={
    id: null,
    name:"",
    longitude:0,
    latitude:0,
    address: {
      id:null,
      street:'',
      state:'',
      postalCode:0,
      houseNumber:'',
      city:'',
      region:''
    },
    farmImages:[],
    description: "",
    creationDate:new Date()
  }

  displayedColumns: string[] = ['no','name','longlat','adr', 'Available','details','edit','delete'];
  dataSource:Farm[]=[];


  formGroupFarm: FormGroup
  editing: boolean=false;

  constructor(private farmService:FarmService,
              private ettabaService: EttabaService,
              private router:Router,
              private route:ActivatedRoute,
              private fb:FormBuilder,
              private imageProcessService:ImageService,
              private sanitizer:DomSanitizer,
              private titleService: Title) { }

  ngOnInit(): void {

    this.titleService.setTitle('Farms | ETTABA');
    this.getFarms();

    this.initForm()
  }

  initForm(){
    this.formGroupFarm=this.fb.group(
      {
        id: [null],
        name: ['', [Validators.required,Validators.minLength(3)]],
        longitude: [0, [Validators.required,]],
        latitude: [0, [Validators.required]],
        description: ['', [Validators.required]],
        address:this.fb.group({
          id:[null],
          state:['', [Validators.required]],
          postalCode:[0, [Validators.required]],
          city:['', [Validators.required]],
          region:['', [Validators.required]],
          street:['', [Validators.required]],
          houseNumber:['', [Validators.required]],
      }
    )})
  }

  public getFarms():void{
    this.farmService.getFarms()
      .pipe(
        map((e:Farm[])=> e.map((f:Farm)=>this.imageProcessService.createImagesForFarm(f)))
      )
      .subscribe(
      (response: Farm[])=>{
        this.farms=response;
        this.dataSource=response;
        response.forEach(elt=>{
          this.getNumberEttabas(elt.id);
        })
      },
      (error: HttpErrorResponse)=>{
        alert(error.message);
      }
    )
  }

  loadFarmFields(){
    this.farm.name=this.formGroupFarm.value.name
    this.farm.latitude=this.formGroupFarm.value.latitude
    this.farm.longitude=this.formGroupFarm.value.longitude
    this.farm.description=this.formGroupFarm.value.description
    this.farm.address.street=this.formGroupFarm.value.address.street
    this.farm.address.region=this.formGroupFarm.value.address.region
    this.farm.address.city=this.formGroupFarm.value.address.city
    this.farm.address.state=this.formGroupFarm.value.address.state
    this.farm.address.postalCode=this.formGroupFarm.value.address.postalCode
    this.farm.address.houseNumber=this.formGroupFarm.value.address.houseNumber

  }
  patchFarmData(elt){
    this.formGroupFarm.patchValue({
      id: elt.id,
      name:elt.name,
      longitude:elt.longitude,
      latitude:elt.latitude,
      description:elt.description,
      address:{
        id:elt.address.id,
        street:elt.address.street,
        state:elt.address.state,
        city:elt.address.city,
        region:elt.address.region,
        postalCode:elt.address.postalCode,
        houseNumber:elt.address.houseNumber,

      },

    })
  }

  noPatchFarmData(){
    this.formGroupFarm.patchValue({
      id: null,
      name:'',
      longitude:0,
      latitude:0,
      description:'',
      address:{
        id:null,
        street:'',
        state:'',
        city:'',
        region:'',
        postalCode:0,
        houseNumber:'',
      },

    })
  }

  public onAddFarm():void{
    this.loadFarmFields()
    document.getElementById('close-add-form').click();
    this.farmService.addFarm(this.prepareFormData(this.farm)).subscribe(
      (response:Farm)=>{
        console.log(response)
        this.getFarms();
        this.formGroupFarm.reset()
        this.farm.farmImages=[]
      },
      (error:HttpErrorResponse) => {
        alert(error.message)

        this.formGroupFarm.reset()
      }
    )
  }

  prepareFormData(farm:Farm):FormData{
    const formData=new FormData();
    formData.append(
      'farm',
      new Blob([JSON.stringify(farm)],
        {type:'application/json'})
    );

    for (let i=0; i<farm.farmImages.length; i++){
      formData.append(
        'imageFile',
        farm.farmImages[i].file,
        farm.farmImages[i].file.name,
      )
    }

    return formData
  }

  public onUpdateFarm():void{
    document.getElementById('close-add-form').click();
    this.farmService.updateFarm(this.formGroupFarm.get('id').value,this.formGroupFarm.value).subscribe(
      (response:Farm)=>{
        console.log(response)
        this.getFarms();
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public onDeleteFarm(id: number):void{
    document.getElementById('close-delete-form').click();
    this.farmService.deleteFarm(id).subscribe(
      (response:Farm)=>{
        console.log(response)
        this.getFarms();
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }
  public onOpenModal(farm: Farm, mode:string, count:number):void{
    const container=document.getElementById('main-container');
    const button=document.createElement('button');
    button.type='button';
    button.style.display='none';
    button.setAttribute('data-bs-toggle','modal');
    if(mode==='add'){
      this.editing=false
      this.noPatchFarmData()

      this.farm.farmImages=[]
      button.setAttribute('data-bs-target','#addModal');
    }
    if(mode==='edit'){
      this.count=count;

      this.editing=true
      this.patchFarmData(farm)
      this.farm.farmImages=[]
      this.farm.farmImages=farm.farmImages

      button.setAttribute('data-bs-target','#addModal');
    }
    if(mode==='delete'){
      this.count=count;
      this.deleteFarm=farm;
      button.setAttribute('data-bs-target','#deleteModal');
    }
    // @ts-ignore
    container.appendChild(button);
    button.click();
  }

  public searchFarms(value: string) {
    const results: Farm[]=[];
    for(const farm of this.farms){
      if(farm.name.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || farm.longitude.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || farm.latitude.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || farm.description.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1){
        results.push(farm);
      }
    }
    this.farms=results;
    this.dataSource=results
    if(results.length === 0 || !value){
      this.getFarms();
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
      this.farm.farmImages.push(fileHandle)
    }

  }

  removeItem(i: number) {
    this.farm.farmImages.splice(i,1)
  }

  fileDrop(fileHandel) {
    this.farm.farmImages.push(fileHandel)
  }

  onSelect(id: number) {
    //this.router.navigate(['/farms',farm.id]);
    this.router.navigate([id],{relativeTo:this.route});

  }

  getNumberEttabas(id:number) {

    this.ettabaService.getEttabasByFarmId(id).subscribe(
      (response: Ettaba[]) => {
        this.farmlength.push(response.length);
        }, error => {
          console.log(error.message)
        }
      )
  }
}
