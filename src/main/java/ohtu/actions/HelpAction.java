package ohtu.actions;

import ohtu.io.IO;
import ohtu.ui.UiController;

public class HelpAction extends Action {

	private UiController uiController;

	public HelpAction(IO io, UiController uiController) {
		super(io);
		this.uiController = uiController;
	}

	@Override
	public void act() {
		uiController.printInstructions();
	}
}
