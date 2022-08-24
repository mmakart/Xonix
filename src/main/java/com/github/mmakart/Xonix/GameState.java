package com.github.mmakart.Xonix;

import java.util.List;

import com.github.mmakart.Xonix.Balls.InnerBall;
import com.github.mmakart.Xonix.Balls.OuterBall;
import com.github.mmakart.Xonix.Balls.Player;
import com.github.mmakart.Xonix.Field.Cell;
import com.github.mmakart.Xonix.Field.CellType;
import com.github.mmakart.Xonix.Field.Field;

public class GameState {

	private final Field field;
	private final Player player;
	private final List<InnerBall> innerBalls;
	private final List<OuterBall> outerBalls;
	private boolean isGameOver = false;
	
	public GameState(int fieldWidth,
			int fieldHeight,
			Player player,
			List<InnerBall> innerBalls,
			List<OuterBall> outerBalls) {
		super();
		this.player = player;
		this.innerBalls = innerBalls;
		this.outerBalls = outerBalls;
		this.field = initField(fieldWidth, fieldHeight);
	}
	
	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}
	
	public Field getField() {
		return field;
	}

	public Player getPlayer() {
		return player;
	}

	public List<InnerBall> getInnerBalls() {
		return innerBalls;
	}

	public List<OuterBall> getOuterBalls() {
		return outerBalls;
	}

	public Field initField(final int width, final int height) {
		Cell[][] cells = new Cell[height][width];
		
		for (int i = 0; i < width; i++) {
			cells[0][i] = new Cell(CellType.OUTER);
			cells[1][i] = new Cell(CellType.OUTER);
			cells[height - 1][i] = new Cell(CellType.OUTER);
			cells[height - 2][i] = new Cell(CellType.OUTER);
		}
		
		for (int i = 0; i < height; i++) {
			cells[i][0] = new Cell(CellType.OUTER);
			cells[i][1] = new Cell(CellType.OUTER);
			cells[i][width - 1] = new Cell(CellType.OUTER);
			cells[i][width - 2] = new Cell(CellType.OUTER);
		}
		
		for (int i = 2; i < height - 2; i++) {
			for (int j = 2; j < width - 2; j++) {
				cells[i][j] = new Cell(CellType.INNER);
			}
		}
		// TODO remove
//		cells[2][7] = new Cell(CellType.DRAWING);
		
		return new Field(cells);
	}
}
