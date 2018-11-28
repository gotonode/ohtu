package ohtu.actions;

import ohtu.dao.BlogpostDao;
import ohtu.dao.BookmarkDao;
import ohtu.dao.VideoDao;
import ohtu.domain.Blogpost;
import ohtu.domain.Bookmark;
import ohtu.domain.Video;
import ohtu.io.IO;
import ohtu.main.Main;
import ohtu.ui.UiController;

import java.sql.SQLException;

public class DeleteAction extends Action {

	private UiController uiController;
	private BookmarkDao bookmarkDao;
	private BlogpostDao blogpostDao;
	private VideoDao videoDao;

	public DeleteAction(IO io, UiController uiController, BookmarkDao bookmarkDao, BlogpostDao blogpostDao, VideoDao videoDao) {
		super(io);
		this.uiController = uiController;
		this.bookmarkDao = bookmarkDao;
		this.blogpostDao = blogpostDao;
		this.videoDao = videoDao;
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

		int id = uiController.askForIdToRemove();
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

		uiController.printDeleteConfirmation(bookmark.getId(), bookmark.getTitle(), bookmark.getClass().getSimpleName());

		char c = uiController.askForCharacter(new char[]{'Y', 'N'}, "Really delete");

		if (c == 'Y') {
			if (bookmark instanceof Blogpost) {
				try {
					boolean success = blogpostDao.delete(id);
					if (success) {
						uiController.printDeleteSuccessful(id);
					} else {
						uiController.printDeleteFailed(id);
					}
				} catch (SQLException e) {
					Main.LOG.warning(e.getMessage());
				}
			} else if (bookmark instanceof Video) {
				try {
					boolean success = videoDao.delete(id);
					if (success) {
						uiController.printDeleteSuccessful(id);
					} else {
						uiController.printDeleteFailed(id);
					}
					uiController.printDeleteSuccessful(id);
				} catch (SQLException e) {
					Main.LOG.warning(e.getMessage());
				}
			}
		} else {
			uiController.printAbortDelete();
		}

		uiController.printEmptyLine();

	}
}
