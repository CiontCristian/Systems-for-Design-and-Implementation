import { Component, OnInit } from '@angular/core';
import {PurchasedService} from "../shared/purchased.service";
import {Purchased} from "../shared/purchased.model";

@Component({
  selector: 'app-purchased-list',
  templateUrl: './purchased-list.component.html',
  styleUrls: ['./purchased-list.component.css']
})
export class PurchasedListComponent implements OnInit {
  errorMessage: string;
  transactions: Array<Purchased>;

  constructor(private purchasedService: PurchasedService) { }

  ngOnInit(): void {
    this.purchasedService.getPurchased()
      .subscribe(
        purchased => this.transactions = purchased,
        error => this.errorMessage = <any>error
      );
  }

}
