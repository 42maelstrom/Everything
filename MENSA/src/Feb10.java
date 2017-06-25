import java.util.ArrayList;
import java.util.Arrays;


public class Feb10 {
	public static final String DESCRIPTION = "Put an X or O in each empty cell of"
			+ " this grid so that four consecutive X's or O's do not appear"
			+ " horizontally, vertically, or diagonally. The answer is unique.";
	public static final ArrayList<Class> HELPER_CLASSES = new ArrayList<Class>(Arrays.asList(Point.class));
	
	private char[][] grid;
	private int rows, cols;
	
	public Feb10(char[][] grid) {
		this.grid = grid;
		rows = grid.length;
		cols = grid[0].length;
	}
	
	public void solve() {
		ArrayList<Point> unfilled = new ArrayList<Point>();
		for(int x = 0; x < cols; x++) {
			for(int y = 0; y < rows; y++) {
				if(grid[y][x] == ' ')
					unfilled.add(new Point(x, y));
			}
		}
		
		boolean didChange = true;
		while(!unfilled.isEmpty() && didChange == true) {
			didChange = false;
			for(int r = 0; r < rows; r++) {
				goingThroughPoints:
				for(int c = 0; c < cols; c++) {
					if(grid[r][c] == ' ') {
						for(int dX = -1; dX <= 1; dX++) {
							for(int dY = -1; dY < 1; dY++) {
								if(dX == 0 && dY == 0 || dX == 1 && dY == 0)
									continue;
								
								//for X's
								//go "left"
								int rL = r + dY, cL = c + dX;
								int count = 0;
								
								while(isInbounds(rL, cL) && grid[rL][cL] == 'X' && count < 3) {
									count++;
									rL = rL + dY;
									cL = cL + dX;
								}
								
								//go "right"
								int rR = r - dY;
								int cR = c - dX;

								while(isInbounds(rR, cR) && grid[rR][cR] == 'X' && count < 3) {
									count++;
									rR = rR - dY;
									cR = cR - dX;
								}
								
								if(count >= 3) {
									grid[r][c] = 'O';
									unfilled.remove(new Point(c, r));
									System.out.println("O " + new Point(c, r).toString());
									didChange = true;
									continue goingThroughPoints;
								}
								
								//for O's
								//go "left"
								rL = r + dY;
								cL = c + dX;
								count = 0;
								
								while(isInbounds(rL, cL) && grid[rL][cL] == 'O' && count < 3) {
									count++;
									rL = rL + dY;
									cL = cL + dX;
								}
								
								//go "right"
								rR = r - dY;
								cR = c - dX;

								while(isInbounds(rR, cR) && grid[rR][cR] == 'O' && count < 3) {
									count++;
									rR = rR - dY;
									cR = cR - dX;
								}
								
								if(count >= 3) {
									grid[r][c] = 'X';
									unfilled.remove(new Point(c, r));
									didChange = true;
									System.out.println("X " + new Point(c, r).toString());
								}
								
							}
						}
					}
				}
			}
		}
		
		for(int r = 0; r < rows; r++) {
			System.out.println();
			for(int c = 0; c < cols; c++) {
				System.out.print(Character.toString((Character)grid[r][c]) + ' ');
			}
		}
	}
	
	private void printGrid() {
		for(int r = 0; r < rows; r++) {
			System.out.println();
			for(int c = 0; c < cols; c++) {
				System.out.print(Character.toString((Character)grid[r][c]) + ' ');
			}
		}
	}
	
	private  boolean isInbounds(int r, int c) {
		return r >= 0 && r < rows && c >= 0 && c < cols;
	}
	
	public static void main(String[] args0) {
		char[][] grid = { {'O', 'X', ' ', ' ', 'X', ' ', ' '}, 
											{' ', ' ', ' ', 'O', ' ', 'O', 'X'}, 
											{' ', ' ', ' ', ' ', 'O', ' ', ' '}, 
											{'O', ' ', 'O', ' ', ' ', 'O', 'X'}, 
											{' ', 'X', ' ', 'X', ' ', ' ', ' '}, 
											{' ', 'O', 'O', ' ', 'O', 'O', ' '}, 
											{'O', 'X', 'O', 'X', ' ', 'X', ' '} };
		new Feb10(grid).solve();
	}
}
