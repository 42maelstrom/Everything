
public class FibTester {

	public static void main(String[] args) {
		for(long i = 1; i < 100; i++) {
			System.out.println(i + ": " + recFib(i));
		}
	}
	
	public static int recFib(long i) {
		if(i < 3)
			return 1;
		return recFib(i - 1) + recFib(i-2);
	}

}
