
package ohtu.domain;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class BookTest {
    private Book book;
    
    @Before
    public void setUp() {
        book = new Book(19, "Title to be tested", new Date(4003), "Test personnel", 
                "1-234-567-8910-11");
    }
    
    @Test
    public void construtorSetsIdOfRelatedBookmarkCorrectly() {
        assertEquals(19, book.getId());
    }

    @Test
    public void construtorSetsTitleOfRelatedBookmarkCorrectly() {
        assertEquals("Title to be tested", book.getTitle());
    }

    @Test
    public void construtorSetsAuthorCorrectly() {
        assertEquals("Test personnel", book.getAuthor());
    }

    @Test
    public void construtorSetsISBNCorrectly() {
        assertEquals("1-234-567-8910-11", book.getIsbn());
    }

    @Test
    public void construtorSetsDateCorrectly() {
        assertEquals(4003, book.getAddDate().getTime());
    }

    @Test
    public void construtorSetsTypeCorrectly() {
        String type = "K";
        assertEquals(type.charAt(0), book.getType());
    }

    @Test
    public void typeIsCorrect() {
        assertTrue(book.isBook());
    }

    @Test
    public void equalsReturnsFalseWhenWrongClass() {
        assertFalse(book.equals(new Object()));
    }

    @Test
    public void hashCodesNotEqual() {
        Book b2 = new Book(18, "Title to be tested", new Date(4003), "Test personnel", 
                "1-234-567-8910-13");
        assertNotEquals(b2.hashCode(), book.hashCode());
    }

    @Test
    public void toStringReturnsCorrectString() {
        assertEquals("Book{" + "author='" + book.getAuthor() + '\'' +
                        ", isbn='" + book.getIsbn() + '\'' + '}', book.toString());
    }
    
    @Test
    public void equalsReturnsTrueWithBookObjectWithSameISBN() {
        Book b2 = new Book(18, "A Title", new Date(4003), "Tester", 
                "1-234-567-8910-11");
        assertTrue(book.equals(b2));
    }
    
    @Test
    public void equalsReturnsFalseWithBookObjectWithDifferentISBN() {
        Book b2 = new Book(19, "Title to be tested", new Date(4003), "Test personnel", 
                "1-234-567-8910-13");
        assertFalse(book.equals(b2));
    }
}
