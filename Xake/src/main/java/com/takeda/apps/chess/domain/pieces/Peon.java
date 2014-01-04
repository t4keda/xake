package com.takeda.apps.chess.domain.pieces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.takeda.apps.chess.domain.Coordinate;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Peon extends Pieza {

	private boolean first_movement = true;

	@Override
	public List<Coordinate> getPosibleMovements(HashMap<String, Pieza> pieces) {
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();

		int next_row;
		if (black_piece) {
			next_row = position.getX() + 1;
		} else
			next_row = position.getX() - 1;

		if (next_row <= 7) // no debería llegar nunca a 8
			if (pieces.get(next_row + "-" + position.getY()) == null) {
				coords.add(new Coordinate(next_row, position.getY()));
			}

		if (first_movement) {
			if (black_piece) {
				if (pieces.get((next_row + 1) + "-" + position.getY()) == null) {
					coords.add(new Coordinate((next_row + 1), position.getY()));
				}
			} else {
				if (pieces.get((next_row - 1) + "-" + position.getY()) == null) {
					coords.add(new Coordinate((next_row - 1), position.getY()));
				}
			}
		}

		// miramos si puede comer
		if (pieces.get((next_row) + "-" + (position.getY() + 1)) != null) {
			if (pieces.get((next_row) + "-" + (position.getY() + 1))
					.isBlack_piece() != black_piece)
				coords.add(new Coordinate(next_row, (position.getY() + 1)));
		}

		if (pieces.get((next_row) + "-" + (position.getY() - 1)) != null) {
			if (pieces.get((next_row) + "-" + (position.getY() - 1))
					.isBlack_piece() != black_piece)
				coords.add(new Coordinate(next_row, (position.getY() - 1)));
		}

		return coords;
	}

	@Override
	public void renderPiece(FrameLayout cell) {
		float density = cell.getContext().getResources().getDisplayMetrics().density;

		TextView t = new TextView(cell.getContext());
		t.setLayoutParams(new ViewGroup.LayoutParams(
				(int) (PIECE_WIDTH * density), (int) (PIECE_WIDTH * density)));
		t.setText("P");
		cell.addView(t);

	}

	@Override
	public void notificateMove() {
		first_movement = false;
	}

	@Override
	public boolean canIEat(HashMap<String, Pieza> pieces, Coordinate coord) {
		int next_row;
		if (black_piece) {
			next_row = position.getX() + 1;
		} else
			next_row = position.getX() - 1;

		if (coord.equals(new Coordinate(next_row, (position.getY() + 1))))
			return true;
		if (coord.equals(new Coordinate(next_row, (position.getY() - 1))))
			return true;

		return false;
	}
}
