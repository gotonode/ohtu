
package ohtu.domain;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class VideoTest {
	private Video video;

	@Before
	public void setUp() {
		video = new Video(11, "Test title", new Date(2500), "test-url.com");
	}

	@Test
	public void isVideoReturnsTrue() {
		assertTrue(video.isVideo());
	}

	@Test
	public void construtorSetsIdOfRelatedBookmarkCorrectly() {
		assertEquals(11, video.getId());
	}

	@Test
	public void construtorSetsTitleOfRelatedBookmarkCorrectly() {
		assertEquals("Test title", video.getTitle());
	}

	@Test
	public void construtorSetsUrlCorrectly() {
		assertEquals("test-url.com", video.getUrl());
	}

	@Test
	public void construtorSetsDateCorrectly() {
		assertEquals(2500, video.getAddDate().getTime());
	}

	@Test
	public void construtorSetsTypeCorrectly() {
		String type = "V";
		assertEquals(type.charAt(0), video.getType());
	}

	@Test
	public void equalsReturnsFalseWhenWrongClass() {
		assertNotEquals(video, new Object());
	}

	@Test
	public void hashCodesNotEqual() {
		Video v = new Video(11, "Test title", new Date(2500), "a-test-url.com");
		assertNotEquals(v.hashCode(), video.hashCode());
	}

	@Test
	public void toStringReturnsCorrectString() {
		assertEquals("Video{url='test-url.com" + '\'' + '}', video.toString());
	}
}
