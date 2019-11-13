import { Injectable } from '@angular/core';
import { Reimb } from '../model/reimb.model';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { ReplaySubject, Subscription } from 'rxjs';
import { AppUser } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class ReimbService {

  private currentReimbsStream = new ReplaySubject<Reimb[]>(1);
  $currentReimbs = this.currentReimbsStream.asObservable();

  constructor(private httpClient: HttpClient, private authService: AuthService) { }

  getReimbs(user: AppUser) {
    console.log(`User at Reimb Service: ${user}`);
    if (!user) {
      return console.log('Not logged in at Reimb Service.');
    } else {
      console.log('Logged in at Reimb Service');
    }
    if (user.role === 'Manager') {
      this.httpClient.get<Reimb[]>('http://localhost:8080/ERSProject/reimbursements', {
        withCredentials: true
      })
        .subscribe(data => {
          console.log(data);
          this.currentReimbsStream.next(data);
        }, err => {
          console.log(err);
        });
    } else {
      console.log(user.username);
      this.httpClient.get<Reimb[]>(`http://localhost:8080/ERSProject/reimbursements?username=${user.username}`, {
        withCredentials: true
      })
        .subscribe(data => {
          console.log(data);
          this.currentReimbsStream.next(data);
        }, err => {
          console.log(err);
        });
    }
  }
}
