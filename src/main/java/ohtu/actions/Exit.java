package ohtu.actions;

import ohtu.io.IO;
import ohtu.ui.UiController;

public class Exit extends Action {

	private UiController uiController;

	public Exit(IO io, UiController uiController) {
		super(io);
		this.uiController = uiController;
	}

	@Override
	public void act() {
		uiController.printGoodbye();
	}
}
