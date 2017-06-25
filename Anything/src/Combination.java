import java.util.ArrayList;


public class Combination {
	private int n;
	private int r;
	ArrayList<Integer> pos;
	
	public Combination(int n, int r) {
		this.n = n;
		this.r = r;
		pos = new ArrayList<Integer>(r);
		for(int i = 0; i < r; i++) {
			pos.add(i);
		}
	}
	
	public void goThroughCombinations() {
		goThrough(0);
	}
	
	private void goThrough(int index) {
		int startIndex = pos.get(index);
		for(int i = 0; i < n - r - pos.get(index) + index + 1; i++) {
			if(index != r - 1) {
				goThrough(index + 1);
			} else {	
				//System.out.println(pos.toString());	
			}
			pos.set(index, pos.get(index) + 1);
		}
		pos.set(index, startIndex + 1);
	}
	
	public static void main(String[] args) {
		Combination test = new Combination(5, 3);
		test.goThroughCombinations();
	}
}