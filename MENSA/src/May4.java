import java.util.ArrayList;

public class May4 {
	public static void main(String[] args) {
		ArrayList<Point> dots = new ArrayList<Point>();
		dots.add(new Point(0, 0));
		dots.add(new Point(2, 0));
		dots.add(new Point(4, 0));
		dots.add(new Point(1, 1));
		dots.add(new Point(5, 1));
		dots.add(new Point(0, 2));
		dots.add(new Point(3, 2));
		dots.add(new Point(1, 3));
		dots.add(new Point(4, 3));
		dots.add(new Point(5, 3));
		dots.add(new Point(0, 4));
		dots.add(new Point(2, 4));
		dots.add(new Point(5, 4));
		dots.add(new Point(1, 5));
		dots.add(new Point(2, 5));
		dots.add(new Point(4, 5));
		new Jan19(dots).solve();
	}
}
