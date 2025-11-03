import { registerLocaleData } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegistroApiComponent } from './registro-api/registro-api.component';
import { RoversMarteComponent } from './rovers-marte/rovers-marte.component';
import { autGuard } from './shared/guards/aut.guard';

const routes: Routes = [
  {path: "registro", component:RegistroApiComponent},
  {path:"rovers", component:RoversMarteComponent, canActivate:[autGuard]},
  {path:"", component:RegistroApiComponent},
  {path:"**", component:RegistroApiComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
