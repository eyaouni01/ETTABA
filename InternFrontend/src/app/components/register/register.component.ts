import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {PasswordValidator} from "../../shared/password.validator";
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  formGroup:FormGroup;

  constructor(private formBuilder:FormBuilder,
              private authService:AuthService,
              private titleService: Title,
              private router:Router,) { }

  ngOnInit(): void {
    this.titleService.setTitle('Register | ETTABA')
    this.initForm()
  }

  initForm(){

    this.formGroup=this.formBuilder.group({
      firstname:['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
      lastname:['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
      email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(30)]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(30)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(30)]],
      role:['0'],
    },{validators:PasswordValidator})
  }

  registerProcess():void {

    this.authService.register(this.formGroup.getRawValue()).subscribe(
      response=>{
        console.log(response)
        this.router.navigate(['login'])
      }
    )
  }
}
