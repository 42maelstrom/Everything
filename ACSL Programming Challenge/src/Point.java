public class Point {
		int x, y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public boolean equals(Object other) {
			if(other instanceof Point) {
				Point p = (Point)other;
				return this.x == p.x && this.y == p.y;
			} else {
				return false;
			}
		}
		
		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}