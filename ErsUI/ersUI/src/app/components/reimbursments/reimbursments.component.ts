import { Component, OnInit, OnDestroy } from '@angular/core';
import { Reimb } from 'src/app/model/reimb.model';
import { HttpClient } from '@angular/common/http';
import { AuthService } from 'src/app/services/auth.service';
import { UpdateService } from 'src/app/services/update.service';
import { AppUser } from 'src/app/model/user.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-reimbursments',
  templateUrl: './reimbursments.component.html',
  styleUrls: ['./reimbursments.component.scss']
})
export class ReimbursmentsComponent implements OnInit, OnDestroy {

  reimbs: Reimb[] = [];

  currentUser: AppUser;
  userSubscription: Subscription;

  constructor(private httpClient: HttpClient, private authService: AuthService, private updateService: UpdateService) { }

  ngOnInit() {
    this.httpClient.get<Reimb[]>('http://localhost:8080/ERSProject/reimbursements', {
      withCredentials: true
    })
      .subscribe(data => {
        console.log(data);
        this.reimbs = data;
      }, err => {
        console.log(err);
      });

    // every time the subject publishes new content 
    // it will invoke the subscriber method
    this.userSubscription = this.authService.$currentUser.subscribe(user => {
      this.currentUser = user;
    });
  }

  ngOnDestroy() {
    this.userSubscription.unsubscribe();
  }

  update(reimb: Reimb) {
    this.httpClient.get<Reimb>(`http://localhost:8080/ERSProject/Reimbursement?id=${reimb.reimbId}`, {
      withCredentials: true
    }).subscribe(
      data => {
          console.log('logged in');
          console.log(data);
          this.updateService.currentReimbStream.next(data);
      },
      err => {
        console.log('failed to get the data.');
      }
    );
  }
}
