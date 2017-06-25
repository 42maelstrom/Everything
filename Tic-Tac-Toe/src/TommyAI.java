import java.util.ArrayList;
import java.util.Random;
public class TommyAI extends TicTacToePlayer 
{
	
	TPoint[] Xs = new TPoint[5];
	TPoint[] Os = new TPoint[4];
	Board ticTac;
	boolean hasWon = false;
	Random rn = new Random();
	int moveNum;
	
	public TommyAI(boolean isPlayerOne) {
		super(isPlayerOne);
		moveNum = 0;
	//add points to the board
			ArrayList<TPoint> points = new ArrayList<TPoint>();
			for (int i = 0; i < 3; i++)
			{
				for (int j = 0; j < 3; j++)
				{
					points.add(new TPoint(i, j));
				}
			}
			ticTac = new Board(points);
	}
	
	public Move makeMove(Tile[][] board, Point lastMove) {
		if(isPlayerOne) {
			if(moveNum != 0) {
				System.out.println(lastMove);
				TPoint p = ticTac.findTPoint(new TPoint(lastMove.x, 2 - lastMove.y));
				Os[moveNum - 1] = p;
				ticTac.fill(p);
			}
			TPoint p = makeXMove(moveNum);
			moveNum++;
			return new Move(new Point((int)p.x, 2 - (int)p.y), Tile.X);
		} else {
			TPoint p = ticTac.findTPoint(new TPoint(lastMove.x, 2 - lastMove.y));
			Xs[moveNum] = p;
			ticTac.fill(p);
			
			System.out.println(Xs[moveNum]);
		  p = makeOMove(moveNum);
			moveNum++;
			System.out.println(p);
			return new Move(new Point((int)p.x, 2 - (int)p.y), Tile.O);
		}
	}
	
	private void displayBoard(TPoint[] Xs, TPoint[] Os)
	{
//		ArrayList<char[]> rows = new ArrayList<char[]>();
//		char[] row1 = new char[3],  row2 = new char[3], row3 = new char[3];
//		rows.add(row1);
//		rows.add(row2);
//		rows.add(row3);
//		//add - to everything
//		for (int i = 0; i < 3; i++) 
//		{
//			for (int j = 0; j < 3; j++)
//			{
//				rows.get(i)[j] = '-';
//			}				
//		}
//		for (int i = 0; i < Xs.length; i++) //add Xs to rows
//		{
//			if (Xs[i] != null)
//			{
//				rows.get((int) Xs[i].getY())[(int) Xs[i].getX()] = 'X';
//			}
//		}
//		
//		for (int i = 0; i < Os.length; i++) //add Os to rows
//		{
//			if (Os[i] != null)
//			{
//				rows.get((int) Os[i].getY())[(int) Os[i].getX()] = 'O';
//			}
//		}
//		
//		//display board
//		for (int i = 2; i >= 0; i--) //start at top row
//		{
//			for (int j = 0; j < 3; j++)
//			{
//				System.out.print(rows.get(i)[j] + " ");
//			}	
//			System.out.println();
//		}
//		System.out.println();
	}
	
