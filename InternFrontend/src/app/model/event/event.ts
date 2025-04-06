import {FileHandle} from "../file/file";

export interface Event {
  id:number;
  name:string;
  price: number;
  description: string;
  startDate: Date;
  endDate: Date;

  creationDate:Date;
  boughtDate: Date;
  numberTickets: number;
  numberAvailableTickets: number;
  eventImages:FileHandle[]
}
