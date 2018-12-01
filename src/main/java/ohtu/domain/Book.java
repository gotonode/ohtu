
package ohtu.domain;

import java.util.Date;


public class Book extends Bookmark {
    private String author;
    private String isbn;

    public Book (int id, String title, Date addDate, String author, String isbn) {
        super(id, title, addDate, 'K'); // K for Book since B is taken.
        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "";
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    // TODO: write toString method and create missing equals and hascode methods
    // will get on this on monday
}
