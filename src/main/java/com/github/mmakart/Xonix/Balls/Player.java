package com.github.mmakart.Xonix.Balls;

import com.github.mmakart.Xonix.GameState;
import com.github.mmakart.Xonix.Field.Cell;
import com.github.mmakart.Xonix.Field.CellType;

public class Player extends Unit {
	
	public static final Direction STOP = new Direction(0, 0);
	public static final Direction TO_RIGHT = new Direction(1, 0);
	public static final Direction TO_LEFT = new Direction(-1, 0);
	public static final Direction TO_UP = new Direction(0, -1);
	public static final Direction TO_DOWN = new Direction(0, 1);
	
	public Player(int x, int y) {
		super(x, y);
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public void move(GameState gameState) {
		int tryX = x + direction.getToRight();
		int tryY = y + direction.getToDown();
		Cell[][] cells = gameState.getField().getCells();
		
		if (cells[y][x].getCellType() == CellType.DRAWING
				&& cells[tryY][tryX].getCellType() == CellType.OUTER) {
			fillCutArea(cells, x, y);
		}
		
		if (tryX >= 0 && tryX < cells[0].length) {
			x += direction.getToRight();
		}
		
		if (tryY >= 0 && tryY < cells.length) {
			y += direction.getToDown();
		}
		
		if (cells[y][x].getCellType() == CellType.INNER) {
			cells[y][x].setCellType(CellType.DRAWING);
		}
	}

	private void fillCutArea(Cell[][] cells, int x, int y) {
		// TODO Auto-generated method stub
		System.out.println("Mock filling " + x + " " + y);
	}
	
}