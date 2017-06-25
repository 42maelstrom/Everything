import java.util.ArrayList;

public class Board {
	private int[][] tiles;
	//to keep track of whether to update the board or not. 
	private boolean boardChanged;
	//during the calculation of a move, this makes sure nothing merges twice.
	private ArrayList<Position> mergedTiles;
	//to know for making a new-tile animation
	private ArrayList<Tile> newTiles;
	//to know where to make sliding motion animations
	private ArrayList<Movement> movements;
	private int scoreIncrease = 0;
	private ArrayList<Tile> tileList;
	private boolean got2048Tile;
	
	/**
	 * Creates a default 4x4 board and adds two new tiles.
	 */
	public Board() {
		tiles = new int[4][4];
		boardChanged = false;
		got2048Tile = false;
		mergedTiles = new ArrayList<Position>(16);
		movements = new ArrayList<Movement>(16);
		tileList = new ArrayList<Tile>(16);
		newTiles = new ArrayList<Tile>(2);
		addNewTile();
		addNewTile();
	}
	
	public int[][] getBoard() {
		return tiles;
	}
	
	public ArrayList<Tile> getTileList() {
		return tileList;
	}
	
	public ArrayList<Tile> getNewTiles() {
		return newTiles;
	}
	
	public ArrayList<Movement> getMovements() {
		return movements;
	}
	
	public int getScoreIncrease() {
		return scoreIncrease;
	}
	
	public boolean boardChanged() {
		return boardChanged;
	}
	
	public boolean isGameOver() {
		for(int r = 0; r < 4; r++) {
			for(int c = 0; c < 4; c++) {
				if(tiles[r][c] == 0)
					return false;
				if(r != 3 && tiles[r][c] == tiles[r+1][c])
					return false;
				if(c != 3 && tiles[r][c] == tiles[r][c+1])
					return false;
			}
		}
		return true;
	}
	
	public boolean got2048Tile() {
		return got2048Tile;
	}
	
	private void addNewTile() {
		ArrayList<Position> openTiles = new ArrayList<Position>();
		//look for empty spaces to add to openTiles.
		for(int r = 0; r < 4; r++) {
			for(int c = 0; c < 4; c++) {
				if(tiles[r][c] == 0)
					openTiles.add(new Position(r,c));
			}
		}
			
		if(openTiles.size() > 0) {
			int i = (int)(openTiles.size()*Math.random());
			Position newTilePos = openTiles.remove(i);
			int value = Math.random() < .9 ? 2 : 4;
			tiles[newTilePos.r][newTilePos.c] = value;
			Tile newTile = new Tile(value, newTilePos);
			newTile.size = 0.0;
			newTiles.add(newTile);
		}
	}
	
	/**
	 * This method is called any time a move is made,
	 * before the move is calculated.
	 */
	private void beginningOfTurn() {
		mergedTiles.clear();
		movements.clear();
		scoreIncrease = 0;
		tileList.clear();
		newTiles.clear();
		boardChanged = false;
	}
	
	/**
	 * This method is called any time a move is made,
	 * after the move is calculated.
	 */
	private void endOfTurn() {
		if(boardChanged)
			addNewTile();
	}
	
	public void moveUp() {
		beginningOfTurn();
		
		Tile originalTile;
		Position finalPos;
		boolean currentTileMoved;
		boolean tileMerges;
		int finalVal;
		
		for(int c = 0; c < 4; c++) {
			/*
			 * We skip the first row because they won't move.
			 * But we have to test and see if there are tiles here to add to
			 * the tileList.
			 */
			if(tiles[0][c] != 0) {
				originalTile = new Tile(tiles[0][c], 0, c);
				tileList.add(originalTile);
			}
			
			for(int r = 1; r < 4; r++) {
				if(tiles[r][c] != 0) {
					currentTileMoved = false;
					tileMerges = false;
					finalVal = tiles[r][c];
					
					originalTile = new Tile(tiles[r][c], r, c);
					tileList.add(originalTile);

					int r2 = r; //this changes since the tile is moving.
					//shift up while there's blank spaces
					while(r2 > 0 && tiles[r2 - 1][c] == 0) {
						currentTileMoved = true;
						tiles[r2 - 1][c] = tiles[r2][c];
						tiles[r2][c] = 0;
						r2--;
					}
					//new position of the tile.
					finalPos = new Position(r2, c);
							
					//now we've hit a tile. If it's a merger, merge them.
					if(r2 > 0 && tiles[r2 - 1][c] == tiles[r2][c]
							&& ! mergedTiles.contains(new Position(r2 - 1, c))) {
						currentTileMoved = true;
						tileMerges = true;
						finalVal*=2;
						tiles[r2 - 1][c] = finalVal;
						tiles[r2][c] = 0;
						scoreIncrease += finalVal;
						finalPos.r--;
						mergedTiles.add(finalPos);
					}
					
					if(currentTileMoved) {
						movements.add(new Movement(originalTile, tileMerges, finalPos, finalVal));
						boardChanged = true;
						
						if(finalVal == 2048)
							got2048Tile = true;
					}
					
				}//end if tiles[r][c] != 0
			}//end for each row
		}//end for each column
		
		endOfTurn();
	}//end method
	
