package com.takeda.apps.chess.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import com.takeda.apps.chess.R;
import com.takeda.apps.chess.domain.pieces.Alfil;
import com.takeda.apps.chess.domain.pieces.Caballo;
import com.takeda.apps.chess.domain.Coordinate;
import com.takeda.apps.chess.domain.pieces.Peon;
import com.takeda.apps.chess.domain.pieces.Pieza;
import com.takeda.apps.chess.domain.pieces.Reina;
import com.takeda.apps.chess.domain.pieces.Rey;
import com.takeda.apps.chess.domain.pieces.Torre;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

@SuppressLint("InlinedApi")
public class BoardFragment extends Fragment {

	private static int CELL_WIDTH = 40;
	private static int CELL_PADDING = 10;
	private LinearLayout root_view;
	private HashMap<String, Pieza> pieces;
	private boolean moving = false;
	private ArrayList<Coordinate> movements;
	private Pieza movingPiece;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root_view = (LinearLayout) inflater.inflate(R.layout.fragment_board,
				null);
		paintBoard();
		initializeBoard(); // FIXME esto si hay partida guardada hay que
							// cambiarlo
		return root_view;
	}

	@Override
	public void onStart() {
		super.onStart();
		redrawBoard();
	}

	private void redrawBoard() {
		clearBoard();

		Pieza p;
		TableLayout table = (TableLayout) root_view
				.findViewById(R.id.chess_board_layout);
		TableRow r;
		FrameLayout c;

		for (String coord : pieces.keySet()) {
			p = pieces.get(coord);
			r = (TableRow) table.getChildAt(p.getPosition().getX());
			c = (FrameLayout) r.getChildAt(p.getPosition().getY());
			p.renderPiece(c);
			// Cogemos la celda que toca
		}

		if (moving) {
			for (Coordinate co : movements) {
				((ViewGroup) table.getChildAt(co.getX()))
						.getChildAt(co.getY())
						.setBackgroundColor(
								table.getContext()
										.getResources()
										.getColor(
												android.R.color.holo_orange_light));
			}
		}
	}

	private void clearBoard() {
		TableLayout table = (TableLayout) root_view
				.findViewById(R.id.chess_board_layout);
		TableRow r;
		FrameLayout c;
		for (int i = 0; i < table.getChildCount(); i++) {
			r = (TableRow) table.getChildAt(i);
			for (int j = 0; j < r.getChildCount(); j++) {
				c = (FrameLayout) r.getChildAt(j);
				c.removeAllViews();

				int color;
				if (i % 2 == 0) {
					if (j % 2 == 0)
						color = getResources().getColor(android.R.color.white);
					else
						color = getResources().getColor(android.R.color.black);
				} else {
					if (j % 2 == 0)
						color = getResources().getColor(android.R.color.black);
					else
						color = getResources().getColor(android.R.color.white);
				}
				// 00 es la primera de arriba a la izquierda, 01 la siguiente,
				// etc
				c.setBackgroundColor(color);
			}
		}
	}

	private void paintBoard() {

		TableLayout table = (TableLayout) root_view
				.findViewById(R.id.chess_board_layout);
		TableRow row;
		FrameLayout cell;
		TableRow.LayoutParams row_params = new TableRow.LayoutParams();
		row_params.height = TableRow.LayoutParams.WRAP_CONTENT;

		float density = getResources().getDisplayMetrics().density;

		TableRow.LayoutParams cell_params = new TableRow.LayoutParams(
				(int) (CELL_WIDTH * density), (int) (CELL_WIDTH * density));
		// cell_params.gravity = Gravity.CENTER;

		for (int row_count = 0; row_count < 8; row_count++) {
			row = new TableRow(root_view.getContext());
			row.setLayoutParams(row_params);
			row.setOrientation(TableRow.HORIZONTAL);
			row.setBackgroundColor(getResources().getColor(
					android.R.color.black));

			for (int cell_row = 0; cell_row < 8; cell_row++) {
				cell = new FrameLayout(root_view.getContext());
				cell.setLayoutParams(cell_params);
				cell.setPadding((int) (CELL_PADDING * density),
						(int) (CELL_PADDING * density),
						(int) (CELL_PADDING * density),
						(int) (CELL_PADDING * density));
				int color;
				if (cell_row % 2 == 0) {
					if (row_count % 2 == 0)
						color = getResources().getColor(android.R.color.white);
					else
						color = getResources().getColor(android.R.color.black);
				} else {
					if (row_count % 2 == 0)
						color = getResources().getColor(android.R.color.black);
					else
						color = getResources().getColor(android.R.color.white);
				}
				// 00 es la primera de arriba a la izquierda, 01 la siguiente,
				// etc
				cell.setId(Integer.valueOf(Integer.valueOf(row_count)
						.toString() + Integer.valueOf(cell_row).toString()));
				cell.setBackgroundColor(color);
				cell.setTag(row_count + "-" + cell_row);
				cell.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						handleEvent(v);
					}
				});
				row.addView(cell);
			}

			table.addView(row, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}

		table.invalidate();
	}

	private void handleEvent(View v) {

		// Si no hay ficha fuera
		if (((FrameLayout) v).getChildCount() == 0) {
			// FIXME cuando hay que mover
			String[] pos = ((String) v.getTag()).split("-");
			Coordinate c = new Coordinate(Integer.valueOf(pos[0]),
					Integer.valueOf(pos[1]));
			if (moving && this.movements.contains(c)) {
				movePiece(c);
			} else
				moving = false;
		} else {
			moving = true;
			movingPiece = pieces.get((String) v.getTag());
			// TODO que si la ficha no es mia también sude

			this.movements = (ArrayList<Coordinate>) movingPiece
					.getPosibleMovements(pieces);
		}
		redrawBoard();
	}

	private void movePiece(Coordinate cord) {
		Coordinate oldCord = movingPiece.getPosition();
		movingPiece.setPosition(cord);
		pieces.remove(oldCord.getX() + "-" + oldCord.getY());
		pieces.put(cord.getX() + "-" + cord.getY(), movingPiece);

		movingPiece.notificateMove();
		// TODO mirar si comes

		// Limpiamos variables
		movingPiece = null;
		movements = null;
		moving = false;
		System.gc();
	}

	private void initializeBoard() {
		Coordinate coord;
		Pieza p;
		pieces = new HashMap<String, Pieza>();

		// creamos las negras

		// Torres
		coord = new Coordinate(0, 0);
		p = new Torre();
		p.setPosition(coord);
		p.setBlack_piece(true);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		coord = new Coordinate(0, 7);
		p = new Torre();
		p.setPosition(coord);
		p.setBlack_piece(true);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		// Caballos
		coord = new Coordinate(0, 1);
		p = new Caballo();
		p.setPosition(coord);
		p.setBlack_piece(true);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		coord = new Coordinate(0, 6);
		p = new Caballo();
		p.setPosition(coord);
		p.setBlack_piece(true);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		// Alfiles
		coord = new Coordinate(0, 2);
		p = new Alfil();
		p.setPosition(coord);
		p.setBlack_piece(true);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		coord = new Coordinate(0, 5);
		p = new Alfil();
		p.setPosition(coord);
		p.setBlack_piece(true);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		// Reina
		coord = new Coordinate(0, 3);
		p = new Reina();
		p.setPosition(coord);
		p.setBlack_piece(true);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		// Rey
		coord = new Coordinate(0, 4);
		p = new Rey();
		p.setPosition(coord);
		p.setBlack_piece(true);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		// Peones
		for (int h = 0; h < 8; h++) {
			// Rey
			coord = new Coordinate(1, h);
			p = new Peon();
			p.setPosition(coord);
			p.setBlack_piece(true);
			pieces.put(coord.getX() + "-" + coord.getY(), p);
		}

		// creamos las blancas

		// Torres
		coord = new Coordinate(7, 0);
		p = new Torre();
		p.setPosition(coord);
		p.setBlack_piece(false);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		coord = new Coordinate(7, 7);
		p = new Torre();
		p.setPosition(coord);
		p.setBlack_piece(false);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		// Caballos
		coord = new Coordinate(7, 1);
		p = new Caballo();
		p.setPosition(coord);
		p.setBlack_piece(false);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		coord = new Coordinate(7, 6);
		p = new Caballo();
		p.setPosition(coord);
		p.setBlack_piece(false);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		// Alfiles
		coord = new Coordinate(7, 2);
		p = new Alfil();
		p.setPosition(coord);
		p.setBlack_piece(false);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		coord = new Coordinate(7, 5);
		p = new Alfil();
		p.setPosition(coord);
		p.setBlack_piece(false);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		// Reina
		coord = new Coordinate(7, 3);
		p = new Reina();
		p.setPosition(coord);
		p.setBlack_piece(false);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		// Rey
		coord = new Coordinate(7, 4);
		p = new Rey();
		p.setPosition(coord);
		p.setBlack_piece(false);
		pieces.put(coord.getX() + "-" + coord.getY(), p);

		// Peones
		for (int h = 0; h < 8; h++) {
			// Rey
			coord = new Coordinate(6, h);
			p = new Peon();
			p.setPosition(coord);
			p.setBlack_piece(false);
			pieces.put(coord.getX() + "-" + coord.getY(), p);
		}

	}
}
