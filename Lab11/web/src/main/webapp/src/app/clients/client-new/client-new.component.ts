import { Component, OnInit } from '@angular/core';
import {ClientService} from "../shared/client.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-client-new',
  templateUrl: './client-new.component.html',
  styleUrls: ['./client-new.component.css']
})
export class ClientNewComponent implements OnInit {

  constructor(private clientService: ClientService,
              private location: Location) { }

  ngOnInit(): void {
  }

  saveClient(firstName: string, lastName: string, age: string) {

    this.clientService.saveClient({
      id: null,
      firstName,
      lastName,
      age:+age
    })
      .subscribe(client => console.log("saved client: ", client));

    this.location.back();
  }
}
