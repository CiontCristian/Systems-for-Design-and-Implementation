import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-purchased',
  templateUrl: './purchased.component.html',
  styleUrls: ['./purchased.component.css']
})
export class PurchasedComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  addNewPurchased(){
    this.router.navigate(["purchased/new"]);
  }

}
