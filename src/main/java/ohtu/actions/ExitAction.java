package ohtu.actions;

import ohtu.io.IO;
import ohtu.ui.UiController;

public class ExitAction extends Action {

	private UiController uiController;

	public ExitAction(IO io, UiController uiController) {
		super(io);
		this.uiController = uiController;
	}

	@Override
	public void act() {
		uiController.printEmptyLine();
		uiController.printGoodbye();
	}
}
