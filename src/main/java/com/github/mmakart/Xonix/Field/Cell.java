package com.github.mmakart.Xonix.Field;

public class Cell {
	CellType cellType;	
	
	public Cell(CellType cellType) {
		super();
		this.cellType = cellType;
	}
	
	public CellType getCellType() {
		return cellType;
	}

	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}
}