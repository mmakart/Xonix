package com.github.mmakart.Xonix.Balls;

import java.util.Arrays;
import java.util.List;

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
		Player player = gameState.getPlayer();

		if (cells[y][x].getCellType() == CellType.DRAWING && cells[tryY][tryX].getCellType() == CellType.OUTER) {
			fillCutArea(gameState);
			player.setDirection(STOP);
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

	private void fillCutArea(GameState gameState) {
		boolean foundLeft = false;
		boolean foundRight = false;
		boolean foundUp = false;
		boolean foundDown = false;

		Cell[][] cells = gameState.getField().getCells();
		List<InnerBall> innerBalls = gameState.getInnerBalls();

		boolean[][] visited = new boolean[cells.length][cells[0].length];

		// assumption that direction.getToRight() == 0
		if (direction.getToDown() != 0) {
			foundLeft = areThereInnerBallsInArea(visited, cells, x - 1, y, innerBalls);

			for (int i = 0; i < visited.length; i++) {
				for (int j = 0; j < visited[0].length; j++) {
					visited[i][j] = false;
				}
			}

			foundRight = areThereInnerBallsInArea(visited, cells, x + 1, y, innerBalls);

			if (foundLeft && foundRight) {
				fillDrawingCells(cells);
			} else if (foundLeft) {
				fillArea(cells, x + 1, y);
			} else if (foundRight) {
				fillArea(cells, x - 1, y);
			} else {
				fillArea(cells, x - 1, y);
				fillArea(cells, x + 1, y);
			}
		} else /* assumption that direction.getToDown() == 0 */ {
			foundUp = areThereInnerBallsInArea(visited, cells, x, y - 1, innerBalls);

			for (int i = 0; i < visited.length; i++) {
				for (int j = 0; j < visited[0].length; j++) {
					visited[i][j] = false;
				}
			}

			foundDown = areThereInnerBallsInArea(visited, cells, x, y + 1, innerBalls);

			if (foundUp && foundDown) {
				fillDrawingCells(cells);
			} else if (foundUp) {
				fillArea(cells, x, y + 1);
			} else if (foundDown) {
				fillArea(cells, x, y - 1);
			}
		}

		fillDrawingCells(cells);
	}

	private void fillArea(Cell[][] cells, int x, int y) {
		if (cells[y][x].getCellType() == CellType.DRAWING) {
			cells[y][x].setCellType(CellType.OUTER);
			return;
		}

		if (cells[y][x].getCellType() == CellType.OUTER) {
			return;
		}

		if (cells[y][x].getCellType() == CellType.INNER) {
			cells[y][x].setCellType(CellType.OUTER);
			fillArea(cells, x, y - 1);
			fillArea(cells, x + 1, y);
			fillArea(cells, x, y + 1);
			fillArea(cells, x - 1, y);
		}
	}

	private void fillDrawingCells(Cell[][] cells) {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				if (cells[i][j].getCellType() == CellType.DRAWING) {
					cells[i][j].setCellType(CellType.OUTER);
				}
			}
		}
	}

	private boolean areThereInnerBallsInArea(boolean[][] visited, Cell[][] cells, int x, int y,
			List<InnerBall> innerBalls) {
		CellType type = cells[y][x].getCellType();

		if (y < 0 || y >= cells.length || x < 0 || x >= cells[0].length) {
			return false;
		}

		if (visited[y][x] || type == CellType.OUTER || type == CellType.DRAWING) {
			return false;
		}

		for (InnerBall innerBall : innerBalls) {
			if (innerBall.getX() == x && innerBall.getY() == y) {
				return true;
			}
		}

		visited[y][x] = true;

		return areThereInnerBallsInArea(visited, cells, x, y - 1, innerBalls)
				|| areThereInnerBallsInArea(visited, cells, x - 1, y, innerBalls)
				|| areThereInnerBallsInArea(visited, cells, x, y + 1, innerBalls)
				|| areThereInnerBallsInArea(visited, cells, x + 1, y, innerBalls);
	}

}