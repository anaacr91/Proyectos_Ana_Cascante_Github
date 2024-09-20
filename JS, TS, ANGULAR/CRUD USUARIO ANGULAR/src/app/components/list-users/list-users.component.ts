import { Component, OnInit } from '@angular/core';
import { UsersInterface } from '../../shared/interfaces/users.interface';

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrl: './list-users.component.css'
})

export class ListUsersComponent implements OnInit{

usuariosGuardados: UsersInterface[];

constructor(){
  this.usuariosGuardados= [];
}

  ngOnInit(): void {
    const usuariosObtenidos=sessionStorage.getItem('usuarios');
   
    if(usuariosObtenidos){
      this.usuariosGuardados=JSON.parse(usuariosObtenidos);
    }
  } 

  eliminarUsuario(user: UsersInterface) {
    let index = this.usuariosGuardados.indexOf(user);
    this.usuariosGuardados.splice(index, 1);
    this.actualizarSesionStorge();
}

cambiarRolUsuario(user: UsersInterface) {
  let index = this.usuariosGuardados.indexOf(user);
  if (!this.usuariosGuardados[index].admin) {
      this.usuariosGuardados[index].admin = true;
  } else {
      this.usuariosGuardados[index].admin = false;
  }
  this.actualizarSesionStorge();
}

actualizarSesionStorge(){
  sessionStorage.setItem('usuarios',JSON.stringify(this.usuariosGuardados));
}

obtenerTipo(tipo: any) {
  return typeof tipo;
}

obtenerAdmin(admin:boolean):boolean{
  if (typeof admin === 'boolean' && admin === true) {
    return true;
  } else {
    return false;
  }
}

}

