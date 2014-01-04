package com.takeda.apps.chess.domain.pieces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.takeda.apps.chess.domain.Coordinate;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Reina extends Pieza {

	@Override
	public List<Coordinate> getPosibleMovements(HashMap<String, Pieza> pieces) {
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();

		int row = position.getY() + 1;

		// buscamos para los laterales
		while (row <= 7) {
			if (pieces.get(position.getX() + "-" + row) == null) {
				coords.add(new Coordinate(position.getX(), row));
			} else {
				// el primero que encuentra se lo puede comer y si paramos
				if (black_piece != pieces.get(position.getX() + "-" + row)
						.isBlack_piece())
					coords.add(new Coordinate(position.getX(), row));
				break;
			}
			row++;
		}

		row = position.getY() - 1;
		while (row >= 0) {
			if (pieces.get(position.getX() + "-" + row) == null) {
				coords.add(new Coordinate(position.getX(), row));
			} else {
				// el primero que encuentra se lo puede comer y si paramos
				if (black_piece != pieces.get(position.getX() + "-" + row)
						.isBlack_piece())
					coords.add(new Coordinate(position.getX(), row));
				break;
			}
			row--;
		}

		// buscamos en vertical
		row = position.getX() + 1;
		while (row <= 7) {
			if (pieces.get(row + "-" + position.getY()) == null) {
				coords.add(new Coordinate(row, position.getY()));
			} else {
				// el primero que encuentra se lo puede comer y si paramos
				if (black_piece != pieces.get(row + "-" + position.getY())
						.isBlack_piece())
					coords.add(new Coordinate(row, position.getY()));
				break;
			}
			row++;
		}

		row = position.getX() - 1;
		while (row >= 0) {
			if (pieces.get(row + "-" + position.getY()) == null) {
				coords.add(new Coordinate(row, position.getY()));
			} else {
				// el primero que encuentra se lo puede comer y si paramos
				if (black_piece != pieces.get(row + "-" + position.getY())
						.isBlack_piece())
					coords.add(new Coordinate(row, position.getY()));
				break;
			}
			row--;
		}

		// diagonales
		row = position.getX() - 1;
		int cell = position.getY() - 1;

		while (row >= 0 && cell >= 0) {
			if (pieces.get(row + "-" + cell) == null) {
				coords.add(new Coordinate(row, cell));
			} else {
				if (black_piece != pieces.get(row + "-" + cell).isBlack_piece())
					coords.add(new Coordinate(row, cell));
				break;
			}

			row--;
			cell--;
		}

		row = position.getX() - 1;
		cell = position.getY() + 1;

		while (row >= 0 && cell <= 7) {
			if (pieces.get(row + "-" + cell) == null) {
				coords.add(new Coordinate(row, cell));
			} else {
				if (black_piece != pieces.get(row + "-" + cell).isBlack_piece())
					coords.add(new Coordinate(row, cell));
				break;
			}

			row--;
			cell++;
		}

		row = position.getX() + 1;
		cell = position.getY() - 1;

		while (row <= 7 && cell >= 0) {
			if (pieces.get(row + "-" + cell) == null) {
				coords.add(new Coordinate(row, cell));
			} else {
				if (black_piece != pieces.get(row + "-" + cell).isBlack_piece())
					coords.add(new Coordinate(row, cell));
				break;
			}

			row++;
			cell--;
		}

		row = position.getX() + 1;
		cell = position.getY() + 1;

		while (row <= 7 && cell <= 7) {
			if (pieces.get(row + "-" + cell) == null) {
				coords.add(new Coordinate(row, cell));
			} else {
				if (black_piece != pieces.get(row + "-" + cell).isBlack_piece())
					coords.add(new Coordinate(row, cell));
				break;
			}

			row++;
			cell++;
		}

		return coords;
	}

	@Override
	public void renderPiece(FrameLayout cell) {
		float density = cell.getContext().getResources().getDisplayMetrics().density;

		TextView t = new TextView(cell.getContext());
		t.setLayoutParams(new ViewGroup.LayoutParams(
				(int) (PIECE_WIDTH * density), (int) (PIECE_WIDTH * density)));
		t.setText("r");
		cell.addView(t);

	}
}
