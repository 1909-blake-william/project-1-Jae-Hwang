import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { ReimbService } from 'src/app/services/reimb.service';
import { AuthService } from 'src/app/services/auth.service';
import { AppUser } from 'src/app/model/user.model';

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.scss']
})
export class PagesComponent implements OnInit, OnDestroy {

  currentUser: AppUser;
  userSubscription: Subscription;

  page = 1;

  insertSubscription: Subscription;

  maxPage = 0;
  maxPageSubscription: Subscription;
  pageArray: number[];

  constructor(private reimbService: ReimbService, private authService: AuthService) { }


  ngOnInit() {
    this.userSubscription = this.authService.$currentUser.subscribe(user => {
      this.currentUser = user;
    });

    this.maxPageSubscription = this.reimbService.$maxPage.subscribe(p => {
      this.maxPage = p;
      this.pageArray = [];
      for (let i = 0; i < p; i++) {
        this.pageArray.push(i + 1);
      }
    });

    this.insertSubscription = this.reimbService.$insertMessage.subscribe( msg => {
      this.page = this.maxPage;
    });
  }

  ngOnDestroy() {
    if (this.maxPageSubscription !== undefined) {
      this.maxPageSubscription.unsubscribe();
    }

    if (this.userSubscription !== undefined) {
      this.userSubscription.unsubscribe();
    }
  }

  clickedPage(num: number) {
    if (this.page === num) {
      return 'page-item active';
    } else {
      return 'page-item';
    }
  }

  clickPage(num: number) {
    this.page = num;
    this.reimbService.getReimbs(this.currentUser, this.page);
  }

  pageUp() {
    if (this.page < this.maxPage) {
      this.page++;
      this.reimbService.getReimbs(this.currentUser, this.page);
    }
  }

  pageDown() {
    if (this.page > 1) {
      this.page--;
      this.reimbService.getReimbs(this.currentUser, this.page);
    }
  }
}
