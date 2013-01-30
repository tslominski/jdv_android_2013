package com.whiteslash.jdv_2013_android;

import android.content.Context;

public class Life {

	public static final int CELL_SIZE = 8;
	public static int WIDTH = 320 / CELL_SIZE;
	public static int HEIGHT = 480 / CELL_SIZE;

	private static int[][] _lifeGrid = new int[HEIGHT][WIDTH];

	private Context _context;

	public Life(Context context, Integer width, Integer height) {
		WIDTH = width / CELL_SIZE;
		HEIGHT = height / CELL_SIZE;
		_lifeGrid = new int[HEIGHT][WIDTH];
		this._context = context;
		initializeGrid();
	}

	public static int[][] getGrid() {
		return _lifeGrid;
	}

	public void initializeGrid() {
		resetGrid(_lifeGrid);

		/*for (int h = 0; h < HEIGHT; h++) {
			for (int w = 0; w < WIDTH; w++) {
				_lifeGrid[h][w]=(int) Math.round(Math.random());
			}
		}*/

		
		  _lifeGrid[8][(WIDTH / 2) - 1] = 1; _lifeGrid[8][(WIDTH / 2) + 1] = 1;
		  _lifeGrid[9][(WIDTH / 2) - 1] = 1; _lifeGrid[9][(WIDTH / 2) + 1] = 1;
		  _lifeGrid[10][(WIDTH / 2) - 1] = 1; _lifeGrid[10][(WIDTH / 2)] = 1;
		  _lifeGrid[10][(WIDTH / 2) + 1] = 1;
		 
	}

	public void generateNextGeneration() {
		int neighbours;
		int minimum = Integer.parseInt(PreferencesActivity
				.getMinimumVariable(this._context));
		int maximum = Integer.parseInt(PreferencesActivity
				.getMaximumVariable(this._context));
		int spawn = Integer.parseInt(PreferencesActivity
				.getSpawnVariable(this._context));

		int[][] nextGenerationLifeGrid = new int[HEIGHT][WIDTH];
		
		for (int h = 0; h < HEIGHT; h++) {
			for (int w = 0; w < WIDTH; w++) {
				neighbours = calculateNeighbours(h, w);

				if (_lifeGrid[h][w] != 0) {
					if ((neighbours >= minimum) && (neighbours <= maximum)) {
						nextGenerationLifeGrid[h][w] = _lifeGrid[h][w]++;
					}
				} else {
					if (neighbours == spawn) {
						nextGenerationLifeGrid[h][w] = 1;
					}
				}
			}
		}
		copyGrid(nextGenerationLifeGrid, _lifeGrid);
	}

	private void resetGrid(int[][] grid) {
		for (int h = 0; h < HEIGHT; h++) {
			for (int w = 0; w < WIDTH; w++) {
				grid[h][w] = 0;
			}
		}
	}

	private int calculateNeighbours(int y, int x) {
		int total = (_lifeGrid[y][x] != 0) ? -1 : 0;
		for (int h = -1; h <= +1; h++) {
			for (int w = -1; w <= +1; w++) {
				if (_lifeGrid[(HEIGHT + (y + h)) % HEIGHT][(WIDTH + (x + w))
						% WIDTH] != 0) {
					total++;
				}
			}
		}
		return total;
	}

	private void copyGrid(int[][] source, int[][] destination) {
		for (int h = 0; h < HEIGHT; h++) {
			for (int w = 0; w < WIDTH; w++) {
				destination[h][w] = source[h][w];
			}
		}
	}
	
	public void createCell(float x, float y){
		int h=(int) (y/Life.CELL_SIZE);
		int w=(int) (x/Life.CELL_SIZE);
		_lifeGrid[h][w]=1;
	}
}
