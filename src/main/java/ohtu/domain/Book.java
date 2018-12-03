
package ohtu.domain;

import java.util.Date;
import java.util.Objects;


public class Book extends Bookmark {
    private String author;
    private String isbn;

    /**
     * Creates a new Book object with the given parameters as the values of 
     * the corresponding fields of the object.
     * @param id the id of the object to be created
     * @param title the title of the object to be created
     * @param addDate the date of creation of the object to be created
     * @param author the author of the object to be created
     * @param isbn the isbn of the object to be created
     */
    public Book (int id, String title, Date addDate, String author, String isbn) {
        super(id, title, addDate, 'K'); // K for Book since B is taken by Blogpost.
        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{" + "author='" + author + '\'' +
                        ", isbn='" + isbn + '\'' + '}';
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
    
    @Override
    public boolean equals(Object object) {
        if (!this.getClass().equals(object.getClass())) {
            return false;
        }

        return this.getIsbn().equals(((Book)object).getIsbn());
    }

    @Override
    public int hashCode() {
        int hash = 21;
        hash = 19 * hash + Objects.hashCode(this.isbn);
        
        return hash;
    }
}
