import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ReplaySubject, Subject } from 'rxjs';
import { Reimb } from '../model/reimb.model';
import { AppUser } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class ReimbService {

  private currentReimbsStream = new ReplaySubject<Reimb[]>(1);
  $currentReimbs = this.currentReimbsStream.asObservable();

  private insertMessageStream = new Subject<string>();
  $insertMessage = this.insertMessageStream.asObservable();

  private availableType = ['Lodging', 'Food', 'Travel', 'Other'];

  constructor(private httpClient: HttpClient) { }

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

  addReimb(user: AppUser, amount: number, type: string, desc: string) {
    let message = '';
    if (amount <= 0) {
      message = 'Please, Enter valid amount for reimbursements.';
      this.insertMessageStream.next(message);
      return;
    }
    if (!this.availableType.includes(type)) {
      message = `'${type}' is not valid reimbursement type. Please enter one of following:  [  `;
      this.availableType.forEach(element => {
        message = message.concat(`${element}  `);
      });
      message = message.concat(']');
      console.log(message);
      this.insertMessageStream.next(message);
      return;
    }
    if (desc.length > 100) {
      message = 'Description is too long. Maximum size for Description is 100 characters.';
      this.insertMessageStream.next(message);
      return;
    }

    let requestUrl = `http://localhost:8080/ERSProject/reimbursements`;
    requestUrl = requestUrl.concat(`?author=${user.username}`);
    requestUrl = requestUrl.concat(`&amount=${amount}`);
    requestUrl = requestUrl.concat(`&type=${type}`);
    requestUrl = requestUrl.concat(`&desc=${desc}`);
    console.log(requestUrl);

    this.httpClient.post(requestUrl, {
      withCredentials: true
    }).subscribe(data => {
      console.log('Successfully added.');
      message = 'Successfully added reimbursement';
      this.insertMessageStream.next(message);
      this.getReimbs(user);
    }, err => {
      console.log('Request failed.');
      console.log(err);
      message = 'Request failed, please report to the right person.';
      this.insertMessageStream.next(message);
    });
  }
}
