package ohtu.domain;

import org.junit.Before;
import org.junit.Test;
import java.util.Date;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class BlogpostTest {

	private Blogpost blogpost;

	@Before
	public void initialize() {
            blogpost = new Blogpost(9, "A Matter of Testing", new Date(1005), 
                    "A test coder", "a-test-blog/tests.com");
	}

	@Test
	public void construtorSetsIdOfRelatedBookmarkCorrectly() {
            assertEquals(9, blogpost.getId());
	}
        
        @Test
        public void construtorSetsTitleOfRelatedBookmarkCorrectly() {
            assertEquals("A Matter of Testing", blogpost.getTitle());
	}
        
        @Test
        public void construtorSetsAuthorCorrectly() {
            assertEquals("A test coder", blogpost.getAuthor());
	}
        
        @Test
        public void construtorSetsUrlCorrectly() {
            assertEquals("a-test-blog/tests.com", blogpost.getUrl());
	}
        
        @Test
        public void construtorSetsDateCorrectly() {
            assertEquals(1005, blogpost.getAddDate().getTime());
	}
        
        @Test
        public void construtorSetsTypeCorrectly() {
            String type = "B";
            assertEquals(type.charAt(0), blogpost.getType());
	}

	@Test
	public void typeIsCorrect() {
            assertTrue(blogpost.isBlogpost());
	}
        
        @Test
        public void equalsReturnsFalseWhenWrongClass() {
            assertFalse(blogpost.equals(new Date()));
        }
        
        @Test
        public void hashCodesNotEqual() {
            Blogpost b2 = new Blogpost(9, "A Matter of Testing", new Date(1005), 
                    "A test coder", "b-test-blog/tests.com");
            assertNotEquals(b2.hashCode(), blogpost.hashCode());
        }
        
        @Test
        public void toStringReturnsCorrectString() {
            assertEquals("Blogpost{author='A test coder" + '\''+ ", url='" + 
                    "a-test-blog/tests.com" + '\''+ "}", blogpost.toString());
        }
}