	private TPoint makeXMove(int moveNum)
	{
		
		if (moveNum == 0)
		{
			//first X, play at a random corner
			Xs[0] = ticTac.findTPoint(ticTac.randomEmptyCorner());
			//test point, not the actual AI:
			//Xs[0] = ticTac.findTPoint(new TPoint(2, 1));
			//System.out.println(Xs[0].toString());
			ticTac.fill(Xs[0]);
			displayBoard(Xs, Os);
		}
		else if (moveNum == 1)
		{
			//second X
			if (Os[0].isMiddle())
			{
				Xs[1] = ticTac.findTPoint(Xs[0].opposite());
			}
			else if (Os[0].isCorner())
			{
				if (Os[0].opposite().equals(Xs[0]))
				{
					Xs[1] = ticTac.findTPoint(ticTac.randomEmptyCorner());
				}
				else
				{
					Xs[1] = ticTac.findTPoint(Os[0].opposite());
				}
			}
			else //an edge, if edge is adjacent, play corner in line that's not adjacent, else play non-opposite adj corner
			{
				if (Os[0].isAdjacentTo(Xs[0]))
				{
					Xs[1] = ticTac.findTPoint(ticTac.adjacentCorners(Os[0]).get(0).opposite());
				}
				
				else
				{
					
					ArrayList<TPoint> availCorners = ticTac.adjacentCorners(Os[0]);
					
					if (availCorners.get(0).opposite().equals(Xs[0]))
					{
						//choose non-opposite point
						Xs[1] = ticTac.findTPoint(availCorners.get(1));
					}
					else
					{
						//choose non-opposite
						Xs[1] = ticTac.findTPoint(availCorners.get(0));
					}
					
				}
				
			}
			//test point: 
			//Xs[1] = ticTac.findTPoint(new TPoint(1, 0));
			//System.out.println(Xs[1].toString());
			ticTac.fill(Xs[1]);
			displayBoard(Xs, Os);
		}
		else if (moveNum == 2)
		{
			//X MOVE THREE, check if you can win, if so win!!!
			//next check and see if 0s are inline and are about to win, block if that, if not make a ... fork?
			if (ticTac.aboutToWin(Xs[0], Xs[1]))
			{
				Xs[2] = ticTac.block();
				ticTac.fill(Xs[2]);
				hasWon = true;
				//System.out.println(Xs[2].toString());
				displayBoard(Xs, Os);
				System.out.println("X won!!!");
			}
			else if (ticTac.aboutToWin(Os[0], Os[1]))//block O from winning
			{
				Xs[2] = ticTac.block();
				ticTac.fill(Xs[2]);
				displayBoard(Xs, Os);
				//System.out.println(Xs[2].toString());
			}
			//only other case happens (if playing with AI the whole time) if O played an edge, which means you can fork and win
			//play on the corner that isn't adjacent to an edge
			else
			{	
				int badIndex = 10; //either zero or one, choose the other, if none it will stay at ten
				for (int i = 0; i < ticTac.emptyCorners.size(); i++)//find that corner
				{
					for (int j = 0; j < 2; j++)
					{
						if (Os[j].isAdjacentTo(ticTac.emptyCorners.get(i)))
						{
							badIndex = i;
						}
					}			
				}
				if (badIndex == 10) //nothing was adjacent, so choose 0 (corner and edge case)
				{
					Xs[2] = ticTac.emptyCorners.get(0);
				}
				else
				{
					Xs[2] = ticTac.emptyCorners.get((badIndex + 1) % 2); //choose the non-bad-index corner!
				}
				ticTac.fill(Xs[2]);
				//System.out.println(Xs[2].toString());
				displayBoard(Xs, Os);
			}
		}
		else if (moveNum == 3)
		{
			//Xs FOURTH MOVE!!!! See if you can win, then check if any combination of 0s makes a winning one and block or guess
					for (int i = 0; i < moveNum; i++)//check if X can win with any combination of Xs
					{
						for (int j = i + 1; j < moveNum; j++)
						{
							if (ticTac.aboutToWin(Xs[i], Xs[j]))			
							{				
								Xs[3] = ticTac.block();	
								ticTac.fill(Xs[3]);
							//	System.out.println(Xs[3].toString());
								displayBoard(Xs, Os);
								System.out.println("X won!!!");
								hasWon = true;
							}
						}
					}
					//check 0 combos, block if one works 
					outer:
					for (int i = 0; i < moveNum; i++)
					{
						for (int j = i + 1; j < moveNum; j++)
						{
							if (ticTac.aboutToWin(Os[i], Os[j]))
							{
								Xs[3] = ticTac.block();	
								ticTac.fill(Xs[3]);
								//System.out.println(Xs[3].toString());
								displayBoard(Xs, Os);
								break outer;
							}
						}
					}
				//if you didn't block anything, try to find a move that makes free Xs in a line, if not play anything 
				if (Xs[3] == null)
				{
					int index = 0;
					outer:
					for (int i = 0; i < ticTac.emptyBoard.size(); i++)
					{
						for (int j = 0; j < moveNum; j++)
						{
							if (ticTac.aboutToWin(ticTac.emptyBoard.get(i), Xs[j]))
							{
								index = i;
								break outer;
							}
						}
					}
					Xs[3] = ticTac.emptyBoard.get(index);
					ticTac.fill(Xs[3]);
					//System.out.println(Xs[3].toString());
					displayBoard(Xs, Os);
				}
		}
		else
		{
			Xs[4] = ticTac.emptyBoard.get(0);
			ticTac.fill(Xs[4]);
			//System.out.println(Xs[4].toString());
			displayBoard(Xs, Os);
			System.out.println("Tie...");	
		}
		return Xs[moveNum];
	}
	
