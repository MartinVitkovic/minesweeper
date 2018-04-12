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

	/*
	 * field.getRowCount(); row++) { for (int col = 0; col < field.getColumnCount();
	 * col++) { Tile tile = field.getTile(row, col); if (tile.getState() ==
	 * Tile.State.OPEN) { if (tile instanceof Clue) { System.out.print(((Clue)
	 * tile).getValue()); } else { System.out.print("X"); } } else if
	 * (tile.getState() == Tile.State.MARKED) { System.out.print("M"); } else {
	 * System.out.print("X"); } } System.out.println(); } }
	 */

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
