package minesweeper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.experimental.theories.Theories;

public class Settings implements Serializable {
	private final int rowCount;
	private final int columnCount;
	private final int mineCount;
	public static final Settings BEGINNER = new Settings(9, 9, 10);
	public static final Settings INTERMEDIATE = new Settings(16, 16, 40);
	public static final Settings EXPERT = new Settings(16, 30, 99);
	private static final String SETTING_FILE = System.getProperty("user.home") + System.getProperty("file.separator")
			+ "minesweeper.settings";

	public Settings(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
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

	@Override
	public boolean equals(Object o) {
		if(o instanceof Settings) {
			Settings oSettings = (Settings) o;
			return rowCount == oSettings.getRowCount() && columnCount == oSettings.getColumnCount() && mineCount == oSettings.mineCount;
		} else {
			return false;
		}	
	}

	public int hashCode() {
		return rowCount * columnCount * mineCount;
	}

	public void save() {
		try (ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(SETTING_FILE))) {
			ous.writeObject(this);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static Settings load() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SETTING_FILE))) {
			return (Settings) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return BEGINNER;
		}

	}

}
