package ohtu.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandsTest {

	Commands commands;

	@Test
	public void commandsTest() {
		commands = Commands.ADD;
		assertEquals('A', commands.getCommand());
	}
}
