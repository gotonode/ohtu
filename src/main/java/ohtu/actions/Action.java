package ohtu.actions;

import ohtu.io.IO;

/**
 * All commands that the user can give are to be handled by these Action classes.
 */
public abstract class Action {

	private IO io;

	Action(IO io) {
		this.io = io;
	}

	public IO getIo() {
		return io;
	}

	public abstract void act();
}
