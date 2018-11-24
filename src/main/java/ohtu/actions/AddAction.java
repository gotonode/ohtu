package ohtu.actions;

import ohtu.dao.BlogpostDao;
import ohtu.dao.BookmarkDao;
import ohtu.domain.Blogpost;
import ohtu.io.IO;
import ohtu.main.Main;
import ohtu.tools.Builder;
import ohtu.ui.UiController;

import java.util.Date;

public class AddAction extends Action {

	private UiController uiController;
	private BookmarkDao bookmarkDao;
	private BlogpostDao blogpostDao;

	public AddAction(IO io, UiController uiController, BookmarkDao bookmarkDao, BlogpostDao blogpostDao) {
		super(io);
		this.uiController = uiController;
		this.bookmarkDao = bookmarkDao;
		this.blogpostDao = blogpostDao;
	}

	/**
	 * Asks the user for title, author and url, and creates a new Blogpost by using Builder.
	 * Then tries to add the new Blogpost into the database. Tells the user if the operation succeeded or not.
	 */
	@Override
	public void act() {

		uiController.printEmptyLine();

		String[] values = uiController.askBlogpostData();
		String title = values[0]; // Care should be taken so as not to mix these indices up.
		String author = values[1];
		String url = values[2];

		// Database will handle assigning correct ID to the object, and decides the addDate value.
		Date date = null; // This is here just as a reference that it is null.
		int id = -1; // When creating new Bookmarks from user input, assign -1 as the ID.

		Blogpost newBlogpost = Builder.buildBlogpost(id, title, author, url, date);
		try {
			final Blogpost success = blogpostDao.create(newBlogpost);
			if (success != null) {
				uiController.addSuccess(newBlogpost.getClass().getSimpleName());
			} else {
				uiController.addFailure();
			}
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
		}

		uiController.printEmptyLine();

	}
}
