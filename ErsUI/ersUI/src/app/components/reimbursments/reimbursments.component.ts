import { Component, OnInit } from '@angular/core';
import { Reimb } from 'src/app/model/reimb.model';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-reimbursments',
  templateUrl: './reimbursments.component.html',
  styleUrls: ['./reimbursments.component.scss']
})
export class ReimbursmentsComponent implements OnInit {

  reimbs: Reimb[] = [];

  constructor(private httpClient: HttpClient) { }

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
  }
}
