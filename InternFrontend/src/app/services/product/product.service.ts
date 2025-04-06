import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs";
import {Product} from "../../model/product/product";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private apiServerUrl=environment.apiBaseUrl+'/api';

  constructor(private http:HttpClient) { }

  public getProductById(id:number): Observable<Product>{
    return this.http.get<Product>(`${this.apiServerUrl}/product/${id}`);
  }

  public getProducts(): Observable<Product[]>{
    return this.http.get<Product[]>(`${this.apiServerUrl}/product`);
  }

  public getProductsPromise(): Promise<Product[] | undefined>{
    return this.http.get<Product[]>(`${this.apiServerUrl}/product`).toPromise();
  }

  public getProductsByEttabaId(ettabaId:number): Observable<Product[]>{
    return this.http.get<Product[]>(`${this.apiServerUrl}/ettaba/${ettabaId}/product`);
  }

  public addProduct(product:FormData): Observable<Product>{
    return this.http.post<Product>(`${this.apiServerUrl}/product`,product);
  }

  public addProductToEttabaById(ettabaId:number,product:FormData): Observable<any>{
    return this.http.post<any>(`${this.apiServerUrl}/ettaba/${ettabaId}/product`,product);
  }

  public updateProduct(productId:number, product:Product): Observable<Product>{
    return this.http.put<Product>(`${this.apiServerUrl}/product/${productId}`,product);
  }

  public deleteProduct(productId:number): Observable<Product>{
    return this.http.delete<Product>(`${this.apiServerUrl}/product/${productId}`);
  }

  public deleteProductsFromEttabaById(ettabaId:number): Observable<Product>{
    return this.http.delete<Product>(`${this.apiServerUrl}/ettaba/${ettabaId}/product`);
  }
}
