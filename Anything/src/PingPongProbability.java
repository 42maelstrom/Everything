import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class PingPongProbability {
	
	public static void test() {
		Scanner s = new Scanner(System.in);
		int trialNum;
		int p1wins; 
		//System.out.println("Trials: ");
		//int numOfTrials = Integer.parseInt(s.nextLine());
		//System.out.println("Probability: ");
		//double p = Double.parseDouble(s.nextLine());
		int numOfTrials = 1000000;
		boolean p1turn;
		boolean lastWasHit;
		boolean isOver;
		
		for(double p = .1; p < 1; p += .1) {
			double[] results = new double[10];
			for(int i = 0; i < 10; i++) {
				trialNum = 1;
				p1wins = 0;
				while(trialNum <= numOfTrials) {
					isOver = false;
					p1turn = true;
					lastWasHit = false;
					while(!isOver) {
						if(p1turn) {
							//System.out.println("Player one:");
						} else {
							//System.out.println("Player two:");
						}
						boolean currentIsHit = p >= Math.random();
						//System.out.println(currentIsHit);
						if(lastWasHit && !currentIsHit) {
							isOver = true;
						} else {
							lastWasHit = currentIsHit;
							p1turn = !p1turn;
						}
					}
					if(!p1turn) {
						//System.out.println("p1 wins");
						p1wins++;
					} else {
						//System.out.println("p2 wins");
					}
					trialNum++;
				}
				results[i] = (double)p1wins / numOfTrials;
			}
			System.out.println(p + ": " + Arrays.toString(results));
		}
	}
	
	public static void main(String[] args) {
		test();
	}
}
