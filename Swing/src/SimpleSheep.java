
public class SimpleSheep implements Runnable{
	
	public static void main(String[] args) {
		(new Thread(new SimpleSheep())).start();
	}

	public void run() {
		
	}
	
	public void dispNextSheep() {
		System.out.println("Here comes the next sheep");
	}
	
	public void getNumberOfSheep() {
		
	}
	
	
}
