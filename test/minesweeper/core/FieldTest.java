package minesweeper.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class FieldTest {

	private static final int ROWS = 6;
	private static final int COLUMNS = 6;
	private static final int MINES = 6;

	@Test
	public void isSolved() {
		Field field = new Field(ROWS, COLUMNS, MINES);

		assertEquals(GameState.PLAYING, field.getState());

		int open = 0;
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column) instanceof Clue) {
					field.openTile(row, column);
					open++;
				}
				if (field.getRowCount() * field.getColumnCount() - open == field.getMineCount()) {
					assertEquals(GameState.SOLVED, field.getState());
				} else {
					assertNotSame(GameState.FAILED, field.getState());
				}
			}
		}

		assertEquals(GameState.SOLVED, field.getState());
	}

	@Test
	public void generate() {
		Field field = new Field(ROWS, COLUMNS, MINES);
		assertEquals(ROWS, field.getRowCount());
		assertEquals(COLUMNS, field.getColumnCount());
		assertEquals(MINES, field.getMineCount());

		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				assertNotNull(field.getTile(row, column));
			}
		}

		int mineCount = 0;
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column) instanceof Mine) {
					mineCount++;
				}
			}
		}

		assertEquals(MINES, mineCount);

		int clueCount = 0;
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column) instanceof Clue) {
					clueCount++;
				}
			}
		}

		assertEquals(ROWS * COLUMNS - MINES, clueCount);

	}
}
