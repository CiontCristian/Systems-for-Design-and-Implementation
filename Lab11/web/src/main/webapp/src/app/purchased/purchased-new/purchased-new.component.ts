import { Component, OnInit } from '@angular/core';
import {PurchasedService} from "../shared/purchased.service";
import {Location} from "@angular/common";
import {Book} from "../../books/shared/book.model";
import {Client} from "../../clients/shared/client.model";

@Component({
  selector: 'app-purchased-new',
  templateUrl: './purchased-new.component.html',
  styleUrls: ['./purchased-new.component.css']
})
export class PurchasedNewComponent implements OnInit {

  book: Book = JSON.parse(sessionStorage.getItem("book"));
  client: Client = JSON.parse(sessionStorage.getItem("client"));

  constructor(private purchasedService: PurchasedService,
              private location: Location) { }

  ngOnInit(): void {
  }

  savePurchased(date: string){
    this.purchasedService.savePurchased({
      id:null,
      book: this.book,
      client: this.client,
      date: new Date(date)
    }).subscribe(purchased => console.log("saved tran: ", purchased));

    sessionStorage.removeItem("book");
    sessionStorage.removeItem("client");

    this.location.back();
  }

}
