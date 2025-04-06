import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthInterceptor} from "../../shared/auth.interceptor";
import {Title} from "@angular/platform-browser";
import {first, fromEvent, map, merge, of, Subscription} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  formGroup:FormGroup;
  formGroupReset:FormGroup;

  msgerr: string;


  err:boolean
  msg = null;

  networkStatus: any;
  networkStatus$: Subscription = Subscription.EMPTY;

  constructor(private authService:AuthService,
              private router:Router,
              private _snackBar: MatSnackBar,
              private formBuilder:FormBuilder,
              private titleService: Title) { }

  ngOnInit(): void {

    this.titleService.setTitle('Login | ETTABA');
    this.initForm();
    this.checkNetworkStatus();
    this.initFormReset()
    this.authService.logout()
  }

  checkNetworkStatus() {
    this.networkStatus = navigator.onLine;
    this.networkStatus$ = merge(
      of(null),
      fromEvent(window, 'online'),
      fromEvent(window, 'offline')
    )
      .pipe(map(() => navigator.onLine))
      .subscribe(status => {
        console.log('status', status);
        this.networkStatus = status;
      });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }

  initForm(){
    this.formGroup= this.formBuilder.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]],

  });
  }

  initFormReset(){
    this.formGroupReset= this.formBuilder.group({
      email: ['', [Validators.required]],

    });
  }

  loginProcess() {
    if(this.formGroup.valid){
      this.authService.login(this.formGroup.getRawValue())
        .pipe(first())
        .subscribe({
        next:() => {
          this.openSnackBar('Logged in!','close')
        this.router.navigate(['home'])
      },
        error:(error:HttpErrorResponse) => {
          this.msgerr = "Email ou mot de passe incorrecte";

          this.openSnackBar('Email ou mot de passe incorrecte','close')
      },
    })
    }
  }


  checkMailandMat() {
    this.authService.getUserByEmail(this.formGroupReset.get('email').value).subscribe(
      (response :any) => {
        if(response){
          this.authService.existUserByEmail(this.formGroupReset.get('email').value).subscribe(value => {
            if(value){
              if(this.formGroupReset.get('email').value==response.email && value){
                if(response.tokenCreationDate==null){
                  this.msgerr=null
                  this.msg="Succés! Verifier votre courriel."
                  this.authService.forgetPassword(this.formGroupReset.get('email').value).subscribe(value1 => {
                  })
                  this.formGroupReset.reset()
                }
                if(response.tokenCreationDate!=null){
                  this.msgerr="Une requête mail a été déjà envoyée."
                  this.msg=null
                }
              }
              else {
                this.msg=null
                this.msgerr="Adresse mail incorrecte."
              }
            }
            else {
              this.msg=null
              this.msgerr='Adresse mail incorrecte.'
            }
          })

        }
        else {
          this.msg=null
          this.msgerr='Matricule ou adresse mail incorrecte.'
        }
      },(error : HttpErrorResponse) => {
        this._snackBar.open("Le serveur a répondu avec une ERREUR", "ERREUR");
      })
  }

}
