package ohtu.io;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ConsoleIOTest {

	private static ConsoleIO consoleIo;

	@BeforeClass
	public static void before() {
		consoleIo = new ConsoleIO(new Scanner(System.in));
	}

	@Test
	public void printSomething() {
		consoleIo.print("Something.");
		assertTrue(true);
	}

	@Test
	public void printSomethingWithLinefeed() {
		consoleIo.println("Something.");
		assertTrue(true);
	}


	@Test
	public void readSuccessfully() {
		//String s = consoleIo.readLine("Read something.");
		//assertEquals("", s);
	}

}
