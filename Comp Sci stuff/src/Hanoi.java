import java.util.ArrayList;


public class Hanoi {
	
	public static void main(String[] args) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		ArrayList<Integer> b = new ArrayList<Integer>();
		ArrayList<Integer> c = new ArrayList<Integer>();
		
		for(int numOfDiscs = 3; numOfDiscs <4; numOfDiscs++)
		{
			for(int i = 1; i <= numOfDiscs; i++)
			{
				a.add(i);
			}
			//move(numOfDiscs, a, b, c);
			//System.out.println("Waiting on recHanoi");
			recHanoi(numOfDiscs, 'a', 'b', 'c');
			System.out.println(numOfDiscs);
			//System.out.println("Waiting on move");
		}
	}

	public static void move(int numOfDiscs, ArrayList<Integer> startPoint,
			ArrayList<Integer> midPoint, ArrayList<Integer> endPoint) {
		
		if (numOfDiscs == 1) {
			//System.out.println(startPoint.toString());
			//System.out.println(midPoint.toString());
			//System.out.println(endPoint.toString());
			//System.out.println();
			endPoint.add(0,startPoint.remove(0));
		} else {
			move(numOfDiscs - 1, startPoint, endPoint, midPoint);
			move(1, startPoint, midPoint, endPoint);
			move(numOfDiscs - 1, midPoint, startPoint, endPoint);
		}
		
	}
	
	public static void recHanoi(int n, char a, char b, char c)
	{
		if(n > 1)
			recHanoi(n-1, a, c, b);
		System.out.println("Move disc " + n + " from " + a + " to " + c);
		if(n > 1)
			recHanoi(n - 1, b, a, c);
	}

}
