package com.github.mmakart.Xonix.Balls;

import com.github.mmakart.Xonix.GameState;

public abstract class Unit {
	protected int x, y;
	protected Direction direction;

	public Unit(int x, int y) {
		this.x = x;
		this.y = y;
		this.direction = new Direction(0, 0);
	}

	public Unit(int x, int y, Direction direction) {
		this(x, y);
		this.direction = direction;
	}

	public abstract void move(GameState gameState);

	public Direction getDirection() {
		return direction;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}