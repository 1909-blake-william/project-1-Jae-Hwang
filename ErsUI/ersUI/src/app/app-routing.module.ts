import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { PokemonComponent } from './components/pokemon/pokemon.component';
import { ReimbursmentsComponent } from './components/reimbursments/reimbursments.component';
import { RoleOptionComponent } from './components/role-option/role-option.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'pokemon',
    component: PokemonComponent
  },
  {
    path: 'reimbursements',
    component: ReimbursmentsComponent
  },
  {
    path: 'role-option',
    component: RoleOptionComponent
  },
  {
    path: '**',
    pathMatch: 'full',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
