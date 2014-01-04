package com.takeda.apps.chess.domain.pieces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.takeda.apps.chess.domain.Coordinate;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Alfil extends Pieza {

	@Override
	public List<Coordinate> getPosibleMovements(HashMap<String, Pieza> pieces) {
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();

		int row = position.getX() - 1;
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
		t.setText("A");
		cell.addView(t);

	}
}
