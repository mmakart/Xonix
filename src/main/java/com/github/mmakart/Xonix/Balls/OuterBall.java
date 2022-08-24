package com.github.mmakart.Xonix.Balls;

import com.github.mmakart.Xonix.GameState;
import com.github.mmakart.Xonix.Field.Cell;
import com.github.mmakart.Xonix.Field.CellType;

public class OuterBall extends Unit {

	public OuterBall(int x, int y, Direction direction) {
		super(x, y, direction);
	}

	// TODO handle collation with another ball
	@Override
	public void move(GameState gameState) {
		int tryX = x + direction.getToRight();
		int tryY = y + direction.getToDown();
		Cell[][] cells = gameState.getField().getCells();

		CellType type1, type2, type3;
		type1 = getCellType1(cells, tryY, x);
		type2 = getCellType2(cells, tryY, tryX);
		type3 = getCellType3(cells, y, tryX);

		if (type1 == CellType.OUTER && type2 == CellType.OUTER && type3 == CellType.OUTER) {
			// direction doesn't change
		} else if (type1 == CellType.OUTER && type2 == CellType.INNER && type3 == CellType.OUTER
				|| type1 == CellType.INNER && type3 == CellType.INNER) {
			direction.setToRight(-direction.getToRight());
			direction.setToDown(-direction.getToDown());
		} else if (type1 == CellType.INNER && type3 == CellType.OUTER) {
			direction.setToDown(-direction.getToDown());
		} else if (type1 == CellType.OUTER && type3 == CellType.INNER) {
			direction.setToRight(-direction.getToRight());
		} // else TODO handle collation with CellType.DRAWING

		x += direction.getToRight();
		y += direction.getToDown();
	}

	private CellType getCellType1(Cell[][] cells, int tryY, int x) {
		if (tryY < 0 || tryY >= cells.length) {
			return CellType.INNER;
		}
		if (cells[tryY][x].getCellType() != CellType.OUTER) {
			return CellType.INNER;
		}
		return CellType.OUTER;
	}

	private CellType getCellType2(Cell[][] cells, int tryY, int tryX) {
		if (tryY < 0 || tryY >= cells.length || tryX < 0 || tryX >= cells[0].length) {
			return CellType.INNER;
		}
		if (cells[tryY][tryX].getCellType() != CellType.OUTER) {
			return CellType.INNER;
		}
		return CellType.OUTER;
	}

	private CellType getCellType3(Cell[][] cells, int y, int tryX) {
		if (tryX < 0 || tryX >= cells[0].length) {
			return CellType.INNER;
		}
		if (cells[y][tryX].getCellType() != CellType.OUTER) {
			return CellType.INNER;
		}
		return CellType.OUTER;
	}

}