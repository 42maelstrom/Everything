
public class Space {
	public char ch;
	public int r, c;
	
	public Space(int r, int c, char ch) {
		this.ch = ch;
		this.r = r;
		this.c = c;
	}
	
	public Space(int r, int c) {
		this(r, c, ' ');
	}
	
	@Override
	public String toString() {
		return "" + ch + " (" + c + ", " + r + ")";
	}
}
