import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BooksComponent } from './books/books.component';
import { BookListComponent } from './books/book-list/book-list.component';
import {BookService} from "./books/shared/book.service";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { BookNewComponent } from './books/book-new/book-new.component';
import { BookDetailComponent } from './books/book-detail/book-detail.component';
import { ClientsComponent } from './clients/clients.component';
import {ClientService} from "./clients/shared/client.service";
import { ClientListComponent } from './clients/client-list/client-list.component';
import { ClientNewComponent } from './clients/client-new/client-new.component';
import { ClientDetailComponent } from './clients/client-detail/client-detail.component';
import { PurchasedComponent } from './purchased/purchased.component';
import { PurchasedNewComponent } from './purchased/purchased-new/purchased-new.component';
import { PurchasedListComponent } from './purchased/purchased-list/purchased-list.component';
import {PurchasedService} from "./purchased/shared/purchased.service";

@NgModule({
  declarations: [
    AppComponent,
    BooksComponent,
    BookListComponent,
    BookNewComponent,
    BookDetailComponent,
    ClientsComponent,
    ClientListComponent,
    ClientNewComponent,
    ClientDetailComponent,
    PurchasedComponent,
    PurchasedNewComponent,
    PurchasedListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [BookService, ClientService, PurchasedService],
  bootstrap: [AppComponent]
})
export class AppModule { }
