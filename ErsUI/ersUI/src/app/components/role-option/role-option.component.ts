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

  insertTypeDrop = 'Select Type';

  currentTable: Reimb[];
  tableSubscription: Subscription;

  currentUpdate: Reimb[];

  updateDropSelected: Reimb;
  updateDropMessage = 'Select Reimbursement';

  updateStatusId: number;
  updateStatusDrop = 'Select Status';

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
      this.currentUpdate = reimbs.filter(reimb => {
        return reimb.reimbStatus === 'Pending' || reimb.reimbStatus === 'Requested';
      });

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


  // -- Employee Methods -- //
  callAdd() {
    console.log('User clicked it!');
    this.reimbService.addReimb( // params below
      this.currentUser,
      this.inputForm.reimbAmount,
      this.inputForm.reimbType,
      this.inputForm.reimbDesc);

    // reset values
    this.inputForm.reimbAmount = 0;
    this.inputForm.reimbType = 'Type';
    this.insertTypeDrop = 'Select Type';
    this.inputForm.reimbDesc = 'Description';
  }

  selectTypeDropdown(selected: string) {
    this.inputForm.reimbType = selected;
    this.insertTypeDrop = selected;
  }

  // -- Manager Methods -- //
  callUpdate() {
    if (this.updateDropSelected && this.updateStatusId) {
      this.updateServie.updateReimb(
        this.currentUser,
        this.updateDropSelected,
        this.updateStatusId);
    }

    // reset values
    this.updateDropSelected = undefined;
    this.updateStatusId = undefined;
    this.updateDropMessage = 'Select Reimbursement';
    this.updateStatusDrop = 'Select Status';
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
