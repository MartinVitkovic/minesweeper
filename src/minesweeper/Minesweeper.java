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
	private Settings setting;

	/**
	 * Constructor.
	 */
	private Minesweeper() {
		instance = this;
		userInterface = new ConsoleUI();
		Settings loadSetting = Settings.load();
		Field field = new Field(loadSetting.getRowCount(), loadSetting.getColumnCount(), loadSetting.getMineCount());
		userInterface.newGameStarted(field);
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

	private Settings getSetting() {
		return setting;
	}

	private void setSetting(Settings setting) {
		this.setting = setting;
		setting.save();
	}
}
