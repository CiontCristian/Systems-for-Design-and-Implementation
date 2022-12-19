import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {BooksComponent} from "./books/books.component";
import {BookNewComponent} from "./books/book-new/book-new.component";
import {BookDetailComponent} from "./books/book-detail/book-detail.component";
import {ClientsComponent} from "./clients/clients.component";
import {ClientNewComponent} from "./clients/client-new/client-new.component";
import {ClientDetailComponent} from "./clients/client-detail/client-detail.component";
import {PurchasedComponent} from "./purchased/purchased.component";
import {PurchasedNewComponent} from "./purchased/purchased-new/purchased-new.component";


const routes: Routes = [
  {path:'books', component:BooksComponent},
  {path:'book/new', component:BookNewComponent},
  {path:'book/detail/:id', component:BookDetailComponent},

  {path:'clients', component:ClientsComponent},
  {path:'client/new', component:ClientNewComponent},
  {path:'client/detail/:id', component:ClientDetailComponent},

  {path:'purchased', component:PurchasedComponent},
  {path:'purchased/new', component:PurchasedNewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
