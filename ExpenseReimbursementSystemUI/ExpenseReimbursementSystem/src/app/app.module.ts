import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BlakeComponent } from './components/blake/blake.component';
import { ClickerComponent } from './components/clicker/clicker.component';
import { FirstComponent } from './components/first/first.component';
import { HeroesComponent } from './components/heroes/heroes.component';
import { HomeComponent } from './components/home/home.component';
import { JaeComponent } from './components/jae/jae.component';
import { LoginComponent } from './components/login/login.component';
import { NavComponent } from './components/nav/nav.component';
import { NestedComponent } from './components/nested/nested.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { PipeComponent } from './components/pipe/pipe.component';
import { PokemonCardComponent } from './components/pokemon-card/pokemon-card.component';
import { PokemonComponent } from './components/pokemon/pokemon.component';
import { SecondComponent } from './components/second/second.component';
import { SpringComponent } from './components/spring/spring.component';
import { TicTacToeComponent } from './components/tic-tac-toe/tic-tac-toe.component';
import { UsersComponent } from './components/users/users.component';
import { AuthService } from './services/auth.service';
import { HttpClient } from 'selenium-webdriver/http';

@NgModule({
  declarations: [
    JaeComponent,
    AppComponent,
    FirstComponent,
    NavComponent,
    BlakeComponent,
    ClickerComponent,
    NotFoundComponent,
    HeroesComponent,
    HomeComponent,
    LoginComponent,
    SecondComponent,
    PipeComponent,
    NestedComponent,
    PokemonComponent,
    UsersComponent,
    TicTacToeComponent,
    SpringComponent,
    PokemonCardComponent
  ],
  imports: [
    HttpClient,
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
