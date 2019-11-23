import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ReimbursmentsComponent } from './components/reimbursments/reimbursments.component';
import { RoleOptionComponent } from './components/role-option/role-option.component';
import { PagesComponent } from './components/pages/pages.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
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
    path: 'pages',
    component: PagesComponent
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: '/login'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
