package Scaryville2;

public class Coordinate {
	private int row;
	private int column;
	private char cordinateValue;

	public Coordinate(int row, int column, char cordinateValue) {
		this.row = row;
		this.column = column;
		this.cordinateValue = cordinateValue;
	}
	
	public Coordinate() {
		this.row = 0;
		this.column = 0;
		this.cordinateValue = ' ';
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public char getCordinateValue() {
		return cordinateValue;
	}
	public void setCordinateValue(char cordinateValue) {
		this.cordinateValue = cordinateValue;
	}
	
}