	public void moveDown() {
		beginningOfTurn();
		
		Tile originalTile;
		Position finalPos;
		boolean currentTileMoved;
		boolean tileMerges;
		int finalVal;
		
		for(int c = 0; c < 4; c++) {
			/*
			 * We skip the first row because they won't move.
			 * But we have to test and see if there are tiles here to add to
			 * the tileList.
			 */
			if(tiles[3][c] != 0) {
				originalTile = new Tile(tiles[3][c], 3, c);
				tileList.add(originalTile);
			}

			for(int r = 2; r >= 0; r--) {
				if(tiles[r][c] != 0) {
					currentTileMoved = false;
					tileMerges = false;
					finalVal = tiles[r][c];
					
					originalTile = new Tile(tiles[r][c], r, c);
					tileList.add(originalTile);

					int r2 = r; //this changes when the tile is shifting
					//shift down while there's blank spaces
					while(r2 < 3 && tiles[r2 + 1][c] == 0) {
						currentTileMoved = true;
						tiles[r2 + 1][c] = tiles[r2][c];
						tiles[r2][c] = 0;
						r2++;
					}
					
					//new position of the tile.
					finalPos = new Position(r2, c);
					
					//now we've hit a tile. If it's a merger, merge them.
					if(r2 < 3 && tiles[r2 + 1][c] == tiles[r2][c]
							&& !mergedTiles.contains(new Position(r2 + 1, c))) {
						currentTileMoved = true;
						tileMerges = true;
						finalVal*=2;
						tiles[r2 + 1][c] = finalVal;
						tiles[r2][c] = 0;
						scoreIncrease += finalVal;
						finalPos.r++;
						mergedTiles.add(finalPos);
					}
					
					if(currentTileMoved) {
						movements.add(new Movement(originalTile, tileMerges, finalPos, finalVal));
						boardChanged = true;
						
						if(finalVal == 2048)
							got2048Tile = true;
					}
					
				}//end if tiles[r][c] != 0
			}//end for each row
		}//end for each column
		
		endOfTurn();
	}//end method
	
	public void moveLeft() {
		beginningOfTurn();

		Tile originalTile;
		Position finalPos;
		boolean currentTileMoved;
		boolean tileMerges;
		int finalVal;
		
		for(int r = 0; r < 4; r++) {
			/*
			 * We skip the first column because they won't move.
			 * But we have to test and see if there are tiles here to add to
			 * the tileList.
			 */
			if(tiles[r][0] != 0) {
				originalTile = new Tile(tiles[r][0], r, 0);
				tileList.add(originalTile);
			}

			for(int c = 1; c < 4; c++) {
				if(tiles[r][c] != 0) {
					currentTileMoved = false;
					tileMerges = false;
					finalVal = tiles[r][c];
					
					originalTile = new Tile(tiles[r][c], r, c);
					tileList.add(originalTile);
					
					int c2 = c; //this changes when the tile is shifting
					//shift while there's blank spaces
					while(c2 > 0 && tiles[r][c2 - 1] == 0) {
						currentTileMoved = true;
						tiles[r][c2 - 1] = tiles[r][c2];
						tiles[r][c2] = 0;
						c2--;
					}
					
					//new position of the tile.
					finalPos = new Position(r, c2);
					
					//now we've hit a tile. If it's a merger, merge them.
					if(c2 > 0 && tiles[r][c2 - 1] == tiles[r][c2]
							&& !mergedTiles.contains(new Position(r, c2 - 1))) {
						currentTileMoved = true;
						tileMerges = true;
						finalVal*=2;
						tiles[r][c2 - 1] = finalVal;
						tiles[r][c2] = 0;
						scoreIncrease += finalVal;
						finalPos.c--;
						mergedTiles.add(finalPos);
					}
					
					if(currentTileMoved) {
						movements.add(new Movement(originalTile, tileMerges, finalPos, finalVal));
						boardChanged = true;
						
						if(finalVal == 2048)
							got2048Tile = true;
					}
					
				}//end if tiles[r][c] != 0
			}//end for each column
		}//end for each row
		
		endOfTurn();
	}
	
	public void moveRight() {
		beginningOfTurn();
		
		Tile originalTile;
		Position finalPos;
		boolean currentTileMoved;
		boolean tileMerges;
		int finalVal;
		
		for(int r = 0; r < 4; r++) {
			/*
			 * We skip the first column because they won't move.
			 * But we have to test and see if there are tiles here to add to
			 * the tileList.
			 */
			if(tiles[r][3] != 0) {
				originalTile = new Tile(tiles[r][3], r, 3);
				tileList.add(originalTile);
			}

			for(int c = 2; c >= 0; c--) {
				if(tiles[r][c] != 0) {
					currentTileMoved = false;
					tileMerges = false;
					finalVal = tiles[r][c];
					
					originalTile = new Tile(tiles[r][c], r, c);
					tileList.add(originalTile);
					
					int c2 = c; //this changes when the tile is shifting
					//shift right while there's blank spaces
					while(c2 < 3 && tiles[r][c2 + 1] == 0) {
						currentTileMoved = true;
						tiles[r][c2 + 1] = tiles[r][c2];
						tiles[r][c2] = 0;
						c2++;
					}
					
					//new position of the tile.
					finalPos = new Position(r, c2);
					
					//now we've hit a tile. If it's a merger, merge them.
					if(c2 < 3 && tiles[r][c2 + 1] == tiles[r][c2]
							&& !mergedTiles.contains(new Position(r, c2 + 1))) {
						currentTileMoved = true;
						tileMerges = true;
						finalVal*=2;
						tiles[r][c2 + 1] = finalVal;
						tiles[r][c2] = 0;
						scoreIncrease += finalVal;
						finalPos.c++;
						mergedTiles.add(finalPos);
					}
					
					if(currentTileMoved) {
						movements.add(new Movement(originalTile, tileMerges, finalPos, finalVal));
						boardChanged = true;
						
						if(finalVal == 2048)
							got2048Tile = true;
					}

				}//end if tiles[r][c] != 0
			}//end for each column
		}//end for each row
		
		endOfTurn();
	}
}
