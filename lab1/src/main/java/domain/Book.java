package domain;

import java.util.ArrayList;
import java.util.List;

public class Book extends BaseEntity<Long> {
    private String title;
    private String author;
    private double price;

    private List<Purchased> purchased;

    public Book(){
    }
    public Book(String _title, String _author, double _price){
        title=_title;
        author=_author;
        price=_price;
        purchased=new ArrayList<>();
    }

    public String getTitle(){return title;}
    public void setTitle(String newTitle){title=newTitle;}

    public String getAuthor(){return author;}
    public void setAuthor(String newAuthor){author=newAuthor;}

    public double getPrice(){return price;}
    public void setPrice(double newPrice){price=newPrice;}

    public List<Purchased> getPurchased() {return purchased;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (price != book.price) return false;
        if (!author.equals(book.author)) return false;
        return title.equals(book.title);

    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                "} " + super.toString();
    }
}
