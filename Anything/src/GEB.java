/**
 * For having fun with recursive stuff from Gšdel, Escher, Bach book
 */
public class GEB {

	public static int g(int n){
		return (n == 0 ? 0 : n - g(g(n-1)));
	}
	
	public static int h(int n) {
		return (n == 0 ? 0 : n - h(h(n-1)));
	}
	
	public static int f(int n) {
		return (n == 0 ? 1 : n - m(f(n-1)));
	}
	
	public static int m(int n) {
		return (n == 0 ? 0 : n - f(m(n-1)));
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 100; i++)
			System.out.println(i + ": "+ m(i));
	}
}
