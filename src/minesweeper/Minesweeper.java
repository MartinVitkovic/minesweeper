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
	private long startMillis = System.currentTimeMillis();
	private BestTimes bestTimes = new BestTimes();
	private static Minesweeper instance;

	/**
	 * Constructooor.
	 */
	private Minesweeper() {
		instance = this;
		userInterface = new ConsoleUI();

		Field field = new Field(8, 8, 6);
		userInterface.newGameStarted(field);
		System.out.println("Hra sa skoncila");
	}

	public int getPlayingSeconds() {
		return ((int) (System.currentTimeMillis() - startMillis) / 1000);
	}

	public BestTimes getBestTimes() {
		return bestTimes;
	}

	public static Minesweeper getInstance() {
		if (instance == null) {
			instance = new Minesweeper();
		}
		return instance;
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
