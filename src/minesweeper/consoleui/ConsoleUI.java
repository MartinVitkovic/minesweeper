package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import minesweeper.Minesweeper;
import minesweeper.UserInterface;
import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Tile;
import minesweeper.consoleui.WrongFormatException;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
	/** Playing field. */
	private Field field;

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * minesweeper.consoleui.UserInterface#newGameStarted(minesweeper.core.Field)
	 */
	@Override
	public void newGameStarted(Field field) {
		this.field = field;
		do {
			update();
			processInput();

			if (field.getState() == GameState.SOLVED) {
				System.out.println("Hra vyhrata");
				System.exit(0);
			}

			if (field.getState() == GameState.FAILED) {
				update();
				System.out.println("Hra prehrata");
				System.exit(0);
			}

		} while (true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#update()
	 */
	@Override
	public void update() {
		Minesweeper minesweeper = Minesweeper.getInstance();
		System.out.printf("  ");
		for (int i = 0; i < field.getColumnCount(); i++) {
			System.out.printf("%d ", i + 1);
		}
		System.out.println();
		for (int row = 0; row < field.getRowCount(); row++) {
			System.out.printf("%c ", row + 65);
			for (int col = 0; col < field.getColumnCount(); col++) {
				Tile tile = field.getTile(row, col);
				switch (tile.getState()) {
				case CLOSED:
					System.out.print("- ");
					break;
				case MARKED:
					System.out.print("M ");
					break;
				case OPEN:
					if (tile instanceof Clue) {
						System.out.print(((Clue) tile).getValue() + " ");
					} else {
						System.out.print("X ");
					}
					break;
				}
			}
			System.out.println();
		}
		System.out.println("Pocet neoznacenych min: " + field.getRemainingMineCount());
		System.out.println("Aktualny hraci cas: " + minesweeper.getPlayingSeconds());
	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */
	private void processInput() {

		System.out.println("X – ukoncenie hry\n" + "MA1 – oznacenie dlazdice v riadku A a stlpci 1\n"
				+ "OB4 – odkrytie dlazdice v riadku B a stlpci 4");
		String input = readLine().trim().toUpperCase();
		try {
			handleInput(input);
		} catch (WrongFormatException ex) {
			// TODO: handle exception
			System.out.println(ex.getMessage());
		}

	}

	private void handleInput(String input) throws WrongFormatException {

		Pattern p = Pattern.compile("(O|M)([A-I])([0-8])");
		Matcher matcher = p.matcher(input);

		if (input.equals("X")) {
			System.exit(0);
		} else if (matcher.matches()) {
			String command = matcher.group(1);
			String row = matcher.group(2);
			String column = matcher.group(3);

			int rowOfField = row.charAt(0) - 65;
			int columnOfField = Integer.parseInt(column);

			// if ((rowOfField < field.getRowCount()) || (rowOfField > field.getRowCount()))
			// {
			// throw new WrongFormatException("Zadal si zly pocet riadkov");
			// }
			//
			// if ((columnOfField < field.getColumnCount()) || columnOfField >
			// field.getColumnCount()) {
			// throw new WrongFormatException("Zadal si zly pocet stlpcov");
			// }

			// try {
			if (command.equals("M")) {
				field.markTile(rowOfField, columnOfField - 1);
			} else if (command.equals("O")) {
				field.openTile(rowOfField, columnOfField - 1);
			}

		} else {
			throw new WrongFormatException("Zadal si zly vstup");
		}
	}

}
