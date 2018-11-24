package ohtu.domain;

import java.util.Date;

/**
 * An abstract class used by Blogpost and others. Cannot be instantiated alone.
 */
public abstract class Bookmark {

    private final int id; // Can never be changed.
    private String title; // Can be changed.
    private Date addDate; // Can never be changed.
    private final char type; // Can never be changed.
    // We'll add "isRead" property later.

    /**
     * Create a new bookmark indirectly by calling the super.
     * @param id ID for the object (from the ohtu.database).
     * @param title Title for the object.
     * @param addDate When it was added to the ohtu.database (convert this to Java's Date).
     */
    Bookmark(int id, String title, Date addDate, char type) {
        this.id = id;
        this.title = title;
        this.addDate = addDate;
        this.type = type;
    }

    /**
     * We can check if this instance of Bookmark is a Blogpost.
     * @return True if it's a Blogpost, false otherwise.
     */
    public boolean isBlogpost() {
        return this instanceof Blogpost;
    }

    // We'll add the rest later (video, article, book, podcast).

    public int getId() {
        return id;
    }

    public Date getAddDate() {
        return addDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    // Override this on a per-object basis.
    public abstract String toString();
}
