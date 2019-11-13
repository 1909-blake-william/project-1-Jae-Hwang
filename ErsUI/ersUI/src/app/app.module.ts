import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthService } from './services/auth.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavComponent } from './components/nav/nav.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { PokemonComponent } from './components/pokemon/pokemon.component';
import { PokemonCardComponent } from './components/pokemon-card/pokemon-card.component';
import { FormsModule } from '@angular/forms';
import { UpdateService } from './services/update.service';
import { ReimbursmentsComponent } from './components/reimbursments/reimbursments.component';
import { ReimbService } from './services/reimb.service';
import { RoleOptionComponent } from './components/role-option/role-option.component';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    NotFoundComponent,
    HomeComponent,
    LoginComponent,
    PokemonComponent,
    PokemonCardComponent,
    ReimbursmentsComponent,
    RoleOptionComponent
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
