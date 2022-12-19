import { Component, OnInit } from '@angular/core';
import {ClientService} from "../shared/client.service";
import {Router} from "@angular/router";
import {Client} from "../shared/client.model";

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {
  errorMessage: string;
  clients: Array<Client>;
  selectedClient: Client;

  constructor(private clientService: ClientService,
              private router: Router) { }

  ngOnInit(): void {
    this.getClients();
  }

  getClients() {
    this.clientService.getClients()
      .subscribe(
        clients => this.clients = clients,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(client: Client): void {
    this.selectedClient = client;
    sessionStorage.setItem("client", JSON.stringify(this.selectedClient));
  }

  gotoDetail(): void {
    this.router.navigate(['/client/detail', this.selectedClient.id]);
  }

  deleteClient(client: Client) {
    this.clientService.deleteClient(client.id)
      .subscribe(_ => {
        console.log("client deleted");

        this.clients = this.clients
          .filter(c => c.id !== client.id);
      });
  }

}
