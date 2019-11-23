import { Injectable } from '@angular/core';
import { Reimb } from '../model/reimb.model';
import { ReplaySubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AppUser } from '../model/user.model';
import { ReimbService } from './reimb.service';

@Injectable({
  providedIn: 'root'
})
export class UpdateService {

  currentUpdateStream = new ReplaySubject<Reimb>(1);
  $currentUpdate = this.currentUpdateStream.asObservable();

  constructor(private httpClient: HttpClient, private router: Router, private reimbService: ReimbService) { }

  updateReimb(user: AppUser, reimb: Reimb, status: number) {
    let requestUrl = `http://localhost:8080/ERSProject/fc/reimbursements`;
    requestUrl = requestUrl.concat(`?resolver=${user.userId}`);
    requestUrl = requestUrl.concat(`&id=${reimb.reimbId}`);
    requestUrl = requestUrl.concat(`&status=${status}`);
    console.log(requestUrl);

    this.httpClient.put(requestUrl, {
      withCredentials: true
    }).subscribe(data => {
      console.log('Successfully updated.');
      this.reimbService.getReimbs(user, 0);
    }, err => {
      console.log('Update failed.');
    });
  }
}
