package minesweeper.core;

import java.util.Random;

import minesweeper.core.Tile.State;

/**
 * Field represents playing field and game logic.
 */
public class Field {
	/**
	 * Playing field tiles.
	 */
	private final Tile[][] tiles;

	/**
	 * Field row count. Rows are indexed from 0 to (rowCount - 1).
	 */
	private final int rowCount;

	/**
	 * Column count. Columns are indexed from 0 to (columnCount - 1).
	 */
	private final int columnCount;

	/**
	 * Mine count.
	 */
	private final int mineCount;

	/**
	 * Game state.
	 */
	private GameState state = GameState.PLAYING;

	/**
	 * Constructor.
	 *
	 * @param rowCount
	 *            row count
	 * @param columnCount
	 *            column count
	 * @param mineCount
	 *            mine count
	 */
	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];

		// printArray(tiles);
		// generate the field content
		generate();
	}

	/**
	 * Opens tile at specified indeces.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.OPEN);
			if (tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			}
			if (tile instanceof Clue) {
				if ((((Clue) tile).getValue()) == 0) {
					openAdjacentTiles(row, column);
				}
			}

			if (isSolved()) {
				state = GameState.SOLVED;
				return;
			}
		}
	}

	/**
	 * Marks tile at specified indeces.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void markTile(int row, int column) {
		// throw new UnsupportedOperationException("Method markTile not yet
		// implemented");
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.MARKED);
		} else if (tile.getState() == Tile.State.MARKED) {
			tile.setState(Tile.State.CLOSED);
		}
	}

	/**
	 * Generates playing field.
	 */
	private void generate() {
		generateMines();
		fillWithClues();
	}

	/**
	 * Fill playing field with Mines.
	 */
	private void generateMines() {
		Random random = new Random();
		int minesToSet = mineCount;

		while (minesToSet > 0) {
			int row = random.nextInt(rowCount);
			int column = random.nextInt(columnCount);
			if (getTile(row, column) == null) {
				tiles[row][column] = new Mine();
				minesToSet--;
			}
		}
	}

	/**
	 * Fill playing field with Clues.
	 */
	private void fillWithClues() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == null) {
					tiles[row][column] = new Clue(countAdjacentMines(row, column));
				}
			}
		}
	}

	/**
	 * Returns true if game is solved, false otherwise.
	 *
	 * @return true if game is solved, false otherwise
	 */
	private boolean isSolved() {
		return (((columnCount * rowCount) - getNumberOf(Tile.State.OPEN)) == getMineCount());
	}

	/**
	 * Returns count of fields in state.
	 * 
	 * @param state
	 *            state of field.
	 * @return count
	 */

	private int getNumberOf(Tile.State state) {
		// TODO Auto-generated method stub
		int count = 0;
		for (int row = 0; row < getRowCount(); row++) {
			for (int column = 0; column < getColumnCount(); column++) {
				if (tiles[row][column].getState() == state) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Returns number of adjacent mines for a tile at specified position in the
	 * field.
	 *
	 * @param row
	 *            row number.
	 * @param column
	 *            column number.
	 * @return number of adjacent mines.
	 */
	private int countAdjacentMines(int row, int column) {
		int count = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Mine) {
							count++;
						}
					}
				}
			}
		}

		return count;
	}

	private void openAdjacentTiles(int row, int column) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {

						// otvor ak clue = 0
						if (tiles[actRow][actColumn].getState() == Tile.State.CLOSED) {
							if (tiles[actRow][actColumn] instanceof Clue) {
								Clue clue = (Clue) tiles[actRow][actColumn];
								if ((clue.getValue()) == 0) {
									openTile(actRow, actColumn);
								}
							}
						}
					}
				}
			}
		}
	}

	public int getRemainingMineCount() {
		return mineCount - getNumberOf(State.MARKED);
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

}