	private TPoint makeOMove(int moveNum)
	{
		if (moveNum == 0)
		{
			//first 0
			if (Xs[0].isCorner()) //play in the middle
			{
				Os[0] = ticTac.findTPoint(new TPoint(1, 1));
			}
			else //play at a random adjacent corner
			{
				ArrayList<TPoint> adjCorn = ticTac.adjacentCorners((Xs[0]));
				Os[0] = ticTac.findTPoint(adjCorn.get(rn.nextInt(adjCorn.size())));
			}
			//test:
			Os[0] = ticTac.findTPoint(new TPoint(0, 1));
			//System.out.println(Os[0].toString());
			ticTac.fill(Os[0]);
			displayBoard(Xs, Os);
		}
		else if (moveNum == 1)
		{
			//second 0, check if player one can win, if it can, block, if not play smart
			if (ticTac.aboutToWin(Xs[0], Xs[1]))
			{
				Os[1] = ticTac.block();
			}
			else if (Xs[0].inLine(Xs[1]))//you already played in block, play strategically
			{
	
				if (Os[0].isMiddle()) //only happens if blocking two edges or two corners
				//if blocking two edges, play at a random empty corner, either way doesn't do much
				//if blocking two corners, play at a random edge (or else you lose)
				{
					Os[1] = ticTac.randomEmptyEdge();
				}
				else //it's an edge, not possible for a corner to block two, have to play middle to force x badly			
				{
					Os[1] = ticTac.findTPoint(new TPoint(1, 1));
				}
			}
			
			else //X's are not aligned, so player one is bad! Create two 0s in a line by finding the non-opposite corner
			{
				if (Xs[0].isEdge() && Xs[1].isEdge()) //both edges
				{
					//if corner is adjacent to both play middle to block
					if (Xs[0].isAdjacentTo(Os[0]) && Xs[1].isAdjacentTo(Os[0]))
					{
						Os[1] = ticTac.findTPoint(new TPoint(1, 1));
					}
					//if corner isn't adjacent play in the non-opposite corner
					else if (ticTac.emptyCorners.get(0).equals(Os[0].opposite())) //play in another corner
					{
						Os[1] = ticTac.emptyCorners.get(1);
					}
					else //first isn't opposite, so play there
					{
						Os[1] = ticTac.emptyCorners.get(0);
					}
				}
				
				else if (Xs[0].isCorner()) //if one's a corner, find opposite and play at a different random empty corner
				{
					if (ticTac.emptyCorners.get(0).equals(Xs[0].opposite())) //first corner is the opposite, choose second
					{
						Os[1] = ticTac.emptyCorners.get(1);			
					}
					else //first isn't opposite, choose it
					{
						Os[1] = ticTac.emptyCorners.get(0);
					}
				}
				else //Xs[1] is the actual corner, repeat but with Xs[1]
				{
					if (ticTac.emptyCorners.get(0).equals(Xs[1].opposite())) //first is the opposite, choose another
					{
						Os[1] = ticTac.emptyCorners.get(1);			
					}
					else //first isn't opposite, choose it
					{
						Os[1] = ticTac.emptyCorners.get(0);
					}
				}
			}
			//System.out.println(Os[1].toString());
			ticTac.fill(Os[1]);
			displayBoard(Xs, Os);
		}
		else if (moveNum == 2)
		{
			//0s THIRD MOVE!!! See if you can win, then check if any combination of Xs makes a winning one, if so block it
			if (ticTac.aboutToWin(Os[0], Os[1]))
			{
				Os[2] = ticTac.block();
				ticTac.fill(Os[2]);
				//System.out.println(Os[2].toString());
				displayBoard(Xs, Os);
				hasWon = true;
				System.out.println("O won!!!");
			}
			else//check x combos, block if one works (if forked you'll lose anyways :()
			{
				outer:
				for (int i = 0; i < moveNum + 1; i++)
				{
					for (int j = i + 1; j < moveNum + 1; j++)
					{
						if (ticTac.aboutToWin(Xs[i], Xs[j]))
						{
							Os[2] = ticTac.block();	
							ticTac.fill(Os[2]);
							//System.out.println(Os[2].toString());
							displayBoard(Xs, Os);
							break outer;
						}
					}
				}
			}
			//if you didn't block anything, try to find a move that makes free 0s in a line, if not play anything 
			if (Os[2] == null)
			{
				int index = 0;
				outer:
				for (int i = 0; i < ticTac.emptyBoard.size(); i++)
				{
					for (int j = 0; j < moveNum; j++)
					{
						if (ticTac.aboutToWin(ticTac.emptyBoard.get(i), Os[j]))
						{
							index = i;
							break outer;
						}
					}
				}
				Os[2] = ticTac.emptyBoard.get(index);
				ticTac.fill(Os[2]);
				//System.out.println(Os[2].toString());
				displayBoard(Xs, Os);
			}
		}
		else 
		{
			//Os FOURTH MOVE!!!!! Normal procedure at this point, just add one to moveCount for the Xs
			for (int i = 0; i < moveNum; i++)//check if O can win with any combination of Os
			{
				for (int j = i + 1; j < moveNum; j++)
				{
					if (ticTac.aboutToWin(Os[i], Os[j]))			
					{				
						Os[3] = ticTac.block();	
						ticTac.fill(Os[3]);
						//System.out.println(Os[3].toString());
						displayBoard(Xs, Os);
						System.out.println("O won!!!");
						hasWon = true;
					}
				}
			}
			//check X combos, block if one works 
			outer:
			for (int i = 0; i < moveNum + 1; i++)//one more X than O
			{
				for (int j = i + 1; j < moveNum + 1; j++)
				{
					if (ticTac.aboutToWin(Xs[i], Xs[j]))
					{
						Os[3] = ticTac.block();	
						ticTac.fill(Os[3]);
					//	System.out.println(Os[3].toString());
						displayBoard(Xs, Os);
						break outer;
					}
				}
			}
		//if you didn't block anything, try to find a move that makes free 0s in a line, if not play anything 
		if (Os[3] == null)
		{
			int index = 0;
			outer:
			for (int i = 0; i < ticTac.emptyBoard.size(); i++)
			{
				for (int j = 0; j < moveNum; j++)
				{
					if (ticTac.aboutToWin(ticTac.emptyBoard.get(i), Os[j]))
					{
						index = i;
						break outer;
					}
				}
			}
			Os[3] = ticTac.emptyBoard.get(index);
			ticTac.fill(Os[3]);
			//System.out.println(Os[3].toString());
			displayBoard(Xs, Os);
		}
		}
		ticTac.fill(Os[moveNum]);
		return Os[moveNum];
	}
	
