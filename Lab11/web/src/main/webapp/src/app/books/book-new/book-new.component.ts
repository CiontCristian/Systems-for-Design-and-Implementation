import { Component, OnInit } from '@angular/core';
import {BookService} from "../shared/book.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-book-new',
  templateUrl: './book-new.component.html',
  styleUrls: ['./book-new.component.css']
})
export class BookNewComponent implements OnInit {

  constructor(private bookService: BookService,
                private  location: Location) { }

  ngOnInit(): void {
  }

  saveBook(title: string, author: string, price: string){
    console.log("saving student", title, author, price);

    this.bookService.saveBook({id: null, title, author, price: +price}).subscribe(book => console.log("saved book: ", book));

    this.location.back();
  }

}
