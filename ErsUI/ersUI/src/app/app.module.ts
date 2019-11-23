import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthService } from './services/auth.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavComponent } from './components/nav/nav.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { LoginComponent } from './components/login/login.component';
import { FormsModule } from '@angular/forms';
import { UpdateService } from './services/update.service';
import { ReimbursmentsComponent } from './components/reimbursments/reimbursments.component';
import { ReimbService } from './services/reimb.service';
import { RoleOptionComponent } from './components/role-option/role-option.component';
import { PagesComponent } from './components/pages/pages.component';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    NotFoundComponent,
    LoginComponent,
    ReimbursmentsComponent,
    RoleOptionComponent,
    PagesComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    AuthService,
    UpdateService,
    ReimbService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
