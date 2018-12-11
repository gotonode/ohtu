package ohtu.actions;

import ohtu.dao.*;
import ohtu.domain.*;
import ohtu.io.IO;
import ohtu.main.Main;
import ohtu.ui.UiController;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.user.UserDbController;

public class DeleteAction extends Action {

	private UiController uiController;
	private BookmarkDao bookmarkDao;
	private BlogpostDao blogpostDao;
	private VideoDao videoDao;
	private BookDao bookDao;
	private UserDbController userDbController;

	public DeleteAction(IO io, UiController uiController, UserDbController userDbController, BookmarkDao bookmarkDao, BlogpostDao blogpostDao, VideoDao videoDao, BookDao bookDao) {
		super(io);
		this.uiController = uiController;
		this.bookmarkDao = bookmarkDao;
		this.blogpostDao = blogpostDao;
		this.videoDao = videoDao;
		this.bookDao = bookDao;
		this.userDbController = userDbController;
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

            try {
                // If the user tries to delete a Bookmark that he/she doesn't own, abort the delete operation.
                if (!userDbController.userOwnsBookmarkWithId(id)) {
                    uiController.printAccessDenied();
                    return;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DeleteAction.class.getName()).log(Level.SEVERE, null, ex);
            }

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

			boolean success = false;

			try {
				if (bookmark instanceof Blogpost) {
					success = blogpostDao.delete(id);
				} else if (bookmark instanceof Video) {
					success = videoDao.delete(id);
				} else if (bookmark instanceof Book) {
					success = bookDao.delete(id);
				}

				if (success) {
					uiController.printDeleteSuccessful(id);
				} else {
					uiController.printDeleteFailed(id);
				}

			} catch (SQLException e) {
				Main.LOG.warning(e.getMessage());
			}

		} else {
			uiController.printAbortDelete();
		}

		uiController.printEmptyLine();
	}
}
