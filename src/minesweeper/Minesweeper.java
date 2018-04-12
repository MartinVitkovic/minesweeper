package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.Tile;

/**
 * Main application class.
 */
public class Minesweeper {
	/** User interface. */
	private UserInterface userInterface;

	/**
	 * Constructooor.
	 */
	private Minesweeper() {
		userInterface = new ConsoleUI();

		Field field = new Field(8, 8, 6);
		userInterface.newGameStarted(field);
		System.out.println("Hra sa skoncila");
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            arguments
	 */
	public static void main(String[] args) {
		new Minesweeper();
	}
}
