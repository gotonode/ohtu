package ohtu.actions;

import ohtu.io.IO;

public class Exit extends Action {

	public Exit(IO io) {
		super(io);
	}

	@Override
	public void act() {
		super.getIo().println("Thanks for using " + ohtu.main.Main.APP_NAME + ".");
	}
}
