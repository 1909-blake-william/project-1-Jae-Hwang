import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AppUser } from '../model/user.model';
import { ReplaySubject, Subject } from 'rxjs';
import { identifierModuleUrl } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUserStream = new ReplaySubject<AppUser>(1);
  $currentUser = this.currentUserStream.asObservable();

  private loginErrorStream = new Subject<string>();
  $loginError = this.loginErrorStream.asObservable();

  constructor(private httpClient: HttpClient, private router: Router) {
    this.httpClient.get<AppUser>('http://localhost:8080/ERSProject/auth/session-user', {
      withCredentials: true
    }).subscribe(
      data => {
        if (data === null) {
          console.log('not currently logged in');
          this.router.navigateByUrl('/login');
        } else {
          console.log('logged in');
          console.log(data);
          this.currentUserStream.next(data);
        }
      },
      err => {
        console.log('not currently logged in');
      }
    );
  }

  login(credentials) {
    this.httpClient.post<AppUser>('http://localhost:8080/ERSProject/auth/login', credentials, {
      withCredentials: true
    }).subscribe(
      data => {
        console.log('Logged in at Auth Service');
        this.router.navigateByUrl('/reimbursements');
        this.currentUserStream.next(data);
      },
      err => {
        console.log(err);
        this.loginErrorStream.next('Failed to Login');
      }
    );
  }

  logout() {
    this.currentUserStream.next(null);
    this.httpClient.post('http://localhost:8080/ERSProject/auth/logout', {
      withCredentials: true
    }).subscribe(
      data => {
        console.log('Logged out successfully');
      },
      err => {
        console.log(err);
      }
    );
  }


}
