import { Injectable } from '@angular/core';
import { Reimb } from '../model/reimb.model';
import { ReplaySubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UpdateService {

  currentReimbStream = new ReplaySubject<Reimb>(1);
  $currentReimb = this.currentReimbStream.asObservable();

  constructor(private httpClient: HttpClient, private router: Router) {
    this.httpClient.get<Reimb>('http://localhost:8080/ERSProject/auth/session-user', {
      withCredentials: true
    }).subscribe(
      data => {
        if (data === null) {
          console.log('Cannot get the data');
          this.router.navigateByUrl('/reimbursements');
        } else {
          console.log('logged in');
          console.log(data);
          this.currentReimbStream.next(data);
        }
      },
      err => {
        console.log('not currently logged in');
      }
    );
  }
}
