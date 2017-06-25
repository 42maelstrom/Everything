public class May25 {
	public static void main(String[] args) {
		Axis lr = Axis.LEFTRIGHT;
		Axis ud = Axis.UPDOWN;
		
		Direction u = Direction.UP;
		Direction d = Direction.DOWN;
		Direction l = Direction.LEFT;
		Direction r = Direction.RIGHT;
		
		Square[][] grid = new Square[6][6];
		// new Square(axis, order, total length, neighbor direction)
		grid[0][0] = new Square(lr, 1, 2, r);
		grid[0][1] = new Square(lr, 2, 2, r);
		grid[0][2] = new Square(lr, 2, 2, l);
		grid[0][3] = new Square(lr, 1, 2, l);
		grid[0][4] = new Square(lr, 1, 2, r);
		grid[0][5] = new Square(lr, 2, 2, r);
		                               
		grid[1][0] = new Square(ud, 5, 5, u);
		grid[1][1] = new Square(ud, 1, 5, d);
		grid[1][2] = new Square(lr, 4, 4, l);
		grid[1][3] = new Square(lr, 3, 4, l);
		grid[1][4] = new Square(lr, 2, 4, l);
		grid[1][5] = new Square(lr, 1, 4, l);
		                               
		grid[2][0] = new Square(ud, 4, 5, u);
		grid[2][1] = new Square(ud, 2, 5, d);
		grid[2][2] = new Square(ud, 3, 3, u);
		grid[2][3] = new Square(lr, 1, 3, r);
		grid[2][4] = new Square(lr, 2, 3, r);
		grid[2][5] = new Square(lr, 3, 3, r);
		                            
		grid[3][0] = new Square(ud, 3, 5, u);
		grid[3][1] = new Square(ud, 3, 5, d);
		grid[3][2] = new Square(ud, 2, 3, u);
		grid[3][3] = new Square(lr, 2, 2, l);
		grid[3][4] = new Square(lr, 1, 2, l);
		grid[3][5] = new Square(ud, 2, 2, u);
		                               
		grid[4][0] = new Square(ud, 2, 5, u);
		grid[4][1] = new Square(ud, 4, 5, d);
	  grid[4][2] = new Square(ud, 1, 3, u);
		grid[4][3] = new Square(lr, 1, 2, r);
		grid[4][4] = new Square(lr, 2, 2, r);
	  grid[4][5] = new Square(ud, 1, 2, u);
	                                   
	 grid[5][0] = new Square(ud, 1, 5, u);
	 grid[5][1] = new Square(ud, 5, 5, d);
	 grid[5][2] = new Square(lr, 1, 4, r);
	 grid[5][3] = new Square(lr, 2, 4, r);
	 grid[5][4] = new Square(lr, 3, 4, r);
	 grid[5][5] = new Square(lr, 4, 4, r);
	 
	 int[] rowBurnValues = {2, 3, 2, 2, 4, 4};
	 int[] colBurnValues = {3, 4, 2, 4, 2, 2};
	 
	 Jan5 puzzle = new Jan5(grid, rowBurnValues, colBurnValues);
	 puzzle.printBoard();
	}
}