	private class Board {
		Random rn = new Random();
		ArrayList<TPoint> board = new ArrayList<TPoint>();
		ArrayList<TPoint> emptyCorners;
		ArrayList<TPoint> emptyEdges;
		ArrayList<TPoint> emptyBoard;
		TPoint block;
		public Board (ArrayList<TPoint> points)
		{
			board = points;
			emptyCorners = getCorners();
			emptyEdges = getEdges();
			emptyBoard = board;		
		}
		
		public ArrayList<TPoint> getCorners()
		{
			ArrayList<TPoint> corners = new ArrayList<TPoint>();
			for (int i = 0; i < board.size(); i++)
			{
				if (board.get(i).isCorner() == true)
				{
					corners.add(board.get(i));
				}
			}
			return corners;
		}
		
		public ArrayList<TPoint> getEdges()
		{
			ArrayList<TPoint> edges = new ArrayList<TPoint>();
			for (int i = 0; i < board.size(); i++)
			{
				if (board.get(i).isEdge() == true)
				{
					edges.add(board.get(i));
				}
			}
			return edges;
		}
		
		public ArrayList<TPoint> adjacentCorners(TPoint point)
		{
			ArrayList<TPoint> adjCorn = getCorners();
			if (point.isCorner())
			{
				return null;
			}
			else if (point.isMiddle())
			{
				return emptyCorners;
			}
			else
			{
				for (int i = 0; i < adjCorn.size(); i++)
				{
					if (adjCorn.get(i).isAdjacentTo(point) == false || adjCorn.get(i).isFilled())
					{
						adjCorn.remove(i);
						i--;
					}
				}
			}
			return adjCorn;
		}
		
		public void fill(TPoint point)
		{
			for (int i = 0; i < board.size(); i++)
			{
				if (board.get(i).equals(point))
				{
					board.get(i).fill();
					if (point.isCorner())
					{
						emptyCorners.remove(point);
					}
					else if (point.isEdge())
					{
						emptyEdges.remove(point);
					}
					emptyBoard.remove(point);
					break;
				}
			}
		}
		
		public TPoint findTPoint(TPoint point)
		{
			for (int i = 0; i < board.size(); i++)
			{
				if (point.equals(board.get(i)))
				{
					return board.get(i);
				}
			}
			return null;
		}
		
