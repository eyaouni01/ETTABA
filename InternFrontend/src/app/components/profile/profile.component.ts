import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from "@angular/forms";
import {ImageService} from "../../services/image/image.service";
import {UserService} from "../../services/user/user.service";
import {DomSanitizer, Title} from "@angular/platform-browser";
import {User} from "../../model/user/user";
import {map} from "rxjs";
import {AuthService, Log} from "../../services/auth/auth.service";
import {Event} from "../../model/event/event";
import {FileHandle} from "../../model/file/file";
import {HttpErrorResponse} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  uploadedImage: File;
  dbImage: any;
  postResponse: any;
  successResponse: string;
  image: any;

  public user:User={
    id:null,
    firstName:"",
    lastName:"",
    dob:null,
    createdAt:null,
    email:"",
    password:"",
    solde:0,
    userImage:{
      file:null,
      url:null
    },
    role:null,
    address: {
      id:null,
      street:"",
      state:"",
      postalCode:0,
      houseNumber:"",
      city:"",
      region:""
    },
    phoneNumber:""
  }

  formGroupUser: FormGroup
  imageSrc: string;

  currentUser: Log;

  constructor(private authService:AuthService,
              private formBuilder:FormBuilder,
              private imageService:ImageService,
              private sanitizer:DomSanitizer,
              private userService:UserService,
              private activatedRoute: ActivatedRoute,
              private titleService: Title) {

    this.authService.user.subscribe(user => this.currentUser = user);

  }

  ngOnInit(): void {

    this.titleService.setTitle('Profile | ETTABA');

    this.activatedRoute.data.subscribe((response: any) => {
      console.log('user FETCHING', response);
      this.user = response.user;
      console.log('user FETCHED');
      this.initUserForm(this.user);
      this.formGroupUser.get('email').disable()
      this.formGroupUser.get('username').disable()
      this.formGroupUser.get('createdAt').disable()

    });


  }

  loadUserFields(){
    this.user.firstName=this.formGroupUser.value.firstName
    this.user.lastName=this.formGroupUser.value.lastName
    this.user.dob=this.formGroupUser.value.dob
    this.user.phoneNumber=this.formGroupUser.value.phoneNumber
    this.user.email=this.formGroupUser.value.email
    this.user.solde=this.formGroupUser.value.solde

    this.user.address.street=this.formGroupUser.value.address.street
    this.user.address.region=this.formGroupUser.value.address.region
    this.user.address.city=this.formGroupUser.value.address.city
    this.user.address.state=this.formGroupUser.value.address.state
    this.user.address.postalCode=this.formGroupUser.value.address.postalCode
    this.user.address.houseNumber=this.formGroupUser.value.address.houseNumber



  }

  addUserImgToBD(){

    this.loadUserFields()
    this.userService.addImgToUser(this.prepareFormData(this.user)).subscribe(
      (response)=>{
        console.log(response)
      },
      (error:HttpErrorResponse) => {
        alert(error.message);
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

  initUserForm(user:any){
    this.formGroupUser=this.formBuilder.group(
      {

        id: [user.id],
        username: [user.firstName+' '+user.lastName],
        firstname: [user.firstName, [Validators.required,]],
        lastname: [user.lastName, [Validators.required]],
        solde: [user.solde],
        email: [user.email, [Validators.required]],
        phoneNumber: [user.phoneNumber, [Validators.required]],
        dob: [user.dob, [Validators.required]],
        createdAt: [user.createdAt],
        address:this.formBuilder.group({
          id: [user.address.id],
          state:[user.address.state, [Validators.required]],
          postalCode:[user.address.postalCode, [Validators.required]],
          city:[user.address.city, [Validators.required]],
          region:[user.address.region, [Validators.required]],
          street:[user.address.street, [Validators.required]],
          houseNumber:[user.address.houseNumber, [Validators.required]],
        })
      }
    )
  }

  onFileSelect(event) {
    if(event.target.files){
      const file=event.target.files[0];

      this.user.userImage={
        file: file,
        url: this.sanitizer.bypassSecurityTrustUrl(
          window.URL.createObjectURL(file))
      }
    }

  }

  removeItem() {
    this.user.userImage=null;
  }

  fileDrop(fileHandel) {
    this.user.userImage=fileHandel;
  }

  updateUser() {
    this.loadUserFields()
    this.userService.updateUser(this.formGroupUser.get('id').value,this.formGroupUser.value).subscribe(
      (response)=>{
        console.log(response)
      }
    )
  }



  imageUploadAction() {
    const imageFormData = new FormData();
    imageFormData.append('image', this.uploadedImage, this.uploadedImage.name);
    //imageFormData.append('image', this.formGroup.get('file').value, this.uploadedImage.name);


    this.imageService.imgUpload(imageFormData).subscribe((response) => {
          if (response.status === 200) {
            this.postResponse = response;
            this.successResponse = this.postResponse.body.message;
          } else {
            this.successResponse = 'Image not uploaded due to some error!';
          }
        }
      );
  }

  viewImage(id:number) {
    this.imageService.getImg(id)
      .subscribe(
        res => {
          this.postResponse = res;
          this.dbImage = 'data:image/jpeg;base64,' + this.postResponse.image;
        }
      );
  }
}
