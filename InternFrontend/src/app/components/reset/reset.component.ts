import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {AuthService} from "../../services/auth/auth.service";
import {HttpErrorResponse} from "@angular/common/http";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-reset',
  templateUrl: './reset.component.html',
  styleUrls: ['./reset.component.css']
})
export class ResetComponent implements OnInit {

  token: string;
  isValidToken: boolean;
  user:any

  formGroup:FormGroup

  constructor(private authService : AuthService,
              private route: ActivatedRoute,
              private router : Router,
              private _snackBar: MatSnackBar,
              private fb:FormBuilder,
              private titleService: Title) { }

  ngOnInit(): void {

    this.titleService.setTitle('Reset | ETTABA');
    this.initForm()
    this.route.queryParams
      .subscribe(params => {
          this.token = params['token'];
        }
      );

    this.checkTokenValidity()
  }


  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }

  initForm(){
    this.formGroup=this.fb.group({
        password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(30)]],
        confirmPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(30)]],

      }
    )
  }
  checkTokenValidity(){
    this.authService.existUserByToken(this.token).subscribe(
      (response :boolean) => {
        this.isValidToken = response == true;
      },(error : HttpErrorResponse) => {
        alert(error)
        this.isValidToken=false;
      }
    );
  }

  resetPass() {
    this.authService.resetPassword(this.token,this.formGroup.get('password').value).subscribe(
      () => {
        this.router.navigate(['login']);
        this.openSnackBar("Mot de passe changé",'Dissmiss')
      },(error : HttpErrorResponse) => {
        this.router.navigate(['login']);
        //this.toaster.error("Entrer un matricule ou Mot de passe exacte","ERREUR");

        this.openSnackBar("Mot de passe changé",'Dissmiss')
      })
  }
}