		public ArrayList<TPoint> emptyCorners()
		{
			return emptyCorners;
		}
		
		public ArrayList<TPoint> emptyEdges()
		{
			return emptyEdges;
		}
		
		public TPoint randomEmptyCorner()
		{
			return (emptyCorners.get(rn.nextInt(emptyCorners.size())));
		}
		
		public TPoint randomEmptyEdge()
		{
			return (emptyEdges.get(rn.nextInt(emptyEdges.size())));
		}
		
		public ArrayList<TPoint> filled()
		{
			ArrayList<TPoint> filledTPoints = new ArrayList<TPoint>();
			for (int i = 0; i < board.size(); i++)
			{
				if (board.get(i).isFilled() == true);
				{
					filledTPoints.add(board.get(i));
				}	
			}
			return filledTPoints;
						
		}
		
		public boolean aboutToWin(TPoint first, TPoint second)
		{
			if (first.inLine(second) == true)
			{
			double x;
			double y;
			if (first.isCorner() && second.isCorner())
			{
				block = first.midTPoint(second);
			}
			else
			{
			//find the bigger point, start there and add difference from smaller --> bigger to bigger
				//modulus is useful for wraparound!!!! 
				TPoint bigger = null;
				TPoint smaller = null;
				if (first.magnitude() > second.magnitude()) //Xs[0] is bigger
				{
					bigger = first;
					smaller = second;
				}
				else //Xs[1] is bigger
				{
					bigger = second;
					smaller = first;
				}
				//bigger + (bigger - smaller) = 2*bigger - smaller			
				x = (2 * bigger.getX()  - smaller.getX()) % 3;
				y = (2 * bigger.getY()  - smaller.getY()) % 3;
				if (x < 0)
				{
					x += 3;
				}
				if (y < 0)
				{
					y += 3;
				}
				block = new TPoint(x, y);
			}
			if (emptyBoard.contains(findTPoint(block))) //block the lineup!!!
			{		 
				return true;
			}
		}
			return false;
		}
		
		public TPoint block()
		{
			return findTPoint(block);
		}
				
	}
	
	private class TPoint {
		double x;
		double y;
		boolean filled;
		public TPoint (double xCoordinate, double yCoordinate)
		{
			x = xCoordinate;
			y = yCoordinate;
			filled = false;
		}
		
		public double getX()
		{
			return x;
		}
		
		public double getY()
		{
			return y;
		}
		
		public boolean isCorner()
		{
			if ((x + y) % 2 == 0 && isMiddle() == false)
			{
				return true;
			}
			return false;
		}
		
		public boolean isMiddle()
		{
			if (x == 1 && y == 1)
			{
				return true;
			}
			return false;
		}
		
		public boolean isEdge()
		{
			if ((x + y) % 2 != 0 && isMiddle() == false)
			{
				return true;
			}
			return false;
		}
		
		public boolean isFilled()
		{
			return filled;
		}
		
		public void fill()
		{
			filled = true;
		}
		
		public TPoint opposite()
		{
			return (new TPoint(2 - x, 2 - y));
		}
		
		public TPoint midTPoint(TPoint second)
		{
			return  (new TPoint(.5*(x + second.getX()), .5*(y + second.getY())));
		}
		
		public String toString()
		{
			return "(" + x + ", " + y + ")" ;
		}
		
		public boolean equals(TPoint second)
		{
			if (getX() == second.getX() && getY() == second.getY())
			{
				return true;
			}
			return false;
		}
		
		public boolean isAdjacentTo(TPoint second)
		{
			if ((Math.abs(getX() - second.getX()) <= 1) && (Math.abs(getY() - second.getY()) <= 1))
			{
				return true;
			}
			return false;
		}
		
		public boolean inLine(TPoint second)
		{
			//check if diff between (abs) is 3 and it's a corner, if it is, they're not in line
			//if edge see if it's an adjacent edge or nonadjacent corner (return false if), else return true
			if (isCorner())
			{
				if (Math.abs(getX() - second.getX()) + Math.abs(getY() - second.getY()) == 3)
				{
					return false;
				}
			}
			else if (isEdge())
			{
				if (second.isEdge() && isAdjacentTo(second))
				{
					return false;
				}
				else if (second.isCorner() && isAdjacentTo(second) == false)
				{
					return false;
				}
				return true;
			}
			return true;
		}
		
		public double magnitude()
		{
			return Math.sqrt(x*x + y*y);		
		}
	}
}