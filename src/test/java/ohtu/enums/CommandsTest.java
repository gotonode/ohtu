package ohtu.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandsTest {

	@Test
	public void commandAddTest() {
		Commands commands = Commands.ADD;
		assertEquals('A', commands.getCommand());
	}

	@Test
	public void commandExitTest() {
		Commands commands = Commands.EXIT;
		assertEquals('E', commands.getCommand());
	}

	@Test
	public void commandModifyTest() {
		Commands commands = Commands.MODIFY;
		assertEquals('M', commands.getCommand());
	}

	@Test
	public void commandHelpTest() {
		Commands commands = Commands.HELP;
		assertEquals('X', commands.getCommand());
	}

	@Test
	public void commandDeleteTest() {
		Commands commands = Commands.DELETE;
		assertEquals('D', commands.getCommand());
	}

	@Test
	public void commandListTest() {
		Commands commands = Commands.LIST;
		assertEquals('L', commands.getCommand());
	}

	@Test
	public void commandSearchTest() {
		Commands commands = Commands.SEARCH;
		assertEquals('S', commands.getCommand());
	}
}
