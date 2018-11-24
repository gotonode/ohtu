package ohtu.actions;

import ohtu.dao.BlogpostDao;
import ohtu.dao.BookmarkDao;
import ohtu.domain.Blogpost;
import ohtu.domain.Bookmark;
import ohtu.io.IO;
import ohtu.main.Main;
import ohtu.ui.UiController;

import java.sql.SQLException;

public class ModifyAction extends Action {

	private UiController uiController;
	private BookmarkDao bookmarkDao;
	private BlogpostDao blogpostDao;

	public ModifyAction(IO io, UiController uiController, BookmarkDao bookmarkDao, BlogpostDao blogpostDao) {
		super(io);
		this.uiController = uiController;
		this.bookmarkDao = bookmarkDao;
		this.blogpostDao = blogpostDao;
	}

	@Override
	public void act() {

		uiController.printEmptyLine();

		try {
			if (bookmarkDao.findAll().isEmpty()) {
				uiController.printNoBookmarks();
				uiController.printEmptyLine();
				return;
			}

		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
			return;
		}

		int id = uiController.askForIdToModify();
		Bookmark bookmark;
		try {
			bookmark = bookmarkDao.findById(id);
		} catch (Exception e) {
			Main.LOG.warning(e.getMessage());
			return;
		}

		if (bookmark == null) {
			uiController.printBookmarkNotFound();
			uiController.printEmptyLine();
			return;
		}

		super.getIo().println("Enter new values. Leave blank to use current value.");

		super.getIo().println("Current title: " + bookmark.getTitle());
		String newTitle = uiController.askForString("New title: ", true);
		if (!newTitle.isEmpty()) {
			bookmark.setTitle(newTitle);
		}

		boolean success = false;

		if (bookmark instanceof Blogpost) {
			try {
				success = blogpostDao.update((Blogpost)bookmark);
			} catch (SQLException e) {
				Main.LOG.warning(e.getMessage());
				return;
			}
		}

		if (success) {
			super.getIo().println("Successfully updated your bookmark.");
		} else {
			super.getIo().println("Couldn't update your bookmark. Please try again.");
		}

		uiController.printEmptyLine();
	}
}
