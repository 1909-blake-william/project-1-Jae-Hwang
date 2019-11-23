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

  private maxPageStream = new ReplaySubject<number>(1);
  $maxPage = this.maxPageStream.asObservable();
  maxPage: number;

  currentPage = 1;

  private insertMessageStream = new Subject<string>();
  $insertMessage = this.insertMessageStream.asObservable();

  constructor(private httpClient: HttpClient) { }

  getReimbs(user: AppUser, page: number) {
    console.log(`User at Reimb Service: ${user}`);
    if (!user) {
      return console.log('Not logged in at Reimb Service.');
    } else {
      console.log('Logged in at Reimb Service');
    }

    if (page === 0) {
      page = this.currentPage;
    } else {
      this.currentPage = page;
    }

    if (user.role === 'Manager') {
      let getUrl = `http://localhost:8080/ERSProject/fc/reimbursements`;
      getUrl = getUrl.concat(`?page=${page}`);
      this.httpClient.get<any>(getUrl, {
        withCredentials: true,
        observe: 'response'
      })
        .subscribe(data => {
          console.log(data.headers);
          console.log(data.headers.has('X-page'));
          console.log(data.headers.get('X-page'));
          console.log(data.body);
          this.currentReimbsStream.next(data.body);
          this.maxPage = parseInt(data.headers.get('X-page'), 0);
          this.maxPageStream.next(this.maxPage);
        }, err => {
          console.log(err);
        });
    } else {
      console.log(user.username);
      let getUrl = `http://localhost:8080/ERSProject/fc/reimbursements`;
      getUrl = getUrl.concat(`?username=${user.username}`);
      getUrl = getUrl.concat(`&page=${page}`);
      console.log(getUrl);
      this.httpClient.get<any>(getUrl, {
        withCredentials: true,
        observe: 'response'
      })
        .subscribe(data => {
          console.log(data.headers);
          console.log(data.headers.has('X-page'));
          console.log(data.headers.get('X-page'));
          console.log(data.body);
          this.currentReimbsStream.next(data.body);
          this.maxPage = parseInt(data.headers.get('X-page'), 0);
          this.maxPageStream.next(this.maxPage);
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
    if (type === 'Type') {
      message = `Please, select Type.`;
      this.insertMessageStream.next(message);
      return;
    }
    if (desc.length > 100) {
      message = 'Description is too long. Maximum size for Description is 100 characters.';
      this.insertMessageStream.next(message);
      return;
    }
    if (type === 'Other' && (desc.length < 3 || desc === 'Description')) {
      message = 'Please, describe "Other" expense.';
      this.insertMessageStream.next(message);
      return;
    }

    let requestUrl = `http://localhost:8080/ERSProject/fc/reimbursements`;
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
      this.getReimbs(user, this.maxPage);
    }, err => {
      console.log('Request failed.');
      console.log(err);
      message = 'Request failed, please report to the right person.';
      this.insertMessageStream.next(message);
    });
  }
}
