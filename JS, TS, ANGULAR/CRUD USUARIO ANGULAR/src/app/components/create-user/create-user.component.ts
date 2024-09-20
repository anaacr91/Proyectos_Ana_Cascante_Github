import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { UsersInterface } from '../../shared/interfaces/users.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrl: './create-user.component.css'
})
export class CreateUserComponent {
  
  hasErrors: boolean;
  name: FormControl;
  lastname: FormControl;
  mail: FormControl;
  birthday: FormControl;
  admin: FormControl;
  inputs: FormControl[];

  constructor(private router: Router) {
    this.hasErrors = false;
    this.name= new FormControl( '', [Validators.required, Validators.minLength(3)]);
    this.lastname= new FormControl( '', [Validators.required, Validators.minLength(3)]);
    this.mail= new FormControl ('', [Validators.required,  Validators.pattern('^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$')]);
    this.birthday= new FormControl ( '', [Validators.required]);
    this.admin= new FormControl( false );
    this.inputs=[this.name, this.lastname, this.mail, this.birthday, this.admin];
  }

  validateInputs():boolean{
    let areInputValid= true;
    for (const input of this.inputs){
      if (input.invalid){
        areInputValid=false;
        break;
      }
    }
    return areInputValid;
}

 enviarFormulario(event:Event) {
  event.preventDefault();

  if (!this.validateInputs()){
    this.hasErrors=true;
    return;}
    this.hasErrors=false;
  
  const usuarioNuevo: UsersInterface= {
    'name':this.name.value, 'lastname':this.lastname.value, 'mail':this.mail.value, 
    'birthday':this.birthday.value, 'admin':this.stringToBoolean(this.admin.value)
  }

  const usuarioSession= sessionStorage.getItem('usuarios');
  

    if (usuarioSession){

      const usuarioDatos= JSON.parse(usuarioSession);
      
      const nuevaListaUsuarios= [...usuarioDatos, usuarioNuevo];

      sessionStorage.setItem('usuarios', JSON.stringify(nuevaListaUsuarios));
  }else{
    sessionStorage.setItem('usuarios', JSON.stringify([usuarioNuevo]))
  }
  this.router.navigate(['/listUsers']);
}
 
stringToBoolean(valor:any):boolean{
  if (typeof valor=="string"){
  if(valor.toLowerCase()==="true"){
    return true;
  }else{
    return false;
  }
}
return valor;
}

}
