package ohtu.validator;

import ohtu.tools.Validator;
import org.junit.Test;
import static org.junit.Assert.*;

public class UrlValidatorTest {

    @Test
    public void urlStartsWithProtocolIsValid() {
        String url1 = "https://validurl.com";
        String url2 = "ftp://validurl.com";
        String url3 = "file://validurl.com";
        assertTrue(Validator.isValidUrl(url1));
        assertTrue(Validator.isValidUrl(url2));
        assertTrue(Validator.isValidUrl(url3));
    }

    @Test
    public void urlWithoutProtocolIsInvalid() {
        String invalidUrl = "invalidurl.html";
        assertFalse(Validator.isValidUrl(invalidUrl));
    }

}
