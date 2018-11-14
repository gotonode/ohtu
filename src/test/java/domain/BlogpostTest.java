package domain;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BlogpostTest {

	private Blogpost blogpost;

	@Before
	public void initialize() {
		blogpost = new Blogpost(0, "TITLE", Date.from(Instant.now()),1,  "AUTHOR", "URL");
	}

	@Test
	public void sampleTest() {
		assertEquals("AUTHOR", blogpost.getAuthor());
	}

	@Test
	public void typeIsCorrect() {
		assertTrue(blogpost.isBlogpost());
		/* We could also do something like:
		if (blogpost instanceof Blogpost) {

		}
		*/
	}
}
