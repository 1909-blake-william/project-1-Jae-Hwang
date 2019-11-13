import { Component, OnInit, OnDestroy } from '@angular/core';
import { Reimb } from 'src/app/model/reimb.model';
import { AuthService } from 'src/app/services/auth.service';
import { AppUser } from 'src/app/model/user.model';
import { Subscription } from 'rxjs';
import { ReimbService } from 'src/app/services/reimb.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reimbursments',
  templateUrl: './reimbursments.component.html',
  styleUrls: ['./reimbursments.component.scss']
})
export class ReimbursmentsComponent implements OnInit, OnDestroy {

  currentUser: AppUser;
  userSubscription: Subscription;

  currentTable: Reimb[];
  tableSubscription: Subscription;

  constructor(private authService: AuthService, private reimbService: ReimbService, private router: Router) { }

  ngOnInit() {
    // every time the subject publishes new content
    // it will invoke the subscriber method
    this.userSubscription = this.authService.$currentUser.subscribe(user => {
      this.currentUser = user;
    });

    console.log(`User at Reimb Components: ${this.currentUser}`);
    if (!this.currentUser) {
      console.log('Not logged in at Reimbursement Component');
      this.router.navigateByUrl('/login');
    } else {
      console.log('Logged in at Reimbursement Component');
      console.log(this.currentUser);
    }

    this.reimbService.getReimbs(this.currentUser);

    this.tableSubscription = this.reimbService.$currentReimbs.subscribe(reimbs => {
      this.currentTable = reimbs;
    });
  }

  ngOnDestroy() {
    if (this.userSubscription !== undefined) {
      this.userSubscription.unsubscribe();
    }
    if (this.tableSubscription !== undefined) {
      this.tableSubscription.unsubscribe();
    }
  }
}
