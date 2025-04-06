import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user/user";
import {Farm} from "../../model/farm/farm";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user/user.service";
import {DomSanitizer, Title} from "@angular/platform-browser";
import {ActivatedRoute, Router} from "@angular/router";
import {ImageService} from "../../services/image/image.service";
import {map} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {AuthService, Log} from "../../services/auth/auth.service";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

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


  public users: User[]=[];
  public count: number;

  userRoles: string[] = ['User','Admin'];

  displayedColumns: string[] = ['no','firstName','lastName','dob','email','phoneNumber','role','solde','createdAt','edit','delete'];
  dataSource:User[]=[];
  userId: number;


  currentUser: Log;

  constructor(private userService: UserService,
              private authService:AuthService,
              private titleService: Title,
              private imageProcessService:ImageService) {

    this.authService.user.subscribe(user => this.currentUser = user);
  }

  ngOnInit(): void {

    this.titleService.setTitle('Users | ETTABA');

    this.getUsers();
  }

  public getUsers():void{
    this.userService.getUsers()
      .pipe(
        map((e:User[])=> e.map((evt:User)=>this.imageProcessService.createImageForUser(evt)))
      )
      .subscribe(
        (response: User[])=>{
          this.users=response;
          this.dataSource=response
        },
        (error: HttpErrorResponse)=>{
          alert(error.message);
        }
      )
  }

  /*public onUpdateUser():void{
    document.getElementById('close-add-form').click();
    this.userService.updateUser(this.formGroupUser.get('id').value,this.formGroupUser.value).subscribe(
      (response:User)=>{
        console.log(response)
        this.getUsers();
      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }*/

  public onDeleteUser(id:number):void{
    document.getElementById('close-delete-form').click();
    this.userService.deleteUser(id).subscribe(
      (response:User)=>{
        console.log(response)

      },
      (error:HttpErrorResponse) => {
        alert(error.message)
      }
    )
  }

  prepareFormData(user:User):FormData{
    const formData=new FormData();
    formData.append(
      'user',
      new Blob([JSON.stringify(user)],
        {type:'application/json'})
    );
    formData.append(
      'imageFile',
      user.userImage.file,
      user.userImage.file.name,
    )
    return formData
  }

  public onOpenModal(user: User, mode:string, count:number):void{
    const container=document.getElementById('main-container');
    const button=document.createElement('button');
    button.type='button';
    button.style.display='none';
    button.setAttribute('data-bs-toggle','modal');
    if(mode==='edit'){
      this.count=count;

      button.setAttribute('data-bs-target','#addModal');
    }
    if(mode==='delete'){
      this.count=count;
      this.userId=user.id
      button.setAttribute('data-bs-target','#deleteModal');
    }
    // @ts-ignore
    container.appendChild(button);
    button.click();
  }

  public searchUsers(value: string) {
    const results: User[]=[];
    for(const user of this.users){
      if(user.firstName.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || user.lastName.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || user.email.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || user.phoneNumber.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1
        || user.solde.toLocaleString().toLowerCase().indexOf(value.toLowerCase()) !== -1){
        results.push(user);
      }
    }
    this.users=results;
    if(results.length === 0 || !value){
      this.getUsers();
    }
  }

  isNotCurrentUser(email: string) {
    return this.currentUser.email!==email
  }
}
