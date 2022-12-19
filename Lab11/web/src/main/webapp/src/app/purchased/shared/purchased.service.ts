import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Purchased} from "./purchased.model";

@Injectable()
export class PurchasedService{
  private purchasedURL = 'http://localhost:8080/api/transactions';

  constructor(private httpClient: HttpClient) {
  }

  getPurchased(): Observable<Purchased[]> {
    return this.httpClient
      .get<Array<Purchased>>(this.purchasedURL + "/getTransactions");
  }

  savePurchased(purchased: Purchased): Observable<Purchased> {
    return this.httpClient
      .post<Purchased>(this.purchasedURL + "/saveTransaction", purchased);
  }
}
