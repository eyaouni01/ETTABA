import {FileHandle} from "../file/file";

export interface Animal {
  id:number;
  name:string;
  type:string;
  description: string;
  age: number;
  price: number;

  creationDate:Date;
  boughtDate: Date;

  animalImages:FileHandle[]
}
