import { Component, OnInit } from '@angular/core';
import {Animal} from "../../model/animal/animal";
import {AnimalService} from "../../services/animal/animal.service";
import {HttpErrorResponse} from "@angular/common/http";
import {FormBuilder, FormGroup, NgForm, Validators} from "@angular/forms";
import {DomSanitizer, Title} from "@angular/platform-browser";
import {Event} from "../../model/event/event";
import {FileHandle} from "../../model/file/file";
import {ImageService} from "../../services/image/image.service";
import {map} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {Farm} from "../../model/farm/farm";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-animal',
  templateUrl: './animal.component.html',
  styleUrls: ['./animal.component.css']
})
export class AnimalComponent implements OnInit {

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


  public animals: Animal[]=[];
  public count: number;

  public farmId:number;
  farm:Farm

  public animal:Animal={
    id:null,
    name:'',
    type:'',
    description: '',
    age: 0,
    price:0,
    animalImages:[],

    boughtDate:new Date(),
    creationDate:new Date()
  }


  animalTypes: string[] = ['mammals', 'fish', 'birds', 'reptiles', 'amphibians'];

  displayedColumns: string[] = ['no','name','type','age','price','edit','delete'];
  dataSource:Animal[]=[];

  formGroupAnimal: FormGroup
  editing: boolean=false;
  deletingAll: boolean=false;

  constructor(private animalService: AnimalService,
              private titleService: Title,
              private fb:FormBuilder,
              private sanitizer:DomSanitizer,

              private router:Router,
              private route:ActivatedRoute,
              private imageProcessService:ImageService) { }

  ngOnInit(): void {

    this.titleService.setTitle('Animals | ETTABA');

    this.route.parent.data
      .subscribe((data) => {
          if(data['farm'] !== undefined) {
            this.farm = data['farm'];
            this.farmId = this.farm.id
            this.getAnimalsByFarmId(this.farmId)
          }
          else {
            this.getAnimals();
          }
        },
        error => {
          alert(error)
        });

    this.initForm()
  }

  initForm(){
    this.formGroupAnimal=this.fb.group(
      {
        id: [null],
        name: ['', [Validators.required, Validators.minLength(3)]],
        type: [0, [Validators.required,]],
        age: [0, [Validators.required]],
        price: [0, [Validators.required]],
        description: [''],
      })
  }


  loadAnimalFields(){
    this.animal.name=this.formGroupAnimal.value.name
    this.animal.type=this.formGroupAnimal.value.type
    this.animal.price=this.formGroupAnimal.value.price
    this.animal.age=this.formGroupAnimal.value.age

    this.animal.description=this.formGroupAnimal.value.description

  }
  patchAnimalData(elt){
    this.formGroupAnimal.patchValue({
      id: elt.id,
      name:elt.name,
      price:elt.price,
      description:elt.description,
      age:elt.age,

      type:elt.type,
    })
  }

  noPatchAnimalData(){
    this.formGroupAnimal.patchValue({
      id:null,
      name:"",
      price: 0,
      description: "",
      age: 0,
      type: "",

    })
  }

  public getAnimals():void{
    this.animalService.getAnimals()
      .pipe(
        map((e:Animal[])=> e.map((evt:Animal)=>this.imageProcessService.createImagesForAnimal(evt)))
      )
      .subscribe(
      (response: Animal[])=>{
        this.animals=response;
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


  addAnimalToFarmById(id:number){

    document.getElementById('close-add-form').click();
    this.loadAnimalFields()
    this.animalService.addAnimalToFarmById(id, this.prepareFormData(this.animal)).subscribe(
      (response:Animal)=>{
        this.getAnimalsByFarmId(id)
        this.formGroupAnimal.reset()
        this.animal.animalImages=[]
      },
      (error:HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  getAnimalsByFarmId(id:number){
    this.animalService.getAnimalsByFarmId(id)
      .pipe(
        map((e:Animal[])=> e.map((evt:Animal)=>this.imageProcessService.createImagesForAnimal(evt)))
      )
      .subscribe(
      (response:Animal[])=>{
        this.animals=response;
        this.dataSource=response;
      },
      (error:HttpErrorResponse)=>{
        alert(error.message)
      }
    )
  }

  public onAddAnimal():void{
    document.getElementById('close-add-form').click();
    this.animalService.addAnimal(this.prepareFormData(this.animal)).subscribe(
      (response:Animal)=>{
        console.log(response)
        this.getAnimals();
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public onUpdateAnimal():void{
    document.getElementById('close-add-form').click();
    this.animalService.updateAnimal(this.formGroupAnimal.get('id').value,this.formGroupAnimal.value).subscribe(
      (response:Animal)=>{
        console.log(response)
        this.getAnimals();
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public onDeleteAnimal():void{
    document.getElementById('close-delete-form').click();
    this.animalService.deleteAnimal(this.formGroupAnimal.get('id').value).subscribe(
      (response:Animal)=>{
        console.log(response)

        this.getAnimalsByFarmId(this.formGroupAnimal.get('id').value);
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public onDeleteAllAnimalsFromFarm(id:number):void{
    document.getElementById('close-delete-form').click();
    this.animalService.deleteAnimalsFromFarmById(id).subscribe(
      (response:Animal)=>{
        console.log(response)

        this.getAnimalsByFarmId(id);
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  prepareFormData(animal:Animal):FormData{
    const formData=new FormData();
    formData.append(
      'animal',
      new Blob([JSON.stringify(animal)],
        {type:'application/json'})
    );

    for (let i=0; i<animal.animalImages.length; i++){
      formData.append(
        'imageFile',
        animal.animalImages[i].file,
        animal.animalImages[i].file.name,
      )
    }

    return formData
  }

  public onOpenModal(animal: Animal, mode:string, count:number):void{
    const container=document.getElementById('main-container');
    const button=document.createElement('button');
    button.type='button';
    button.style.display='none';
    button.setAttribute('data-bs-toggle','modal');
    if(mode==='add'){
      this.editing=false
      this.noPatchAnimalData()
      this.animal.animalImages=[]
      button.setAttribute('data-bs-target','#addModal');
    }
    if(mode==='edit'){
      this.count=count;

      this.editing=true
      this.patchAnimalData(animal)
      this.animal.animalImages=[]

      this.animal.animalImages=animal.animalImages

      button.setAttribute('data-bs-target','#addModal');
    }
    if(mode==='delete'){
      this.count=count;
      this.deletingAll=false
      this.patchAnimalData(animal)
      button.setAttribute('data-bs-target','#deleteModal');
    }
    if(mode==='deleteAll'){
      this.count=count;

      this.noPatchAnimalData()
      this.deletingAll=true
      button.setAttribute('data-bs-target','#deleteModal');
    }
    // @ts-ignore
    container.appendChild(button);
    button.click();
  }

  public searchAnimals(value: string) {
    const results: Animal[]=[];
    for(const animal of this.animals){
      if(animal.price.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || animal.name.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || animal.type.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || animal.description.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || animal.age.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1){
        results.push(animal);
      }
    }
    this.animals=results;
    if(results.length === 0 || !value){
      if(isNaN(this.farmId)) this.getAnimals();
      else this.getAnimalsByFarmId(this.farmId)
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
      this.animal.animalImages.push(fileHandle)
    }

  }

  removeItem(i: number) {
    this.animal.animalImages.splice(i,1)
  }

  fileDrop(fileHandel) {
    this.animal.animalImages.push(fileHandel)
  }
}
