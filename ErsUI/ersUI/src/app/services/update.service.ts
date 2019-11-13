import { Injectable } from '@angular/core';
import { Reimb } from '../model/reimb.model';
import { ReplaySubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UpdateService {

  currentUpdateStream = new ReplaySubject<Reimb>(1);
  $currentUpdate = this.currentUpdateStream.asObservable();

  constructor(private httpClient: HttpClient, private router: Router) {

  }

  setUpdate(reimb: Reimb): Reimb[] {
    this.httpClient.get<Reimb>(`http://localhost:8080/ERSProject/reimbursements?id=${reimb.reimbId}`, {
      withCredentials: true
    }).subscribe(
      data => {
        console.log('logged in');
        console.log(data);
        this.currentUpdateStream.next(data);
        return data;
      },
      err => {
        console.log('failed to get the data.');
        return null;
      }
    );
    return null;
  }
}
