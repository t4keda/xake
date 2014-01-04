package com.takeda.apps.chess.domain.pieces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.takeda.apps.chess.domain.Coordinate;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Caballo extends Pieza {

	@Override
	public List<Coordinate> getPosibleMovements(HashMap<String, Pieza> pieces) {
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();

		Coordinate new_coord = new Coordinate((position.getX() + 2),
				(position.getY() + 1));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		new_coord = new Coordinate((position.getX() - 2), (position.getY() + 1));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		new_coord = new Coordinate((position.getX() + 2), (position.getY() - 1));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		new_coord = new Coordinate((position.getX() - 2), (position.getY() - 1));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		new_coord = new Coordinate((position.getX() + 1), (position.getY() + 2));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		new_coord = new Coordinate((position.getX() + 1), (position.getY() - 2));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		new_coord = new Coordinate((position.getX() - 1), (position.getY() + 2));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		new_coord = new Coordinate((position.getX() - 1), (position.getY() - 2));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		return coords;
	}

	private boolean isValidCoord(Coordinate coord, HashMap<String, Pieza> pieces) {
		// esta dentro del tablero
		if (coord.getX() >= 0 && coord.getY() >= 0 && coord.getX() <= 7
				&& coord.getY() <= 7) {

			if (pieces.get(coord.getX() + "-" + coord.getY()) == null)
				return true;
			else {
				if (pieces.get(coord.getX() + "-" + coord.getY())
						.isBlack_piece() != black_piece)
					return true;
			}
		}

		return false;
	}

	@Override
	public void renderPiece(FrameLayout cell) {
		float density = cell.getContext().getResources().getDisplayMetrics().density;

		TextView t = new TextView(cell.getContext());
		t.setLayoutParams(new ViewGroup.LayoutParams(
				(int) (PIECE_WIDTH * density), (int) (PIECE_WIDTH * density)));
		t.setText("C");
		cell.addView(t);

	}
}
