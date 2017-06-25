import java.util.ArrayList;
import java.util.Arrays;

public class Board {

	private Tile[][] tiles;
	private ArrayList<Position> goodSpots;
	
	public Board()
	{
		tiles = new Tile[182][182];
		goodSpots = new ArrayList<Position>(25);
	}
	
	public Tile getTileAt(int xPos, int yPos)
	{
		return tiles[yPos][xPos];
	}
	
	public boolean isTileAt(int xPos, int yPos)
	{
		if(getTileAt(xPos, yPos) != null)
			return true;
		return false;
	}
	
	public void setGoodSpots(ArrayList<Position> spots)
	{
		this.goodSpots = spots;
	}
	
	/**
	 * @param tile - the tile to be added
	 * @param xPos - the x position of the tile's new position
	 * @param yPos - the y position of the tile's new position
	 */
	public void addTile(Tile tile, int xPos, int yPos)
	{
		tiles[yPos][xPos] = tile;
	}
	
	/**
	 * Calculates the points for a move.
	 * Already assumes that the move is a valid move.
	 * In the move, positions in the ArrayList<Position> list must be in order.
	 * That is, the first and last element should be the first and last tiles in the row.
	 */
	public int pointsFor(Move move)
	{
		int totalPoints = move.length();
		if(move.length() == 1)
		{
			Position tilePos = move.getPositions().get(0);
			int[] xShift = {-1,1,0,0};
			int[] yShift = {0,0,-1,1};
			
			for(int d = 0; d < 3; d++)
			{
				int shift = 1;
				while(isTileAt(tilePos.getX() - shift*xShift[d], tilePos.getY() - shift*yShift[d]))
				{
					shift++;
				}
				totalPoints+=shift-1;
			}
		}else{
			ArrayList<Position> positions = move.getPositions();
			if(0 == positions.get(0).getY() - positions.get(1).getY())
			{
				//horizontal move
				int leftRight = -1;
				if(positions.get(0).getX() - positions.get(positions.size() - 1).getX() < 0)
				{
					//get(0) is on the left
					leftRight = 1;
				}
				int shift = 1;
				while(isTileAt(positions.get(0).getX() - leftRight*shift, positions.get(0).getY()))
				{
					shift++;
				}
				totalPoints += shift - 1;
				shift = 1;
				while(isTileAt(positions.get(positions.size() - 1).getX() + leftRight*shift, positions.get(0).getY()))
				{
					shift++;
				}
				totalPoints += shift - 1;
				
				for(Position pos: positions)
				{
					shift = 1;
					while(isTileAt(pos.getX(), pos.getY() + shift))
					{
						shift++;
					}
					totalPoints += shift - 1;
					shift = 1;
					while(isTileAt(pos.getX(), pos.getY() - shift))
					{
						shift++;
					}
					totalPoints += shift - 1;
				}
				
			}else{
				//vertical move
				int upDown = -1;
				if(positions.get(0).getY() - positions.get(positions.size() - 1).getY() < 0)
				{
					//get(0) is on the top
					upDown = 1;
				}
				
				int shift = 1;
				while(isTileAt(positions.get(0).getX(), positions.get(0).getY() - upDown*shift))
				{
					shift++;
				}
				totalPoints += shift - 1;
				shift = 1;
				while(isTileAt(positions.get(positions.size() - 1).getX(), positions.get(0).getY() + upDown*shift))
				{
					shift++;
				}
				totalPoints += shift - 1;
				
				for(Position pos: positions)
				{
					shift = 1;
					while(isTileAt(pos.getX() + shift, pos.getY()))
					{
						shift++;
					}
					totalPoints += shift - 1;
					shift = 1;
					while(isTileAt(pos.getX() - shift, pos.getY()))
					{
						shift++;
					}
					totalPoints += shift - 1;
				}
			}
			
		}
		
		return totalPoints;
	}
	
	public Move bestMove(ArrayList<Tile> tileRack)
	{
		Move bestMove = new Move();
		
		for(Position pos: goodSpots)
		{
			//the max length move for each direction
			Move[] maxLengthMoves = new Move[4];
			
			for(Tile tile: tileRack)
			{
				if(isValidPos(tile, pos.getX(), pos.getY()))
				{
					ArrayList<Tile> rest = new ArrayList<Tile>(tileRack);
					rest.remove(tile);
					
					int[] xShift = {-1,1,0,0};
					int[] yShift = {0,0,-1,1};
					
					//for each direction (up, down, left, right)
					for(int d = 0; d < 3; d++)
					{
						//second tile of the move
						for(Tile temp: rest)
						{
							if(isValidPos(temp, pos.getX() + xShift[d], pos.getY() + yShift[d]))
							{
								ArrayList<Tile> rest2 = new ArrayList<Tile>(4);
								
								//if color is maintained
								if(temp.getColor() == tile.getColor())
								{
									for(Tile tempTile: rest)
									{
										if(tempTile.getColor() == temp.getColor())
										{
											boolean isDuplicate = false;
											for(Tile temp2: rest2)
											{
												if(temp2.getShape() == tempTile.getShape())
												{
													isDuplicate = true;
													break;
												}	
											}
											if(isDuplicate == false)
												rest2.add(tempTile);
										}
									}
								}else{
									for(Tile tempTile: rest)
									{
										if(tempTile.getShape() == temp.getShape())
										{
											boolean isDuplicate = false;
											for(Tile temp2: rest2)
											{
												if(temp2.getShape() == tempTile.getShape())
												{
													isDuplicate = true;
													break;
												}	
											}
											if(isDuplicate == false)
												rest2.add(tempTile);
										}
									}
								}
								rest2.remove(temp);
								
								int length = 0;
								
								//for each permutation
								for(ArrayList<Tile> tiles: permute(rest2))
								{
									ArrayList<Position> positions = new ArrayList<Position>();
									while(length < tiles.size() && isValidPos(tiles.get(length), pos.getX() + xShift[d], pos.getY() + yShift[d]))
									{
										length++;
										positions.add(new Position(pos.getX() + xShift[d], pos.getY() + yShift[d]));
									}
									
									if(maxLengthMoves[d] == null || length > maxLengthMoves[d].length())
									{
										tiles.add(0, temp);
										tiles.add(0, tile);
										maxLengthMoves[d] = new Move(positions, tiles);
									}
								}//end for each permutation
								
							}//end isValidPos(temp)
							
						}//end for each tile temp from rest
						
					}//end for each direction
					
				}// end isValidPosition tile
				
			}//end for each tile
			
			for(Move move: maxLengthMoves)
			{
				if(move != null)
				{
					int points = pointsFor(move);
					if(points > bestMove.getPoints())
					{
						bestMove = move;
						bestMove.setPoints(points);
					}
				}
			}
			
		}//end for each position
		
		return bestMove;
	}
	
