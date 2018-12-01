
package ohtu.dao;

import java.util.ArrayList;
import java.util.List;
import ohtu.database.Database;
import ohtu.domain.Book;


public class BookDao implements ObjectDao<Book, Integer> {

    private final Database database;
    
    /**
    * Creates a new BookDao object. The new BookDao communicates with the given
    * database and adds, edits and removes the data concerning Book-objects stored
    * in the database. 
     * @param database the database to be communicated with
    */
    public BookDao(Database database) {
        this.database = database;
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Book findById(Integer id) {
        return null;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return new ArrayList<>();
    }

    @Override
    public Book create(Book video) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean update(Book video) {
        return false;
    }

    public List<Book> findAllOrderByTitle() {
        return new ArrayList<>();
    }
    
}
