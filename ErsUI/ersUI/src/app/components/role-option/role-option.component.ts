import { Component, OnInit, OnDestroy } from '@angular/core';
import { UpdateService } from 'src/app/services/update.service';
import { AuthService } from 'src/app/services/auth.service';
import { ReimbService } from 'src/app/services/reimb.service';
import { AppUser } from 'src/app/model/user.model';
import { Subscription } from 'rxjs';
import { Reimb } from 'src/app/model/reimb.model';

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

  currentTable: Reimb[];
  tableSubscription: Subscription;

  updateDropSelected: Reimb;
  updateDropMessage = 'Select Reimbursement';

  updateStatusDrop = 'Select Status';
  updateStatusId: number;

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
    this.tableSubscription = this.reimbService.$currentReimbs.subscribe(reimbs => {
      this.currentTable = reimbs;
    });
  }

  ngOnDestroy() {
    if (this.userSubscription !== undefined) {
      this.userSubscription.unsubscribe();
    }

    if (this.insertSubscription !== undefined) {
      this.insertSubscription.unsubscribe();
    }

    if (this.tableSubscription !== undefined) {
      this.tableSubscription.unsubscribe();
    }
  }

  callAdd() {
    console.log('User clicked it!');
    this.reimbService.addReimb( // params below
      this.currentUser,
      this.inputForm.reimbAmount,
      this.inputForm.reimbType,
      this.inputForm.reimbDesc);
  }

  callUpdate() {
    if (this.updateDropSelected && this.updateStatusId) {
      this.updateServie.updateReimb(this.currentUser, this.updateDropSelected, this.updateStatusId);
    }
  }

  selectUpdateDropdown(selected: Reimb) {
    this.updateDropMessage = `${selected.reimbId} : ${selected.reimbAmount} by ${selected.reimbAuthor}`;
    this.updateDropSelected = selected;
  }

  selectStatusDropdown(selected: number) {
    this.updateStatusId = selected;
    switch (selected) {
      case 1:
        this.updateStatusDrop = 'Requested';
        return;

      case 2:
        this.updateStatusDrop = 'Pending';
        return;

      case 3:
        this.updateStatusDrop = 'Denied';
        return;

      case 4:
        this.updateStatusDrop = 'Approved';
        return;

      default:
        console.log('Something broke...');
        return;
    }
  }
}
