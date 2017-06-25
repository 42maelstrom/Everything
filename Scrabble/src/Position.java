
public class Position {
	public int x, y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position(String s) {
		this(Integer.valueOf(s.replaceAll(" ", "").substring(1, 
			s.replaceAll(" ", "").indexOf(','))), 
			Integer.valueOf(s.replaceAll(" ", "").substring(s.replaceAll(" ", "").indexOf(',') + 1,
				s.replaceAll(" ", "").length() - 1)));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