	private ArrayList<ArrayList<Tile>> permute(ArrayList<Tile> tiles) {
		
		ArrayList<ArrayList<Tile>> result = new ArrayList<ArrayList<Tile>>();
	 
		//start from an empty list
		result.add(new ArrayList<Tile>());
	 
		for (int i = 0; i < tiles.size(); i++) {
			
			//list of list in current iteration of the array tiles
			ArrayList<ArrayList<Tile>> current = new ArrayList<ArrayList<Tile>>();
		
			//for each list in result, add tiles[i] to all of the locations
			for (ArrayList<Tile> l : result) {
				
				// # of locations to insert is largest index + 1
				for (int j = 0; j < l.size()+1; j++) {
					
					// + add tiles[i] to different locations
					l.add(j, tiles.get(i));
	 
					ArrayList<Tile> temp = new ArrayList<Tile>(l);
					current.add(temp);
	 
					l.remove(j);
				}
			}
			result = new ArrayList<ArrayList<Tile>>(current);
		}
		return result;
	}
	
	/**
	 * Already assumes that this is an empty space that is surrounded by at least one tile.
	 */
	public boolean isValidPos(Tile tile, int xPos, int yPos)
	{
		//for going to the left.
		if(isTileAt(xPos - 1, yPos))
		{
			boolean shapesEqual = tile.getShape() == getTileAt(xPos - 1, yPos).getShape();
			boolean colorsEqual = tile.getColor() == getTileAt(xPos - 1, yPos).getColor();
			// shapes are equal XOR colors are equal
			if(shapesEqual ^ colorsEqual)
			{
				int shift = 2;
				while(isTileAt(xPos - shift, yPos))
				{
					Tile tile2 = getTileAt(xPos - shift, yPos);
					if(shapesEqual != (tile.getShape() == tile2.getShape())
					   || colorsEqual != (tile.getColor() == tile2.getColor()))	
							return false;
					shift++;
				}
			}else{
				return false;
			}
		}
		
		//for going to the right.
		if(isTileAt(xPos + 1, yPos))
		{
			boolean shapesEqual = tile.getShape() == getTileAt(xPos + 1, yPos).getShape();
			boolean colorsEqual = tile.getColor() == getTileAt(xPos + 1, yPos).getColor();
			// shapes are equal XOR colors are equal
			if(shapesEqual ^ colorsEqual)
			{
				int shift = 2;
				while(isTileAt(xPos + shift, yPos))
				{
					Tile tile2 = getTileAt(xPos + shift, yPos);
					if(shapesEqual != (tile.getShape() == tile2.getShape())
					   || colorsEqual != (tile.getColor() == tile2.getColor()))	
							return false;
					shift++;
				}
			}else{
				return false;
			}
		}
		
		//for going up.
		if(isTileAt(xPos, yPos - 1))
		{
			boolean shapesEqual = tile.getShape() == getTileAt(xPos, yPos - 1).getShape();
			boolean colorsEqual = tile.getColor() == getTileAt(xPos, yPos - 1).getColor();
			// shapes are equal XOR colors are equal
			if(shapesEqual ^ colorsEqual)
			{
				int shift = 2;
				while(isTileAt(xPos, yPos - shift))
				{
					Tile tile2 = getTileAt(xPos, yPos - shift);
					if((shapesEqual != (tile.getShape() == tile2.getShape()))
					   || (colorsEqual != (tile.getColor() == tile2.getColor())))
							return false;
					shift++;
				}
			}else{
				return false;
			}
		}
		
		//for going down.
		if(isTileAt(xPos, yPos + 1))
		{
			boolean shapesEqual = tile.getShape() == getTileAt(xPos, yPos + 1).getShape();
			boolean colorsEqual = tile.getColor() == getTileAt(xPos, yPos + 1).getColor();
			// shapes are equal XOR colors are equal
			if(shapesEqual ^ colorsEqual)
			{
				int shift = 2;
				while(isTileAt(xPos, yPos + shift))
				{
					Tile tile2 = getTileAt(xPos, yPos + shift);
					if(shapesEqual != (tile.getShape() == tile2.getShape())
					   || colorsEqual != (tile.getColor() == tile2.getColor()))	
							return false;
					shift++;
				}
			}else{
				return false;
			}
		}
		
		return true;
	}
	
	public void printTiles() {
		for(int i = 0; i < tiles[0].length; i++) {
			for(int j = 0; j < tiles[1].length; j++) {
				if(isTileAt(i, j))
					System.out.println(tiles[j][i].toString() + ": " + new Position(i, j).toString());
			}
		}
	}
}
