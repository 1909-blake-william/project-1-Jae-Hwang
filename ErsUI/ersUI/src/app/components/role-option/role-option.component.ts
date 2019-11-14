import { Component, OnInit, OnDestroy } from '@angular/core';
import { UpdateService } from 'src/app/services/update.service';
import { AuthService } from 'src/app/services/auth.service';
import { ReimbService } from 'src/app/services/reimb.service';
import { AppUser } from 'src/app/model/user.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-role-option',
  templateUrl: './role-option.component.html',
  styleUrls: ['./role-option.component.scss']
})
export class RoleOptionComponent implements OnInit, OnDestroy {
  inputForm = {
    reimbAmount: 0,
    reimbType: 'Type',
    reimbDesc: 'Description',
    message: 'Fill out the form then, click Request button.'
  };

  currentUser: AppUser;
  userSubscription: Subscription;

  insertSubscription: Subscription;

  constructor(
    private updateServie: UpdateService,
    private authService: AuthService,
    private reimbService: ReimbService) { }

  ngOnInit() {
    this.userSubscription = this.authService.$currentUser.subscribe(user => {
      this.currentUser = user;
    });
    this.insertSubscription = this.reimbService.$insertMessage.subscribe(msg => {
      this.inputForm.message = msg;
    });
  }

  callStuff() {
    console.log('User clicked it!');
    this.reimbService.addReimb( // params below
      this.currentUser,
      this.inputForm.reimbAmount,
      this.inputForm.reimbType,
      this.inputForm.reimbDesc);
  }

  ngOnDestroy() {
    if (this.userSubscription !== undefined) {
      this.userSubscription.unsubscribe();
    }
  }
}
