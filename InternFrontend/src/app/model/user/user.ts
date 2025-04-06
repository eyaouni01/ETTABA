import {Image} from "../image/image";
import {FileHandle} from "../file/file";
import {Address} from "../adress/adress";

export interface User {
  id:number,
  firstName:string,
  lastName:string,
  dob:Date,
  createdAt:Date,
  email:string
  password:string,
  solde:number
  userImage:FileHandle
  role:string
  address:Address
  phoneNumber:string
}
