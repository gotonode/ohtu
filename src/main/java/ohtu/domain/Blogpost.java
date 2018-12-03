package ohtu.domain;

import java.util.Date;
import java.util.Objects;

/**
 * A container for the Blogspot data.
 */
public class Blogpost extends Bookmark {

    private String author;
    private String url;
    
    /**
     * Creates a new Blogpost object with the given parameters as the corresponding 
     * values of the fields of the object.
     * 
     * @param id the id of the object to be created
     * @param title the title of the object to be created
     * @param addDate the date of creation of the object to be created
     * @param author the author of the object to be created
     * @param url the url of the object to be created
     */
    public Blogpost(int id, String title, Date addDate, String author, String url) {
        super(id, title, addDate, 'B'); // B here indicates a Blogpost.
        this.author = author;
        this.url = url;
    }

    @Override
    public String toString() {
            return "Blogpost{" +
                            "author='" + author + '\'' +
                            ", url='" + url + '\'' +
                            '}';
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (!this.getClass().equals(o.getClass())) {
            return false;
        }
        Blogpost compare = (Blogpost) o;
        return this.getUrl().equals(compare.getUrl());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.url);
        return hash;
    }


}
