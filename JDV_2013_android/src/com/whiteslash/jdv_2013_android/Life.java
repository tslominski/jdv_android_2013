package com.whiteslash.jdv_2013_android;

import android.content.Context;

public class Life {

	public static final int CELL_SIZE = 8;
	public static int WIDTH = 320 / CELL_SIZE;
	public static int HEIGHT = 480 / CELL_SIZE;

	private static Cellule[][] _lifeGrid;

	private Context _context;

	public Life(Context context, Integer width, Integer height) {
		WIDTH = width / CELL_SIZE;
		HEIGHT = height / CELL_SIZE;
		_lifeGrid = new Cellule[HEIGHT][WIDTH];
		this._context = context;
		initializeGrid();
	}

	public static Cellule[][] getGrid() {
		return _lifeGrid;
	}

	public void initializeGrid() {
		resetGrid(_lifeGrid);

		/*
		 * for (int h = 0; h < HEIGHT; h++) { for (int w = 0; w < WIDTH; w++) {
		 * _lifeGrid[h][w]=(int) Math.round(Math.random()); } }
		 */

		_lifeGrid[(HEIGHT / 3) - 1][(WIDTH / 2) - 1].naitre();
		_lifeGrid[(HEIGHT / 3) - 1][(WIDTH / 2) + 1].naitre();
		_lifeGrid[HEIGHT / 3][(WIDTH / 2) - 1].naitre();
		_lifeGrid[HEIGHT / 3][(WIDTH / 2) + 1].naitre();
		_lifeGrid[(HEIGHT / 3) + 1][(WIDTH / 2) - 1].naitre();
		_lifeGrid[(HEIGHT / 3) + 1][(WIDTH / 2)].naitre();
		_lifeGrid[(HEIGHT / 3) + 1][(WIDTH / 2) + 1].naitre();

	}

	public void generateNextGeneration() {
		int neighbours;
		int minimum = Integer.parseInt(PreferencesActivity
				.getMinimumVariable(this._context));
		int maximum = Integer.parseInt(PreferencesActivity
				.getMaximumVariable(this._context));
		int spawn = Integer.parseInt(PreferencesActivity
				.getSpawnVariable(this._context));

		Cellule[][] nextGenerationLifeGrid = new Cellule[HEIGHT][WIDTH];
		resetGrid(nextGenerationLifeGrid);

		for (int h = 0; h < HEIGHT; h++) {
			for (int w = 0; w < WIDTH; w++) {
				neighbours = calculateNeighbours(h, w);

				if (_lifeGrid[h][w].isVivant()) {
					if ((neighbours >= minimum) && (neighbours <= maximum)) {
						nextGenerationLifeGrid[h][w] = _lifeGrid[h][w];
						nextGenerationLifeGrid[h][w].vieillir();
					}
				} else {
					if (neighbours == spawn) {
						nextGenerationLifeGrid[h][w].naitre();
					}
				}
			}
		}
		copyGrid(nextGenerationLifeGrid, _lifeGrid);
	}

	private void resetGrid(Cellule[][] grid) {
		for (int h = 0; h < HEIGHT; h++) {
			for (int w = 0; w < WIDTH; w++) {
				grid[h][w] = new Cellule(w, h);
			}
		}
	}

	private int calculateNeighbours(int y, int x) {
		int total = (_lifeGrid[y][x].isVivant()) ? -1 : 0;
		for (int h = -1; h <= +1; h++) {
			for (int w = -1; w <= +1; w++) {
				if (_lifeGrid[(HEIGHT + (y + h)) % HEIGHT][(WIDTH + (x + w))
						% WIDTH].isVivant()) {
					total++;
				}
			}
		}
		return total;
	}

	private void copyGrid(Cellule[][] source, Cellule[][] destination) {
		for (int h = 0; h < HEIGHT; h++) {
			for (int w = 0; w < WIDTH; w++) {
				destination[h][w] = source[h][w];
			}
		}
	}

	public void createCell(float x, float y) {
		int h = (int) (y / Life.CELL_SIZE);
		int w = (int) (x / Life.CELL_SIZE);

		_lifeGrid[h][w].naitre();// centrale

		/*
		 * //Création d'un bloc de 3*3 quand on clic sur l'ecran avec un trou au
		 * milieu _lifeGrid[(h-1)%HEIGHT][w].naitre();
		 * _lifeGrid[h-1%HEIGHT][(w+1)%WIDTH].naitre();
		 * _lifeGrid[h][(w+1)%WIDTH].naitre();
		 * _lifeGrid[(h+1)%HEIGHT][(w+1)%WIDTH].naitre();
		 * _lifeGrid[(h+1)%HEIGHT][w].naitre();
		 * _lifeGrid[(h+1)%HEIGHT][(w-1)%WIDTH].naitre();
		 * _lifeGrid[h][w-1].naitre();
		 * _lifeGrid[(h-1)%HEIGHT][(w-1)%WIDTH].naitre();
		 */

	}
}
