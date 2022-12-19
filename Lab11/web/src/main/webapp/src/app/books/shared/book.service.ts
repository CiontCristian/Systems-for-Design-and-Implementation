import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Observable} from "rxjs";
import {Book} from "./book.model";
import {map} from "rxjs/operators";

@Injectable()
export class BookService {
  private booksURL = 'http://localhost:8080/api/books';

  constructor(private httpClient: HttpClient) {
  }

  getBooks(): Observable<Book[]>{
    return this.httpClient.get<Array<Book>>(this.booksURL);
  }

  getBook(id: number): Observable<Book>{
    return this.getBooks().pipe(map(books => books.find(book => book.id === id)));
  }

  saveBook(book: Book): Observable<Book>{
    console.log("saveBook", book);

    return this.httpClient.post<Book>(this.booksURL, book);
  }

  updateBook(book: Book): Observable<Book>{
    const url = `${this.booksURL}/${book.id}`;

    return this.httpClient.put<Book>(url, book);
  }

  deleteBook(id: number): Observable<any>{
    const url = `${this.booksURL}/${id}`;

    return this.httpClient.delete(url);
  }

  getSortedBooks(attributeName: any): Observable<Book[]>{
    return this.httpClient.post<Array<Book>>(this.booksURL+'/sort', attributeName);
  }

  getFilteredBooks(attributeName: any, value: any): Observable<Book[]>{
    return this.httpClient.post<Array<Book>>(this.booksURL+'/filter', [attributeName, value]);
  }
}
