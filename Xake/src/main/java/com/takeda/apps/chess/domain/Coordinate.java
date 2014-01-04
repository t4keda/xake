package com.takeda.apps.chess.domain;

public class Coordinate {

	public int x = 0;
	public int y = 0;

	public Coordinate(int i, int j) {
		x = i;
		y = j;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Coordinate))
			return false;
		return ((Coordinate) o).getX() == this.x
				&& ((Coordinate) o).getY() == this.y;
	}

}