package ohtu.tools;

import ohtu.dao.BlogpostDao;
import ohtu.dao.BookDao;
import ohtu.dao.BookmarkDao;
import ohtu.dao.VideoDao;
import ohtu.database.Database;

public class DaoBuilder {

    private DaoBuilder() {
        throw new IllegalStateException("Builder should not be instantiated.");
    }

    /**
     * Builds a new BlogpostDao
     *
     * @param db The database which BlogpostDao will use
     * @return the new BlogpostDao
     */
    public static BlogpostDao buildBlogpostDao(Database db) {
        return new BlogpostDao(db);
    }

    /**
     * Builds a new VideoDao
     *
     * @param db The database which VideoDao will use
     * @return the new VideoDao
     */
    public static VideoDao buildVideoDao(Database db) {
        return new VideoDao(db);
    }

    /**
     * Builds a new BookDao
     *
     * @param db The database which BookDao will use
     * @return the new BookDao
     */
    public static BookDao buildBookDao(Database db) {
        return new BookDao(db);
    }

    /**
     * Builds a new BokmarkDao
     *
     * @param db The database which BookmarkDao will use
     * @param blogpostDao The BlogpostDao which BookmarkDao will use when
     * listing and finding blogposts
     * @param videoDao The VideoDao which BookmarkDao will use when listing and
     * finding videos
     * @param bookDao The BookDao which BookmarkDao will use when listing and
     * finding books
     * @return
     */
    public static BookmarkDao buildBookmarkDao(Database db, BlogpostDao blogpostDao, VideoDao videoDao, BookDao bookDao) {
        return new BookmarkDao(db, blogpostDao, videoDao, bookDao);
    }
}
