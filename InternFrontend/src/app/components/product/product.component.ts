import {Component, Input, OnInit} from '@angular/core';
import {Product} from "../../model/product/product";
import {Ettaba} from "../../model/ettaba/ettaba";
import {FormBuilder, FormGroup, NgForm, Validators} from "@angular/forms";
import {ProductService} from "../../services/product/product.service";
import {DomSanitizer, Title} from "@angular/platform-browser";
import {MatDialog} from "@angular/material/dialog";
import {ActivatedRoute} from "@angular/router";
import {ImageService} from "../../services/image/image.service";
import {map} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {FileHandle} from "../../model/file/file";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

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


  public products: Product[]=[];
  public count: number;
  public product:Product={
    id:null,
    name:"",
    etat:'',
    boughtPrice: 0,
    soldPrice: 0,
    weight: 0,
    description: '',
    productImages:[],
    boughtDate:new Date(),
    creationDate:new Date()
  }

  @Input('parentData') public ettabaId:number;

  ettaba:Ettaba

  productStates: string[] = ['SEED', 'INPROGRESS', 'READY'];


  displayedColumns: string[] = ['no','name','etat', 'boughtPrice', 'soldPrice','weight','edit','delete'];
  dataSource:Product[]=[];

  formGroupProduct: FormGroup
  editing: boolean=false;
  deletingAll: boolean=false;

  constructor(private productService: ProductService,
              private sanitizer:DomSanitizer,
              private dialog:MatDialog,
              private fb:FormBuilder,
              private route:ActivatedRoute,
              private imageProcessService:ImageService,
              private titleService: Title) { }

  ngOnInit(): void {
    this.titleService.setTitle('Products | ETTABA');

    //this.ettaba=this.route.snapshot.data['ettaba']
        if(this.ettabaId !== undefined) {
          this.getProductsByEttabaId(this.ettabaId)
        }
        else {
          this.getProducts();
        }
    this.initForm()
  }

  initForm(){
    this.formGroupProduct=this.fb.group(
      {
        id: [null],
        name: ['', [Validators.required,Validators.minLength(3)]],
        etat: ['', [Validators.required,]],
        boughtPrice: [0, [Validators.required]],

        soldPrice: [0, [Validators.required]],
        weight: [0, [Validators.required]],
        description: [''],

      })
  }

  loadProductFields(){
    this.product.name=this.formGroupProduct.value.name
    this.product.etat=this.formGroupProduct.value.etat
    this.product.boughtPrice=this.formGroupProduct.value.boughtPrice

    this.product.soldPrice=this.formGroupProduct.value.soldPrice

    this.product.weight=this.formGroupProduct.value.weight
    this.product.description=this.formGroupProduct.value.description

  }
  patchProductData(elt){
    this.formGroupProduct.patchValue({
      id: elt.id,
      name:elt.name,
      etat:elt.etat,
      boughtPrice:elt.boughtPrice,

      soldPrice:elt.soldPrice,
      weight:elt.weight,
      description:elt.description,
    })
  }

  noPatchProductData(){
    this.formGroupProduct.patchValue({
      id:null,
      name:"",
      etat:'',
      boughtPrice: 0,
      soldPrice: 0,
      weight: 0,
      description: '',

    })
  }

  public getProducts():void{
    this.productService.getProducts()
      .pipe(
        map((e:Product[])=> e.map((evt:Product)=>this.imageProcessService.createImagesForProduct(evt)))
      )
      .subscribe(
        (response: Product[])=>{
          this.products=response;
          this.dataSource=response
        },
        (error: HttpErrorResponse)=>{
          alert(error.message);
        }
      )
  }


  getProductsByEttabaId(id:number){
    this.productService.getProductsByEttabaId(id)
      .pipe(
        map((e:Product[],i)=> e.map((evt:Product)=>this.imageProcessService.createImagesForProduct(evt)))
      )
      .subscribe(
        (response:Product[])=>{
          this.products=response;
          this.dataSource=response
        },
        (error:HttpErrorResponse)=>{
          alert(error.message)
        }
      )
  }

  isEttabaLinked():boolean{
    return this.ettabaId === undefined
  }


  addProductToEttabaById(id:number){

    this.loadProductFields()
    document.getElementById('close-add-form').click();
    this.productService.addProductToEttabaById(id, this.prepareFormData(this.product)).subscribe(
      (response:Product)=>{
        this.getProductsByEttabaId(id)
        this.formGroupProduct.reset()
        this.product.productImages=[]
      },
      (error:HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }


  public onAddProduct(addForm: NgForm):void{
    document.getElementById('close-add-form').click();
    this.productService.addProduct(this.prepareFormData(addForm.value)).subscribe(
      (response:Product)=>{
        console.log(response)
        this.getProducts();
        addForm.resetForm();
        this.product.productImages=[]
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
        addForm.resetForm();
      }
    )
  }

  prepareFormData(product:Product):FormData{
    const formData=new FormData();
    formData.append(
      'product',
      new Blob([JSON.stringify(product)],
        {type:'application/json'})
    );

    for (let i=0; i<product.productImages.length; i++){
      formData.append(
        'imageFile',
        product.productImages[i].file,
        product.productImages[i].file.name,
      )
    }

    return formData
  }

  public onUpdateProduct():void{
    document.getElementById('close-add-form').click();
    this.productService.updateProduct(this.formGroupProduct.get('id').value,this.formGroupProduct.value).subscribe(
      (response:Product)=>{
        if(this.ettabaId) this.getProductsByEttabaId(this.formGroupProduct.get('id').value)
        else this.getProducts();
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public onDeleteProduct():void{
    document.getElementById('close-delete-form').click();
    this.productService.deleteProduct(this.formGroupProduct.get('id').value).subscribe(
      (response:Product)=>{
        console.log(response)
        this.getProductsByEttabaId(this.formGroupProduct.get('id').value);
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  public onDeleteAllProductsFromEttaba(id:number):void{
    document.getElementById('close-delete-form').click();
    this.productService.deleteProductsFromEttabaById(id).subscribe(
      (response:Product)=>{
        console.log(response)
        this.getProductsByEttabaId(id);
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }
  public onOpenModal(product: Product, mode:string, count:number):void{
    const container=document.getElementById('main-container');
    const button=document.createElement('button');
    button.type='button';
    button.style.display='none';
    button.setAttribute('data-bs-toggle','modal');
    if(mode==='add'){

      this.editing=false
      this.noPatchProductData()
      this.product.productImages=[]
      button.setAttribute('data-bs-target','#addModal');
    }
    if(mode==='edit'){
      this.count=count;

      this.editing=true
      this.patchProductData(product)
      this.product.productImages=[]

      this.product.productImages=product.productImages

      button.setAttribute('data-bs-target','#addModal');
    }
    if(mode==='delete'){
      this.count=count;
      this.deletingAll=false
      this.patchProductData(product)
      button.setAttribute('data-bs-target','#deleteModal');
    }
    if(mode==='deleteAll'){
      this.count=count;

      this.noPatchProductData()
      this.deletingAll=true
      button.setAttribute('data-bs-target','#deleteModal');
    }
    // @ts-ignore
    container.appendChild(button);
    button.click();
  }

  public searchProducts(value: string) {
    const results: Product[]=[];
    for(const product of this.products){
      if(product.etat.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1

        || product.name.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || product.description.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || product.boughtPrice.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || product.soldPrice.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || product.weight.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1){
        results.push(product);
      }
    }
    this.products=results;
    if(results.length === 0 || !value){
      if(isNaN(this.ettabaId)) {
        this.getProducts();
      }
      else this.getProductsByEttabaId(this.ettabaId)
    }
  }

  onFileSelect(product) {
    if(product.target.files){
      const file=product.target.files[0];

      const fileHandle:FileHandle={
        file: file,
        url: this.sanitizer.bypassSecurityTrustUrl(
          window.URL.createObjectURL(file))
      }
      this.product.productImages.push(fileHandle)
    }

  }

  removeItem(i: number) {
    this.product.productImages.splice(i,1)
  }

  fileDrop(fileHandel) {
    this.product.productImages.push(fileHandel)
  }

  /*showDetails(product:Product) {
    this.dialog.open(ProductDetailsComponent,{
      height: '500px',
      width:'800px',
      data:{
        images:product.productImages,
        description:product.description
      }})
  }*/

}
