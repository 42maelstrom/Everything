import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Jan19 {
	public static final String DESCRIPTION = 
			"The 6 x 6 grid was tiled using only the two trominos shown,"
			+ " with reflection and rotations of these tiles permitted. The"
			+ " locations of the tiles' circles are indicated in the grid. Can you"
			+ " add the thickened lines to show the tiling?";
	public static final ArrayList<Class> HELPER_CLASSES =
			new ArrayList<Class>(Arrays.asList(Point.class, Tile.class));

	ArrayList<Point> dots;
	ArrayList<Point> tiled;
	
	
	public Jan19(ArrayList<Point> dots) {
		this.dots = dots;
		this.tiled = new ArrayList<Point>();
	}
	
	public void solve() {
		boolean didAnythingChange = false;
		do {
			//System.out.println("new round");
			didAnythingChange = false;
			for(Point dotP: dots) {
				if(!isTiled(dotP)) {
					//System.out.println("New point: " + dotP.toString());
					boolean isOneValid = false;
					boolean areTwoValid = false;
					Tile valid = null;
					
					for(Tile t: Tile.makeAllTileTypesAtPoint(dotP.x,  dotP.y)) {
						if(isValid(t)) {
							//System.out.println("hi");
							if(isOneValid == false) {
								isOneValid = true;
								valid = t;
							} else {
								areTwoValid = true;
								break;
							}
						}
					}
					
					if(isOneValid && !areTwoValid) {
						if(valid == null)
							System.out.println("This shouldn't be null");
						place(valid);
						didAnythingChange = true;
					}
				}
			}
			//String s = new Scanner(System.in).nextLine();
		} while(didAnythingChange == true);
		
		boolean isFilled = true;
		outer:
		for(int x = 0; x < 6; x++) {
			for(int y = 0; y < 6; y++) {
				if(!tiled.contains(new Point(x, y))) {
					isFilled = false;
					break outer;
				}
			}
		}
		if(isFilled) {
			System.out.println("Board completed!");
		} else {
			System.out.println("Sorry, I couldn't do it completely :(");
		}
	}
	
	private void place(Tile t) {
		for(Point p: t.points)
			tiled.add(p);
		
		System.out.println("New tile:");
		print(t);
	}
	
	private boolean isValid(Tile t) {
		
		for(Point tileP: t.points) {
			if(!isInbounds(tileP))
				return false;
			if(isTiled(tileP))
				return false;
			if(isDotted(tileP) && !t.dots.contains(tileP))
				return false;
		}
		
		for(Point dotP: t.dots) {
			if(!isDotted(dotP))
				return false;
		}
		
		ArrayList<Point> hypoTiled = new ArrayList<Point>();
		hypoTiled.addAll(tiled);
		for(Point p: t.points)
			hypoTiled.add(p);
		
		for(int x = 0; x < 6; x++) {
			for(int y = 0; y < 6; y++) {
				if(!hypoTiled.contains(new Point(x, y))
						&& (hypoTiled.contains(new Point(x - 1, y)) || !isInbounds(new Point(x - 1, y)))
						&& (hypoTiled.contains(new Point(x + 1, y)) || !isInbounds(new Point(x + 1, y)))
						&& (hypoTiled.contains(new Point(x, y - 1)) || !isInbounds(new Point(x, y - 1)))
						&& (hypoTiled.contains(new Point(x, y + 1)) || !isInbounds(new Point(x, y + 1)))) {
					//it's surrounded!
					//System.out.println("Surrounded: " + new Point(x, y).toString());
					return false;
				}
			}
		}
		
		return true;
	}
	
	private boolean isInbounds(Point p) {
		return p.x >= 0 && p.x < 6 && p.y >= 0 && p.y < 6;
	}
	
	private boolean isTiled(Point p) {
		return tiled.contains(p);
	}
	
	private boolean isDotted(Point p) {
		return dots.contains(p);
	}
	
	private boolean isInbounds(int x, int y) {
		return x >= 0 && x < 6 && y >= 0 && y < 6;
	}
	
	private boolean isTiled(int x, int y) {
		return tiled.contains(new Point(x, y));
	}
	
	private boolean isDotted(int x, int y) {
		return dots.contains(new Point(x, y));
	}
	
	private static void print(Tile t) {
		for(int y = 0; y < 6; y++) {
			for(int x = 0; x < 6; x++) {
				if(t.points.contains(new Point(x, y))) {
					if(t.dots.contains(new Point(x, y))) {
						System.out.print("*");
					} else {
						System.out.print("O");
					}
				} else {
					System.out.print("-");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		ArrayList<Point> dots = new ArrayList<Point>();
		dots.add(new Point(0, 0));
		dots.add(new Point(0, 2));
		dots.add(new Point(0, 3));
		dots.add(new Point(0, 4));
		dots.add(new Point(1, 2));
		dots.add(new Point(2, 0));
		dots.add(new Point(2, 3));
		dots.add(new Point(2, 5));
		dots.add(new Point(3, 0));
		dots.add(new Point(3, 2));
		dots.add(new Point(3, 3));
		dots.add(new Point(3, 5));
		dots.add(new Point(4, 2));
		dots.add(new Point(4, 5));
		dots.add(new Point(5, 0));
		dots.add(new Point(5, 3));
		new Jan19(dots).solve();	
		
	}
}
