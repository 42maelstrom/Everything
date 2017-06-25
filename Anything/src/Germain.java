
public class Germain {

	public static void printNumbers() {
		for(int i = 1; i <= 100; i++) {
			if(i % 3 == 0) {
				if(i % 5 ==0) {
					System.out.println("germain");
				} else {
					System.out.println("apm");
				}
			} else if(i % 5 == 0) {
				System.out.println("crt");
			} else {
				System.out.println(i);
			}
		}
	}
	
	public static void main(String[] args) {
		printNumbers();
	}
}
