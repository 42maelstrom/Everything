import java.util.ArrayList;


public class Jan25 {

	public static final String NAME = "WIN THE LOTTERY";
	public static final String DESCRIPTION = "A lottery uses only three-digit"
			+ " numbers. the number that will be awarded the grand prize was selected"
			+ " before the close of sales, and I was told the following three things"
			+ " about the number. I'm going to rush out to get some tikets, which"
			+ " cost $5 each. If I want to guarantee that I will share in the prize"
			+ " money, what is the least amount that I will need to spend?";
	
	public Jan25() {
	 
	}
 
	public void solve() {
		ArrayList<String> possibleNumbers = new ArrayList<String>();
		
		for(int i1 = 0; i1 < 10; i1++) {
			for(int i2 = 0; i2 < 10; i2++) {
				for(int i3 = 0; i3 < 10; i3++) {
					if(isValidTicket(i1, i2, i3)) {
						possibleNumbers.add(Integer.toString(i3) + Integer.toString(i2)
								+ Integer.toString(i1));
						System.out.println(possibleNumbers.get(possibleNumbers.size() - 1));
					}
				}
			}
		}
		
		System.out.println("Lottery tickets needed: " + possibleNumbers.size());
	}
	
	//automatically makes sure there are no duplicates here too
	private boolean isValidTicket(int... nums) {
		boolean hasConsecutive = false;
		boolean hasSquare = false;
		for(int i = 0; i < nums.length - 1; i++) {
			if(nums[i] >= nums[i+1])
				return false;
			
			if(!hasConsecutive) {
				if(nums[i] == nums[i+1] - 1)
					hasConsecutive = true;
			}
			
			if(!hasSquare) {
				for(int j = i + 1; j < nums.length; j++) {
					if(nums[i] == nums[j]*nums[j] || nums[j] == nums[i]*nums[i])
						hasSquare = true;
				}
			}
	
		}
		return hasConsecutive && hasSquare;
	}
 
	public static void main(String[] args) {
		new Jan25().solve();
	}
}
