import {Component, Inject, OnInit} from '@angular/core';
import { MAT_DIALOG_DATA} from "@angular/material/dialog";
import {FileHandle} from "../../model/file/file";

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.css']
})
export class EventDetailsComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data:any) { }

  ngOnInit(): void {
  }
}
