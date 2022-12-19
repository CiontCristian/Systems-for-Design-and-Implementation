import {Book} from "../../books/shared/book.model";
import {Client} from "../../clients/shared/client.model";

export class Purchased{
  id: number;
  book: Book;
  client: Client;
  date: Date;
}
