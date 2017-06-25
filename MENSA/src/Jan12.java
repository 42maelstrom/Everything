import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Jan12 {
	public static final String DESCRIPTION =
			"In this maze, start on the star and end on the flag by exact"
			+ " count as you follow these rules: \n1. Move 3 cells, then 2 cells,"
			+ " then 1 cell:\nrepeat in order as needed.\n2. Move horizontally,"
			+ " vertically, or diagonally\nbut always in a straight line.\3. Do not"
			+ " move forward in the same direction nor go in the reverse direction"
			+ " of the move you just made.\n4. Do not land on or even cross over"
			+ " any of the black cells.\5. Do not land on the same cell more than"
			+ " once.\nCan you find the shortest path?";
	public static final ArrayList<Class> HELPER_CLASSES = 
			new ArrayList<Class>(Arrays.asList(Point.class));

	public final ArrayList<Point> BLACK_POINTS;
	
	public Jan12(ArrayList<Point> blackPoints) {
		this.BLACK_POINTS = blackPoints;
	}
	
	public void solve() {
		Point current = new Point(0,0);
		ArrayList<Point> usedPs = new ArrayList<Point>();
		Point direction = new Point(-5, -5);
		int moveNum = 3;
		solve(current, usedPs, direction, moveNum);
	}
	
	private ArrayList<Point> solve(Point currentP, ArrayList<Point> usedPs,
			Point lastDir, int moveDist) {
		
		System.out.println("P: " + currentP.toString() + " " + moveDist + " " + 
						   "lD: " + lastDir.toString() + " Depth: " + usedPs.size() + " Used = " + usedPs.toString());
		
		if(currentP.equals(new Point(5, 5))) {
			usedPs.add(currentP);
			return usedPs;
		}

		for(int dx = -1; dx < 2; dx++) {
			outer:
			for(int dy = -1; dy < 2; dy++) {
				
				if(dx == 0 && dy == 0) 
					continue;
				
				if(new Point(dx, dy).equals(lastDir) || new Point(-dx, -dy).equals(lastDir))
					continue;
				
				Point newP = currentP.move(moveDist*dx, moveDist*dy);
				if(usedPs.contains(newP))
					continue;
				
				if(!(newP.x >= 0 && newP.x < 6 && newP.y >= 0 && newP.y < 6))
					continue;
				
				for(int i = 1; i <= moveDist; i++) {
					Point nextStep = new Point(currentP.x + dx*i, currentP.y + dy*i);
					
					if(isBlack(nextStep))
						continue outer;
				}

				ArrayList<Point> newUsedPs = new ArrayList<Point>();
				newUsedPs.addAll(usedPs);
				newUsedPs.add(currentP);
				Point newLastDir = new Point(dx, dy);
				int newMoveDist = moveDist - 1;
				if(newMoveDist == 0)
					newMoveDist = 3;
				ArrayList<Point> solution = solve(newP, newUsedPs, newLastDir, newMoveDist);
				
				if(solution != null) {
					return solution;
				}
			}
		}
		System.out.println("Dead end at " + currentP.toString());
		return null;
	}
	
	private boolean isBlack(Point p) {
		return BLACK_POINTS.contains(p);
	}
	
	public static void main(String[] args) {
		ArrayList<Point> blackPoints = new ArrayList<Point>();
		blackPoints.add(new Point(0, 2));
		blackPoints.add(new Point(1, 3));
		blackPoints.add(new Point(3, 2));
		blackPoints.add(new Point(3, 3));
		blackPoints.add(new Point(4, 4));
		blackPoints.add(new Point(5, 2));
	
		new Jan12(blackPoints).solve();
	}
}
