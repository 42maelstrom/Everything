import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Jan5 {
	public static final String NAME = "MATCH PLAY";
	public static final String DESCRIPTION = 
			"The grid contains matches of different sizes, any of which"
			+ " may be completely unburnt, partially burnt, or completely burnt."
			+ " Matches burn from the head (rounded end) to the tail, without"
			+ " skipping segments. The numbers outside the grid indicate the number"
			+ " of burny segments in the corresponding row or column. Can you"
			+ " shade in the burnt segments to \"match\" the numbers?";
	public static final ArrayList<Class> HELPER_CLASSES = new ArrayList<Class>(
			Arrays.asList(Square.class, Direction.class, Axis.class));
	
	private Square[][] grid;
	private int[] rowBurnValues;
	private int[] colBurnValues;
	
	public Jan5(Square[][] grid, int[] rowBurnValues, int[] colBurnValues) {
		this.grid = grid;
		this.rowBurnValues = rowBurnValues;
		this.colBurnValues = colBurnValues;
	}
	
	public void solve() {
		while(!isSolved()) {
			System.out.println("Hi");
			checkSimpleBurning();
			//System.out.println();
			checkBurning();
			//System.out.println();
			checkBoxing();
			//System.out.println();
			for(int r = 0; r < 6; r++) {
				for(int c = 0; c < 6; c++)
					System.out.print((grid[r][c].isBurnt() ? "*" : "0"));
				System.out.println();
			}
			String test = new Scanner(System.in).nextLine();
		}
		
	}
	
	private boolean isSolved() {
		for(int i = 0; i < 6; i++) {
			if(rowBurnValues[i] != 0)
				return false;
			if(colBurnValues[i] != 0)
				return false;
		}
		return true;
	}
	
	private void checkBurning() {
		for(int r = 0; r < 6; r++)
			checkBurnRow(r);
		for(int c = 0; c < 6; c++)
			checkBurnCol(c);
	}
	
	private void checkSimpleBurning() {
		for(int r = 0; r < 6; r++)
			checkBurnSimpleRow(r);
		for(int c = 0; c < 6; c++)
			checkBurnSimpleCol(c);
	}
	
	private void checkBoxing() {
		for(int r = 0; r < 6; r++) {
			for(int c = 0; c < 6; c++) {
				Square s = grid[r][c];
				if(!s.isBoxed() && !s.isBurnt()) {
					if(s.getAxis() == Axis.UPDOWN) {
						if(s.getOrder() > colBurnValues[c]) {
							System.out.println("boxing: r = " + r + " c = " + c);
							box(r, c);
						}
					} else {
						if(s.getOrder() > rowBurnValues[r]) {
							System.out.println("boxing: r = " + r + " c = " + c);
							box(r, c);
						}
					}
				}
			}
		}
		
		for(int r = 0; r < 6; r++) {
			if(rowBurnValues[r] == 0) {
				for(int i = 0; i < 6; i++) {
					if(!grid[r][i].isBurnt() && !grid[r][i].isBoxed()) {
						box(r, i);
						System.out.println("row boxing: r = " + r + " c = " + i);
					}
				}
			}
		}
		
		for(int c = 0; c < 6; c++) {
			if(colBurnValues[c] == 0) {
				for(int i = 0; i < 6; i++) {
					if(!grid[i][c].isBurnt() && !grid[i][c].isBoxed()) {
						box(i, c);
						System.out.println("col boxing: r = " + i + " c = " + c);
					}
				}
			}
		}
	}
	
	private void reduceNeighborOrders(int r, int c) {
		int totalSize = grid[r][c].getTotalSize();
		int origOrder = grid[r][c].getOrigOrder();
		Direction dir = grid[r][c].getNeighborsDirection();
		
		int endR = r;
		int endC = c;
		
		switch(dir) {
		case LEFT:
			endC -= totalSize - origOrder;
			break;
		case RIGHT:
			endC += totalSize - origOrder;
			break;
		case UP:
			endR -= totalSize - origOrder;;
			break;
		case DOWN:
			endR += totalSize - origOrder;
			break;
		default:
			System.out.println("This shouldn't happen");
		}
		
		while(c != endC || r != endR) {
			
			switch(dir) {
			case LEFT:
				c--;
				break;
			case RIGHT:
				c++;
				break;
			case UP:
				r--;
				break;
			case DOWN:
				r++;
				break;
			default:
				System.out.println("This shouldn't happen");
			}
			
			if(!grid[r][c].isBoxed() && !grid[r][c].isBurnt()) {
				grid[r][c].reduceOrder();
				//System.out.println("New order of " + grid[r][c].getOrder() + " at r = " + r + " c = " + c);
			}
		}
	}
	
	private void box(int r, int c) {
		grid[r][c].boxMe();
		reduceNeighborOrders(r, c);
	}
	
	private void burn(int r, int c) {
		grid[r][c].burn();
		rowBurnValues[r]--;
		colBurnValues[c]--;
		reduceNeighborOrders(r, c);
		
		int totalSize = grid[r][c].getTotalSize();
		int origOrder = grid[r][c].getOrigOrder();
		Direction dir = grid[r][c].getNeighborsDirection();
		
		int startR = r;
		int startC = c;
		
		switch(dir) {
		case LEFT:
			startC += origOrder - 1;
			break;
		case RIGHT:
			startC -= origOrder - 1;
			break;
		case UP:
			startR += origOrder - 1;
			break;
		case DOWN:
			startR -= origOrder - 1;
			break;
		default:
			System.out.println("This shouldn't happen");
		}
		
		while(r!= startR || c!= startC) {
			switch(dir) {
			case LEFT:
				c++;
				break;
			case RIGHT:
				c--;
				break;
			case UP:
				r++;
				break;
			case DOWN:
				r--;
				break;
			default:
				System.out.println("This shouldn't happen");
			}
			
			if(grid[r][c].isBurnt() == false) {
				burn(r, c);
			}
		}
	}
	
	private void checkBurnSimpleRow(int r) {
		int numToBurn = rowBurnValues[r];
		int numUntouched = 0;
		
		for(int i = 0; i < 6; i++) {
			Square s = grid[r][i];
			if(s.isBoxed() == false && s.isBurnt() == false) {
				numUntouched++;
			}
		}
		
		if(numToBurn == numUntouched) {
			for(int i = 0; i < 6; i++) {
				if(!grid[r][i].isBoxed() && !grid[r][i].isBurnt()) {
					System.out.println("Simple Row Burn: r = " + r + " c = " + i);
					burn(r, i);
				}
			}
		}
	}
	
	private void checkBurnSimpleCol(int c) {
		int numToBurn = colBurnValues[c];
		int numUntouched = 0;
		
		for(int i = 0; i < 6; i++) {
			Square s = grid[i][c];
			if(s.isBoxed() == false && s.isBurnt() == false) {
				numUntouched++;
			}
		}
		
		if(numToBurn == numUntouched) {
			for(int i = 0; i < 6; i++) {
				if(!grid[i][c].isBoxed() && !grid[i][c].isBurnt()) {
					System.out.println("Simple Col Burn: r = " + i + " c = " + c);
					burn(i, c);
				}
			}
		}
	}
	
	private void checkBurnCol(int c) {
		int numBurned = colBurnValues[c];
		int numOtherDirectioners = 0;
		int numUpNexters = 0;
		
		for(int i = 0; i < 6; i++) {
			Square s = grid[i][c];
			if(s.isBoxed() == false) {
				if(s.getAxis() == Axis.LEFTRIGHT) {
					numOtherDirectioners++;
				} else if(s.getOrder() == 1) {
					numUpNexters++;
				}
			}
		}
		
		int numLeft = numBurned - numOtherDirectioners;
		
		boolean[] toBurn = new boolean[6];
		
		if(numUpNexters <= numLeft) {
			for(int i = 0; i < 6; i++) {
				Square s = grid[i][c];
				if(!s.isBurnt() && !s.isBoxed()) {
					if(s.getAxis() == Axis.UPDOWN && s.getOrder() == 1) {
						System.out.println("Col Burn: r = " + i + " c = " + c);
						toBurn[i] = true;
					}
				}
			}
		}
		
		for(int i = 0; i < 6; i++) {
			if(toBurn[i])
				burn(i, c);
		}
	}
	
	private void checkBurnRow(int r) {
//		each square has a number that tells the number of match sections ahead of it
//		can get adjusted once the top match gets burned, for example.
//		
//		first subtract the throwaways = other directioners and any other one-rs
//		if the number is not zero, you have to shade it in.
		
		int numBurned = rowBurnValues[r];
		int numOtherDirectioners = 0;
		int numUpNexters = 0;
		
		for(int i = 0; i < 6; i++) {
			Square s = grid[r][i];
			if(s.isBoxed() == false) {
				if(s.getAxis() == Axis.UPDOWN) {
					numOtherDirectioners++;
				} else if(s.getOrder() == 1) {
					numUpNexters++;
				}
			}
		}
		
		int numLeft = numBurned - numOtherDirectioners;
		
		boolean[] toBurn = new boolean[6];
		
		if(numUpNexters <= numLeft) {
			for(int i = 0; i < 6; i++) {
				Square s = grid[r][i];
				if(!s.isBurnt() && !s.isBoxed()) {
					if(s.getAxis() == Axis.LEFTRIGHT && s.getOrder() == 1) {
						System.out.println("Row Burn: r = " + r + " c = " + i);
						toBurn[i] = true;
					}
				}
			}
		}
		
		for(int i = 0; i < 6; i++) {
			if(toBurn[i])
				burn(r, i);
		}
	}
	
	public void printBoard() {
		for(int r = 0; r < 6; r++) {
			for(int c = 0; c < 6; c++) {
				Square s = grid[r][c];
				// new Square(axis, order, total length, neighbor direction)
				if(s.origOrder == 1)
					System.out.print('O');
				else if(s.getNeighborsDirection() == Direction.LEFT)
					System.out.print('<');
				else if(s.getNeighborsDirection() == Direction.RIGHT)
					System.out.print('>');
			}
			System.out.println();
		}
	}
	
	
	public static void main(String[] args) {
		Axis lr = Axis.LEFTRIGHT;
		Axis ud = Axis.UPDOWN;
		
		Direction u = Direction.UP;
		Direction d = Direction.DOWN;
		Direction l = Direction.LEFT;
		Direction r = Direction.RIGHT;
		
		Square[][] grid = new Square[6][6];
		// new Square(axis, order, total length, neighbor direction)
		grid[0][0] = new Square(lr, 1, 4, r);
		grid[0][1] = new Square(lr, 2, 4, r);
		grid[0][2] = new Square(lr, 3, 4, r);
		grid[0][3] = new Square(lr, 4, 4, r);
		grid[0][4] = new Square(ud, 1, 4, d);
		grid[0][5] = new Square(ud, 3, 3, u);
		                               
		grid[1][0] = new Square(ud, 4, 4, u);
		grid[1][1] = new Square(lr, 3, 3, l);
		grid[1][2] = new Square(lr, 2, 3, l);
		grid[1][3] = new Square(lr, 1, 3, l);
		grid[1][4] = new Square(ud, 2, 4, d);
		grid[1][5] = new Square(ud, 2, 3, u);
		                               
		grid[2][0] = new Square(ud, 3, 4, u);
		grid[2][1] = new Square(ud, 1, 3, d);
		grid[2][2] = new Square(lr, 1, 2, r);
		grid[2][3] = new Square(lr, 2, 2, r);
		grid[2][4] = new Square(ud, 4, 4, d);
		grid[2][5] = new Square(ud, 1, 3, u);
		                            
		grid[3][0] = new Square(ud, 2, 4, u);
		grid[3][1] = new Square(ud, 2, 3, d);
		grid[3][2] = new Square(lr, 2, 2, l);
		grid[3][3] = new Square(lr, 1, 2, l);
		grid[3][4] = new Square(ud, 4, 4, d);
		grid[3][5] = new Square(ud, 1, 3, d);
		                               
		grid[4][0] = new Square(ud, 1, 4, u);
		grid[4][1] = new Square(ud, 3, 3, d);
	  grid[4][2] = new Square(lr, 3, 3, l);
		grid[4][3] = new Square(lr, 2, 3, l);
		grid[4][4] = new Square(lr, 1, 3, l);
	  grid[4][5] = new Square(ud, 2, 3, d);
	                                   
	 grid[5][0] = new Square(lr, 1, 3, r);
	 grid[5][1] = new Square(lr, 2, 3, r);
	 grid[5][2] = new Square(lr, 3, 3, r);
	 grid[5][3] = new Square(lr, 1, 2, r);
	 grid[5][4] = new Square(lr, 2, 2, r);
	 grid[5][5] = new Square(ud, 3, 3, d);
	 
	 int[] rowBurnValues = {2, 4, 3, 2, 4, 1};
	 int[] colBurnValues = {3, 4, 2, 1, 3, 3};
	 
	 Jan5 puzzle = new Jan5(grid, rowBurnValues, colBurnValues);
	 puzzle.solve();
	}
}
