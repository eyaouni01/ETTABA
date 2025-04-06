import {FileHandle} from "../file/file";
import {Address} from "../adress/adress";

export interface Farm {
  id: number;
  name:string;
  longitude:number;
  latitude:number;
  address:Address;
  farmImages:FileHandle[];
  description: string;
  creationDate:Date;
}
