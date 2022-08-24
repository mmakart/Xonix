package com.github.mmakart.Xonix.Balls;

public class Direction {
	private int toDown, toRight;
	
	public Direction(int toRight, int toDown) {
		this.toRight = toRight;
		this.toDown = toDown;
	}

	public void setToDown(int toDown) {
		this.toDown = toDown;
	}

	public void setToRight(int toRight) {
		this.toRight = toRight;
	}

	public int getToDown() {
		return toDown;
	}

	public int getToRight() {
		return toRight;
	}
}
