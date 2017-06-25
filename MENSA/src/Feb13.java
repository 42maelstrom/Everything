
public class Feb13 {
	public static final String DESCRIPTION = "Each of the six letters stands for"
			+ " a different letter from 1 to 9. Using the four clues, can you deduce"
			+ " the number represented by each letter?";
	
	public Feb13() {
		
	}
	
	public void solve() {
		for(int i = 0; i <= 999999; i++) {
			String s = String.valueOf(i);
			
			if(s.length() != 6 || s.contains("0"))
				continue;
			
			int a = Integer.parseInt(s.substring(0, 1));
			int b = Integer.parseInt(s.substring(1, 2));
			int c = Integer.parseInt(s.substring(2, 3));
			int d = Integer.parseInt(s.substring(3, 4));
			int e = Integer.parseInt(s.substring(4, 5));
			int f = Integer.parseInt(s.substring(5, 6));
			
			
			if(a + d == 9 && b + e == 9 && c + f == 9
					&& d + e + f == 17 && a + b == c && a*e == 12)
				System.out.println(a + " " + b + " " + c + "\n" + d + " " + e + " " + f);
					
		}
	}
	
	public static void main(String[] args) {
		new Feb13().solve();
	}
}
