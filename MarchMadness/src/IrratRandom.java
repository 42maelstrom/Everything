
public class IrratRandom {
	String number;
	public String name;
	int digit;
	int capacity;
	
	public IrratRandom(String name, String number) {
		this.name = name;
		int i = number.indexOf('.');
		number = number.substring(0,i) + number.substring(i+1);
		this.number = number;
		digit = 0;
		capacity = number.length();
	}
	
	public void setDigit(int digit) {
		this.digit = digit;
	}
	
	/**
	 * Returns an integer between 0-99 inclusive.
	 */
	public int nextInt() {
		int next = Integer.parseInt(number.substring(digit, digit + 2));
		digit+=2;
		if(digit + 2 > capacity)
			digit = 0;
		return next;
	}
	
	public void reset() {
		digit = 0;
	}
	
}	
