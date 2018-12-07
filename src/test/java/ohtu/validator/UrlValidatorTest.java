package ohtu.validator;

import ohtu.tools.Validator;
import org.junit.Test;

import static org.junit.Assert.*;

public class UrlValidatorTest {

	@Test
	public void urlStartsWithProtocolHttpIsValid() {
		String url = "http://validurl.com";
		assertTrue(Validator.isValidUrl(url));
	}

	@Test
	public void urlStartsWithProtocolHttpsIsValid() {
		String url = "https://validurl.com";
		assertTrue(Validator.isValidUrl(url));
	}

	@Test
	public void urlStartsWithProtocolFileIsValid() {
		String url = "file://validurl.com";
		assertTrue(Validator.isValidUrl(url));
	}

	@Test
	public void urlStartsWithProtocolFtpIsValid() {
		String url = "ftp://validurl.com";
		assertTrue(Validator.isValidUrl(url));
	}

	@Test
	public void urlWithoutProtocolIsInvalid() {
		String invalidUrl = "invalidurl.html";
		assertFalse(Validator.isValidUrl(invalidUrl));
	}

}
