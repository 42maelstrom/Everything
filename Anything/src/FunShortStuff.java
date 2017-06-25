import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class FunShortStuff {
	public static void booWords() {
		Dictionary d = new Dictionary();
		for(String w: Dictionary.dict) {
			if(w.contains("boo"))
				System.out.println(w);
		}
	}
	
	public static void xuxuxWords() {
		for(String w: Dictionary.dict) {
			if(isXuxuxWord(w)) {
				System.out.println(w);
			}
		}
	}
	
	public static boolean isXuxuxWord(String w) {
		// xuxux
		// xxuxxuxx
		// xuuxuux
		// xxuuxxuuxx
		String x = "";
		String u = "";
		
		switch(w.length()) {
			case 5:
				x = "" + w.charAt(0);
				u = "" + w.charAt(1);
				break;
			case 7:
				x = "" + w.charAt(0);
				u = "" + w.substring(1, 3);
				break;
			case 8:
				x = "" + w.substring(0, 2);
				u = "" + w.substring(2, 3);
				break;
			case 10:
				x = "" + w.substring(0, 2);
				u = "" + w.substring(2, 4);
				break;
			default:
				return false;
		}
		if((x + u + x + u + x).equals(w)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void scrabble() {
		while(true) {
			int i = Integer.valueOf(new Scanner(System.in).nextLine());
			int d;
			
			if(i == 25) {
				System.out.println("boring");
				continue;
			}else if (i >= 50) {
				System.out.println("WOW! Take off a clothing, opponent!");
				continue;
			} else if(i < 25) {
				System.out.println("Chance of losing item of clothing: " + (25 - i) + "/ 25 = " + (25 - i) / 25d);
				d = 25 - i;
			} else {
				System.out.println("Look at you!");
				System.out.println("Chance of gaining item of clothing: " + (i-25) + "/ 25 = " + (i-25) / 25d);
				d = i - 25;
			}
				int r = (new Random().nextInt(25) + 1);
				System.out.println("Dice roll: " + r);
				if( r<= d*3) {
					if(i < 25) {
						System.out.println("You lose an item, haaaaa");
					} else {
						System.out.println("You gain an item, lucky");
					}
				} else {
					System.out.println("Nothing happens");
				}
				
		}
	}
	
	public static void printWords() {
		scrabble();
	}
	
	public static void main(String[] args) {
		xuxuxWords();
	}
	
	
	
	
}
