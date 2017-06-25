
public class ThreadAgain implements Runnable{
	
	public void run() {
		System.out.println("HI");
	}
	
	public static void main(String[] args) {
		Thread t = new Thread(new ThreadAgain());
		t.start();
	}

}
