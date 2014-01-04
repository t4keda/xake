package com.takeda.apps.chess.domain.pieces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.takeda.apps.chess.domain.Coordinate;

import android.widget.FrameLayout;

public abstract class Pieza {

	protected Coordinate position;
	protected boolean black_piece = false;
	protected static int PIECE_WIDTH = 20;

	public abstract List<Coordinate> getPosibleMovements(
			HashMap<String, Pieza> pieces);

	public abstract void renderPiece(FrameLayout cell);

	public boolean isBlack_piece() {
		return black_piece;
	}

	public void setBlack_piece(boolean black_piece) {
		this.black_piece = black_piece;
	}

	public Coordinate getPosition() {
		return position;
	}

	public void setPosition(Coordinate position) {
		this.position = position;
	}

	public void notificateMove() {
	}

	public boolean canIEat(HashMap<String, Pieza> pieces, Coordinate coord) {
		ArrayList<Coordinate> coords = (ArrayList<Coordinate>) getPosibleMovements(pieces);
		if (coords.contains(coord))
			return true;
		else
			return false;
	}

}
