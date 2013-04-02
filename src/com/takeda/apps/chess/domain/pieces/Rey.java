package com.takeda.apps.chess.domain.pieces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.takeda.apps.chess.domain.Coordinate;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Rey extends Pieza {

	@Override
	public List<Coordinate> getPosibleMovements(HashMap<String, Pieza> pieces) {
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();

		// Diagonales
		Coordinate new_coord = new Coordinate((position.getX() + 1),
				(position.getY() + 1));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		new_coord = new Coordinate((position.getX() + 1), (position.getY() - 1));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		new_coord = new Coordinate((position.getX() - 1), (position.getY() + 1));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		new_coord = new Coordinate((position.getX() - 1), (position.getY() - 1));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		// normales
		new_coord = new Coordinate((position.getX() + 1), (position.getY()));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		new_coord = new Coordinate((position.getX() - 1), (position.getY()));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		new_coord = new Coordinate((position.getX()), (position.getY() - 1));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		new_coord = new Coordinate((position.getX()), (position.getY() + 1));

		if (isValidCoord(new_coord, pieces)) {
			coords.add(new_coord);
		}

		return coords;
	}

	private boolean isValidCoord(Coordinate coord, HashMap<String, Pieza> pieces) {
		// esta dentro del tablero
		if (coord.getX() >= 0 && coord.getY() >= 0 && coord.getX() <= 7
				&& coord.getY() <= 7) {

			if (pieces.get(coord.getX() + "-" + coord.getY()) == null
					|| (pieces.get(coord.getX() + "-" + coord.getY())
							.isBlack_piece() != black_piece)) {
				// Mirar si no se lo puede comer nadie
				if (!imInJaque(coord, pieces))
					return true;
			}
		}

		// FIXME FALTA MIRAR EL MOVIMIENTO CON REYES
		return false;
	}

	private boolean imInJaque(Coordinate coord, HashMap<String, Pieza> pieces) {

		boolean jaque = false;

		for (String key : pieces.keySet()) {

			// ignoramos las de mismo color
			if (pieces.get(key).isBlack_piece() == black_piece)
				continue;

			if (pieces.get(key).canIEat(pieces, coord)) {
				Log.v("bleh", key);
				jaque = true;
				break;
			}
		}

		return jaque;
	}

	@Override
	public void renderPiece(FrameLayout cell) {
		float density = cell.getContext().getResources().getDisplayMetrics().density;

		TextView t = new TextView(cell.getContext());
		t.setLayoutParams(new ViewGroup.LayoutParams(
				(int) (PIECE_WIDTH * density), (int) (PIECE_WIDTH * density)));
		t.setText("R");
		cell.addView(t);

	}

	@Override
	public boolean canIEat(HashMap<String, Pieza> pieces, Coordinate coord) {
		// Diagonales
		Coordinate new_coord = new Coordinate((position.getX() + 1),
				(position.getY() + 1));
		if (new_coord.equals(coord))
			return true;

		new_coord = new Coordinate((position.getX() + 1), (position.getY() - 1));
		if (new_coord.equals(coord))
			return true;

		new_coord = new Coordinate((position.getX() - 1), (position.getY() + 1));
		if (new_coord.equals(coord))
			return true;

		new_coord = new Coordinate((position.getX() - 1), (position.getY() - 1));
		if (new_coord.equals(coord))
			return true;

		// normales
		new_coord = new Coordinate((position.getX() + 1), (position.getY()));
		if (new_coord.equals(coord))
			return true;

		new_coord = new Coordinate((position.getX() - 1), (position.getY()));
		if (new_coord.equals(coord))
			return true;

		new_coord = new Coordinate((position.getX()), (position.getY() - 1));
		if (new_coord.equals(coord))
			return true;

		new_coord = new Coordinate((position.getX()), (position.getY() + 1));
		if (new_coord.equals(coord))
			return true;

		return false;
	}
}
