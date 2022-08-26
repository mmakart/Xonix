package com.github.mmakart.Xonix.Balls;

import com.github.mmakart.Xonix.GameState;
import com.github.mmakart.Xonix.Field.Cell;
import com.github.mmakart.Xonix.Field.CellType;

public class InnerBall extends Unit {

	public InnerBall(int x, int y, Direction direction) {
		super(x, y, direction);
	}

	@Override
	public void move(GameState gameState) {
		if (direction.getToRight() == 0 && direction.getToDown() == 0) {
			return;
		}

		int tryX = x + direction.getToRight();
		int tryY = y + direction.getToDown();
		Cell[][] cells = gameState.getField().getCells();

		CellType type1 = getType1(cells, tryY, x);
		CellType type2 = getType2(cells, tryY, tryX);
		CellType type3 = getType3(cells, y, tryX);

		if (type1 == CellType.INNER && type2 == CellType.INNER && type3 == CellType.INNER) {
			// direction doesn't change
		} else if (type1 == CellType.INNER && type2 == CellType.OUTER && type3 == CellType.INNER
				|| type1 == CellType.OUTER && type3 == CellType.OUTER) {
			direction.setToRight(-direction.getToRight());
			direction.setToDown(-direction.getToDown());
		} else if (type1 == CellType.OUTER && type3 == CellType.INNER) {
			direction.setToDown(-direction.getToDown());
		} else if (type1 == CellType.INNER && type3 == CellType.OUTER) {
			direction.setToRight(-direction.getToRight());
		} else if (type1 == CellType.DRAWING || type2 == CellType.DRAWING || type3 == CellType.DRAWING) {
			gameState.setGameOver(true);
		}

		x += direction.getToRight();
		y += direction.getToDown();
	}

	private CellType getType1(Cell[][] cells, int tryY, int x) {
		if (tryY < 0 || tryY >= cells.length) {
			return CellType.OUTER;
		}
		return cells[tryY][x].getCellType();
	}

	private CellType getType2(Cell[][] cells, int tryY, int tryX) {
		if (tryY < 0 || tryY >= cells.length || tryX < 0 || tryX >= cells[0].length) {
			return CellType.OUTER;
		}
		return cells[tryY][tryX].getCellType();
	}

	private CellType getType3(Cell[][] cells, int y, int tryX) {
		if (tryX < 0 || tryX >= cells[0].length) {
			return CellType.OUTER;
		}
		return cells[y][tryX].getCellType();
	}

}