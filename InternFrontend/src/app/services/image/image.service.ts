import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {Event} from "../../model/event/event";
import {FileHandle} from "../../model/file/file";
import {DomSanitizer} from "@angular/platform-browser";
import {Farm} from "../../model/farm/farm";
import {User} from "../../model/user/user";
import {Animal} from "../../model/animal/animal";
import {Product} from "../../model/product/product";

@Injectable({
  providedIn: 'root'
})
export class ImageService {


  private apiServerUrl=environment.apiBaseUrl+'/api';

  constructor(private http:HttpClient,
              private sanitizer:DomSanitizer,) { }


  public createImagesForEvent(event:Event){
    const eventImages:any[]=event.eventImages;
    const eventImagesToFileHandle:FileHandle[]=[];

    for (let i=0;i<eventImages.length;i++){
      const imageFileData=eventImages[i];
      const imageBlob=this.dataURIToBlob(imageFileData.picByte,imageFileData.type);
      const imageFile=new File([imageBlob],imageFileData.name,{type:imageFileData.type});

      const finalFileHandle:FileHandle={
        file:imageFile,
        url:this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(imageFile))
      };

      eventImagesToFileHandle.push(finalFileHandle)
    }

    event.eventImages=eventImagesToFileHandle;
    return event;
  }

  public createImagesForProduct(product:Product){
    const productImages:any[]=product.productImages;
    const productImagesToFileHandle:FileHandle[]=[];

    for (let i=0;i<productImages.length;i++){
      const imageFileData=productImages[i];
      const imageBlob=this.dataURIToBlob(imageFileData.picByte,imageFileData.type);
      const imageFile=new File([imageBlob],imageFileData.name,{type:imageFileData.type});

      const finalFileHandle:FileHandle={
        file:imageFile,
        url:this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(imageFile))
      };

      productImagesToFileHandle.push(finalFileHandle)
    }

    product.productImages=productImagesToFileHandle;
    return product;
  }

  public createImagesForFarm(farm:Farm){
    const farmImages:any[]=farm.farmImages;
    const farmImagesToFileHandle:FileHandle[]=[];

    for (let i=0;i<farmImages.length;i++){
      const imageFileData=farmImages[i];
      const imageBlob=this.dataURIToBlob(imageFileData.picByte,imageFileData.type);
      const imageFile=new File([imageBlob],imageFileData.name,{type:imageFileData.type});

      const finalFileHandle:FileHandle={
        file:imageFile,
        url:this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(imageFile))
      };

      farmImagesToFileHandle.push(finalFileHandle)
    }

    farm.farmImages=farmImagesToFileHandle;
    return farm;
  }

  public createImagesForAnimal(animal:Animal){
    const animalImages:any[]=animal.animalImages;
    const animalImagesToFileHandle:FileHandle[]=[];

    for (let i=0;i<animalImages.length;i++){
      const imageFileData=animalImages[i];
      const imageBlob=this.dataURIToBlob(imageFileData.picByte,imageFileData.type);
      const imageFile=new File([imageBlob],imageFileData.name,{type:imageFileData.type});

      const finalFileHandle:FileHandle={
        file:imageFile,
        url:this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(imageFile))
      };

      animalImagesToFileHandle.push(finalFileHandle)
    }

    animal.animalImages=animalImagesToFileHandle;
    return animal;
  }

  public createImageForUser(user:User){

    if(user.userImage) {
      const userImage: any = user.userImage;

      const imageBlob = this.dataURIToBlob(userImage.picByte, userImage.type);
      const imageFile = new File([imageBlob], userImage.name, {type: userImage.type});

      user.userImage = {
        file: imageFile,
        url: this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(imageFile))
      };
    }
    return user;
  }

  public dataURIToBlob(picBytes,imageType){
    const byteString=window.atob(picBytes);
    const arrBuffer=new ArrayBuffer(byteString.length)
    const int8Arr=new Uint8Array(arrBuffer)

    for (let i=0;i<byteString.length;i++){
      int8Arr[i]=byteString.charCodeAt(i);
    }

    return new Blob([int8Arr], {type: imageType})
  }

  uploadImg(file):Observable<any>{
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', file, file.name);
    return this.http.post(`${this.apiServerUrl}/image`, uploadImageData);
  }

  imgUpload(imageFormData):Observable<any>{
    return this.http.post(`${this.apiServerUrl}/image/`, imageFormData, { observe: 'response' })
  }

  getImg(id : number): Observable<any> {
    return this.http.get(`${this.apiServerUrl}/image/${id}`);
  }

  getAllImg(): Observable<any> {
    return this.http.get(`${this.apiServerUrl}/image`);
  }

  deleteImage(id : number) : Observable<void> {
    return this.http.get<void>(`${this.apiServerUrl}/image/${id}`);
  }
}
