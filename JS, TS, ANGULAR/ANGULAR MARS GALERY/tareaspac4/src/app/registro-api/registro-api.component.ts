import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-registro-api',
  templateUrl: './registro-api.component.html',
  styleUrl: './registro-api.component.css',
})
export class RegistroApiComponent {
  nombreCompleto: FormControl; 
  username: FormControl;
  mail: FormControl;
  inputs: FormControl[];
  primerintento: boolean;

  constructor(private http: HttpClient) {
 
    this.nombreCompleto = new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(200),
    ]);
    this.username = new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(200),
    ]);
    this.mail = new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(200),
      Validators.email,
    ]);
    this.inputs = [this.nombreCompleto, this.username, this.mail];
    this.primerintento = false;
  }

  onsubmit($event: Event) {
    $event.preventDefault(); 
    this.primerintento = true;
   
    if (this.verificarFormularioCorrecto()) {
      const nuevaPersona = {
       
        nombre: this.nombreCompleto.value,
        user: this.username.value,
        mail: this.mail.value,
      };
     
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
      });
     
      this.http
        .post('https://jsonplaceholder.typicode.com/users', nuevaPersona, 
        {headers})
        .subscribe(
          (Response) => {
            sessionStorage.setItem('registro', JSON.stringify(Response));
          },
          (error: HttpErrorResponse) => {
            if (error.error instanceof ErrorEvent) {
              alert(error.error.message);
            } else {
              alert(error.message);
            }
          }
        );
    }
  }

 
  verificarFormularioCorrecto(): boolean {
    let escorrecto: boolean = true;
    for (const input of this.inputs) {
      if (input.invalid) {
        escorrecto = false;
        break;
      }
    }
    return escorrecto;
  }
}
