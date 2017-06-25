import java.util.ArrayList;
import java.util.Scanner;


public class Mar11 {
	public static final String DESCRIPTION = "Subdivide this region along the grid"
			+ " lines into non-overlapping squares and rectangles. Each rectangle or"
			+ " square must contain exactly one number that indicates how many small"
			+ " cells make up its area";
	
	int width, height;
	ArrayList<Point> numPoints;	
	ArrayList<Integer> numNums;
	ArrayList<Boolean> isSolved;
	ArrayList<ArrayList<Point>> numBoxSizes;
	
	/**
	 * 0 - empty
	 * 1 - taken
	 * 2 - taken by a number
	 */
	int[][] field;
	
	public Mar11(int width, int height, ArrayList<Point> numPoints, ArrayList<Integer> numNums) {
		this.numPoints = numPoints;
		this.numNums = numNums;
		this.width = width;
		this.height = height;
		
		field = new int[height][width];
		
		for(int r = 0; r < height; r++) {
			for(int c = 0; c < width; c++) {
				field[r][c] = 0;
			}
		}
		
		for(Point p: numPoints) {
			field[p.y][p.x] = 2;
		}
		
		isSolved = new ArrayList<Boolean>(numPoints.size());
		numBoxSizes = new ArrayList<ArrayList<Point>>();
		for(int i = 0; i < numPoints.size(); i++) {
			isSolved.add(false);
			numBoxSizes.add(getBoxSizes(numNums.get(i)));
		}
		
	}
	
	public void solve() {
		//cycle through until all of the points have been solved.
		//for each point
		//for each box size possible (rotations included here)
		//for each position of the box size
		//if it's possible, add it to the list of possible boxes
		//if there is more than one possible place, do nothing
		//if there was only one possible place, set it down on the map.
		
		printField();
		while(isSolved.contains(false)) {
			points:
			for(int i = 0; i < numPoints.size(); i++) {
				Point p = numPoints.get(i);
				int n = numNums.get(i);
				System.out.println("P: " + p.toString() + " N: " + n);
				String s = new Scanner(System.in).nextLine();
				ArrayList<Point> boxSizes = numBoxSizes.get(i);
				
				Point possiblePoint = null;
				Point possibleSize = null;
				
				for(Point boxSize: boxSizes) {
					System.out.println("Box size = " + boxSize.toString());
					for(int xPos = 0; xPos < boxSize.x; xPos++) {
						boxPlacing:
						for(int yPos = 0; yPos < boxSize.y; yPos++) {
							//this is where the number is in the box made.
							Point relativeNumPos = new Point(xPos, yPos);
							
							for(int x = 0; x < boxSize.x; x++) {
								for(int y = 0; y < boxSize.y; y++) {
									int actualX = p.x - xPos + x;
									int actualY = p.y - yPos + y;
									System.out.println("relativePos: " + relativeNumPos.toString()
											+ " actual: (" + actualX + ", " + actualY + ")");
									boolean isEigenNumPoint = actualX == p.x && actualY == p.y;
									if(!isInbounds(actualX, actualY) || (field[actualY][actualX] != 0
											&& !isEigenNumPoint)) {
										if(!isInbounds(actualX, actualY)) {
											System.out.println("out of bounds");
										} else if(field[actualY][actualX] != 0) {
											System.out.println("taken");
										}
										//String s = new Scanner(System.in).nextLine();
										continue boxPlacing;
									}
									System.out.println("Valid");
									//String s = new Scanner(System.in).nextLine();
								}
							}
							//wow, it worked in all of the locations!
							if(possiblePoint == null) {
								possiblePoint = relativeNumPos;
								possibleSize = boxSize;
							} else {
								//there was also another one that worked so nothing can be found out.
								continue points;
							}
						} // end yPos loop
					} // end xPos loop
				}//end boxSizes loop
				
				if(possiblePoint != null) {
					System.out.println("Solved a number!");
					System.out.println(n + " at " + p.toString() + " goes in box size " 
							+ possibleSize.toString() + " in relative position " 
							+ possiblePoint.toString());
					
					for(int x = 0; x < possibleSize.x; x++) {
						for(int y = 0; y < possibleSize.y; y++) {
							int actualX = p.x - possiblePoint.x + x;
							int actualY = p.y - possiblePoint.y + y;
							System.out.println(new Point(actualX, actualY).toString());
							field[actualY][actualX] = 1;
						}
					}
					
					printField();
				}
			}//end point loop
		}//end method
	}
	
	private boolean isInbounds(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}
	
	private void printField() {
		for(int r = 0; r < field.length; r++) {
			for(int c = 0; c < field[0].length; c++) {
				if(field[r][c] == 1)
					System.out.print("X ");
				if(field[r][c] == 2)
					System.out.print("N ");
				if(field[r][c] == 0) 
					System.out.print("_ ");
			}
			System.out.println();
		}
		String s = new Scanner(System.in).nextLine();
	}
	
	/**
	 * basically factor the number.
	 */
	private static ArrayList<Point> getBoxSizes(int n) {
		ArrayList<Point> sizes = new ArrayList<Point>();
		for(int i = 1; i <= n; i++) {
			if(n % i == 0) {
				sizes.add(new Point(i, n / i)); 
			}
		}
		return sizes;
	}
	
	public static void main(String[] args) {
		ArrayList<Point> numPoints = new ArrayList<Point>();
		ArrayList<Integer> numNums = new ArrayList<Integer>();
		
		numPoints.add(new Point(0, 0));	numNums.add(8);
		numPoints.add(new Point(6, 1)); numNums.add(12);
		numPoints.add(new Point(8, 1)); numNums.add(9);
		numPoints.add(new Point(0, 2)); numNums.add(6);
		numPoints.add(new Point(2, 2)); numNums.add(3);
		numPoints.add(new Point(3, 3)); numNums.add(3);
		numPoints.add(new Point(8, 3)); numNums.add(3);
		numPoints.add(new Point(1, 4)); numNums.add(9);
		numPoints.add(new Point(8, 5)); numNums.add(9);
		numPoints.add(new Point(2, 6)); numNums.add(6);
		numPoints.add(new Point(6, 7)); numNums.add(12);
		
		new Mar11(10, 8, numPoints, numNums).solve();
	}
}
