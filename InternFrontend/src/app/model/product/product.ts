import {FileHandle} from "../file/file";

export interface Product {
  id:number;
  name:string;
  etat:string;
  boughtPrice:number;
  soldPrice:number;
  weight:number;

  creationDate:Date;
  boughtDate: Date;

  description:string;
  productImages:FileHandle[]
}
