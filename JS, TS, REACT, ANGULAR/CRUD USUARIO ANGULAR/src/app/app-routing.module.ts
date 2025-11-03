import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateUserComponent } from './components/create-user/create-user.component';
import { ListUsersComponent } from './components/list-users/list-users.component';


const routes: Routes = [
{path: "createUser", component: CreateUserComponent}, 
{path: "listUsers", component: ListUsersComponent},
{path: "", component: CreateUserComponent},
{path:"**", redirectTo:""}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
