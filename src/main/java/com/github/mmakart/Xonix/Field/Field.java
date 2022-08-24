package com.github.mmakart.Xonix.Field;

public class Field {
	private Cell[][] cells;
	
	public Field(Cell[][] cells) {
		this.cells = cells;
	}
	
	public Cell[][] getCells() {
		return cells;
	}
	
	public int getWidth() {
		return cells[0].length;
	}
	
	public int getHeight() {
		return cells.length;
	}
}