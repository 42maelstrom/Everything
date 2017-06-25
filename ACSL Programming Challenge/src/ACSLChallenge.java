import java.util.ArrayList;
import java.util.Arrays;


public class ACSLChallenge {
	
	
	public static String generateEncodedString(String input) {
		String alpha = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int numOfLetters = (input.length() - 1) / 2;
		String answer = "";
		int currentLetterValue = 1; //starts with A
	
		for(int i = 0; i < numOfLetters; i++) {
			int lettersToCount = getLettersToCount(
					alpha.indexOf(input.charAt(2*i)), //the letter
					Integer.valueOf(Character.toString(input.charAt(2*i + 1)))); //the number
			currentLetterValue = currentLetterValue + lettersToCount;
			
			while(currentLetterValue < 0)
				currentLetterValue += 26;
			
			currentLetterValue = currentLetterValue % 26;
			char letterToAdd = alpha.charAt(currentLetterValue);
			answer+=letterToAdd;
		}
		
		return answer;		
	}
	
	/**
	 * @param letter - the numerical value of the letter input
	 * @param rule - the rule number
	 * @return - the number of letters to count
	 */
	private static int getLettersToCount(int letter, int rule) {
		if(rule == 1) {
			return 2*letter;
		} else if(rule == 2) {
			return 5*(letter % 3);
		} else if(rule == 3) {
			double quotient = (double)letter / (double)4;
			double integerPart = Math.floor(quotient);
			int answer = (int)integerPart * -8;
			return answer;
		} else if(rule == 4) {
			double truncatedSqrt = Math.floor(Math.sqrt(letter));
			int answer = (int)truncatedSqrt * -12;
			return answer;
		} else {
			int sumOfFactors = 0;
			for(int i = 1; i <= letter; i++) {
				if(letter % i == 0) {
					sumOfFactors+=i;
				}
			}
			return sumOfFactors*10;
		}
	}
	
	public static String simplifyConic(String input) {
		int i = 0;
		int a = 0, c = 0, d = 0, e = 0, f = 0;
		String sub = "";
		String lookFor = "x^2";
		int pos = input.indexOf(lookFor, i);
		if(pos != -1) {
			if(pos - i == 0) {
				a = 1;
			} else {
				a = Integer.valueOf(input.substring(i, pos));
			}
			i = pos + lookFor.length();		
		}
		
		lookFor = "y^2";
		pos = input.indexOf(lookFor, i);
		if(pos != -1) {
			if(pos - i == 1) {
				c = 1;
				if(input.charAt(i) == '-')
					c = -1;
			} else {
				sub = input.substring(i, pos);
				if(sub.charAt(0) == '+')
					sub = sub.substring(1);
				c = Integer.valueOf(sub);
			}
			i = pos + lookFor.length();
		}
		
		lookFor = "x";
		pos = input.indexOf(lookFor, i);
		if(pos != -1) {
			if(pos - i == 1) {
				d = 1;
				if(input.charAt(i) == '-')
					d = -1;
			} else {
				sub = input.substring(i, pos);
				if(sub.charAt(0) == '+')
					sub = sub.substring(1);
				d = Integer.valueOf(sub);
			}
			i = pos + lookFor.length();
		} 
		
		lookFor = "y";
		pos = input.indexOf(lookFor, i);
		if(pos != -1) {
			if(pos - i == 1) {
				e = 1;
				if(input.charAt(i) == '-')
					e = -1;
			} else {
				sub = input.substring(i, pos);
				if(sub.charAt(0) == '+')
					sub = sub.substring(1);
				e = Integer.valueOf(sub);
			}
			i = pos + lookFor.length();
		}
		
		if(i == input.length() - 2) {
			f = 0;
		} else {
			sub = input.substring(i, input.length() - 2);
			if(sub.charAt(0) == '+')
				sub = sub.substring(1);
			f = Integer.valueOf(sub);
		}
		
		//System.out.println("a=" + a + ", c=" + c + ", d=" + d + ", e=" + e + ", f=" + f);
		
		String answer;
		if(a == c) {
			double centerX = -d / (2*a);
			double centerY = -e / (2*c);
			double radius = Math.sqrt((-f + d*d / (4*a) + e*e / (4*c)) / a);
			String centerP = "(" + Double.toString(centerX) + "," + Double.toString(centerY) + ")";
			
			answer = "Circle, " + centerP + ", " + Double.toString(radius);
			return answer;
		} else if((a < 0 && c < 0) || (a > 0 && c > 0)) {
			double centerX = -d / (2*a);
			double centerY = -e / (2*c);
			String centerP = "(" + Double.toString(centerX) + "," + Double.toString(centerY) + ")";
			
			double majorAxis;
			if(c > a) {
				majorAxis = Math.sqrt(c)*2;
			} else {
				majorAxis = Math.sqrt(a)*2;
			}
			
			answer = "Ellipse, " + centerP + ", " + Double.toString(majorAxis);
			return answer;
		} else if(a == 0 || c == 0) {
			//it's a parabola
			double centerX, centerY;
			String centerP;
			String axisOfSym;

			if(c == 0) {
				centerX = -d / (2*a);
				centerY = -(a*f + d*d / (4*a)) / e;
				axisOfSym = "x=" + Double.toString(centerX);
			} else {
				centerX = -(c*f + e*e / (4*c)) / d;
				centerY = -e / (2*c);
				axisOfSym = "y=" + Double.toString(centerY);
			}
			
			centerP = "(" + Double.toString(centerX) + "," + Double.toString(centerY) + ")";
			
			answer = "Parabola, " + centerP + ", " + axisOfSym;
			return answer;
		} else {
			double centerX = -d / (2*a);
			double centerY = -e / (2*c);
			String centerP = "(" + Double.toString(centerX) + "," + Double.toString(centerY) + ")";
			
			String principalAxis;
			if(c < 0) {
				principalAxis = "y=" + Double.toString(centerY);
			} else {
				principalAxis = "x=" + Double.toString(centerX);
			}
			
			answer = "Hyperbola, " + centerP + ", " + principalAxis;
			return answer;
		}		
	}
	
	public static String producePalindrome(String number, int base) {
		if(isPalindrome(number))
			return number;
		
		for(int i = 0; i < 10; i++) {
			number = add(number, reverse(number), base);
			if(isPalindrome(number))
				return number;
			
		}
		
		return "NONE, " + number;
	}
	
	private static String add(String num1, String num2, int base) {
		int baseTenSum = convertToBaseTen(num1, base) + convertToBaseTen(num2, base);
		String ans = convertFromBaseTen(baseTenSum, base);
		return ans;
	}
	
	private static String convertFromBaseTen(int number, int base) {
		String bases = "0123456789ABCDEFGH";
		int p = 0;
		
		while(Math.pow(base, p) <= number)
			p++;
		
		p = p - 1;
		
		String ans = "";
		
		while(p >= 0) {
			int digit = number / (int)Math.pow(base, p);
			number = number % (int)Math.pow(base, p);
			ans = ans + bases.charAt(digit);
			p--;
		}
		
		return ans;
	}
	
	private static int convertToBaseTen(String number, int base) {
		String bases = "0123456789ABCDEFGH";
		int ans = 0;
		int j = 0;
		for(int i = number.length() - 1; i >= 0; i--) {
			ans = ans + bases.indexOf(number.charAt(i))*(int)Math.pow(base, j);
			j++;
		}
		
		return ans;
	}
	
	private static String reverse(String number) {
		String ans = "";
		for(int i = number.length() - 1; i >= 0; i--) {
			ans += number.charAt(i);
		}
		
		return ans;
	}
	
	private static boolean isPalindrome(String number) {
		for(int i = 0; i < number.length() / 2; i++) {
			if(number.charAt(i) != number.charAt(number.length() - i - 1))
				return false;
		}
		
		return true;
	}
	
	private static int findGreatestJumpsPossible(Point myPos, ArrayList<Point> theirPieces, ArrayList<Point> allPieces, boolean isKing) {
		boolean canJumpLeft = isInbounds(myPos.x - 2, myPos.y + 2)
				&& theirPieces.contains(new Point(myPos.x - 1, myPos.y + 1))
				&& ! allPieces.contains(new Point(myPos.x - 2, myPos.y + 2)); 
		boolean canJumpRight = isInbounds(myPos.x + 2, myPos.y + 2)
				&& theirPieces.contains(new Point(myPos.x + 1, myPos.y + 1))
				&& ! allPieces.contains(new Point(myPos.x + 2, myPos.y + 2));
		
		boolean canJumpDownLeft = isKing && isInbounds(myPos.x - 2, myPos.y - 2)
				&& theirPieces.contains(new Point(myPos.x - 1, myPos.y - 1))
				&& ! allPieces.contains(new Point(myPos.x - 2, myPos.y - 2));
		boolean canJumpDownRight = isKing && isInbounds(myPos.x + 2, myPos.y - 2)
				&& theirPieces.contains(new Point(myPos.x + 1, myPos.y - 1))
				&& ! allPieces.contains(new Point(myPos.x + 2, myPos.y - 2));
		
		int maxJumps = 0;
		
		if(canJumpLeft) {
			ArrayList<Point> newTheirPieces = new ArrayList<Point>();
			newTheirPieces.addAll(theirPieces);
			newTheirPieces.remove(new Point(myPos.x - 1, myPos.y + 1));
			
			boolean isNowKing = isKing;
			if(myPos.y == 5)
				isNowKing = true;
			
			int maxJumpsLeft = 1 + findGreatestJumpsPossible(new Point(myPos.x - 2, myPos.y + 2), newTheirPieces, allPieces, isNowKing);
			
			if(maxJumpsLeft > maxJumps)
				maxJumps = maxJumpsLeft;
		} 
		
		if(canJumpRight) {
			ArrayList<Point> newTheirPieces = new ArrayList<Point>();
			newTheirPieces.addAll(theirPieces);
			newTheirPieces.remove(new Point(myPos.x + 1, myPos.y + 1));
			
			boolean isNowKing = isKing;
			if(myPos.y == 5)
				isNowKing = true;
			
			int maxJumpsRight = 1 + findGreatestJumpsPossible(new Point(myPos.x + 2, myPos.y + 2), newTheirPieces, allPieces, isNowKing);
				
			if(maxJumpsRight > maxJumps)
				maxJumps = maxJumpsRight;
		}
		
		if(canJumpDownLeft) {
			ArrayList<Point> newTheirPieces = new ArrayList<Point>();
			newTheirPieces.addAll(theirPieces);
			newTheirPieces.remove(new Point(myPos.x - 1, myPos.y - 1));
			
			boolean isNowKing = true;
			
			int maxJumpsRight = 1 + findGreatestJumpsPossible(new Point(myPos.x - 2, myPos.y - 2), newTheirPieces, allPieces, isNowKing);
			
			if(maxJumpsRight > maxJumps)
				maxJumps = maxJumpsRight;
		} 
		
		if(canJumpDownRight) {
			ArrayList<Point> newTheirPieces = new ArrayList<Point>();
			newTheirPieces.addAll(theirPieces);
			newTheirPieces.remove(new Point(myPos.x + 1, myPos.y - 1));
			
			boolean isNowKing = true;
			
			int maxJumpsRight = 1 + findGreatestJumpsPossible(new Point(myPos.x + 2, myPos.y - 2), newTheirPieces, allPieces, isNowKing);
			
			if(maxJumpsRight > maxJumps)
				maxJumps = maxJumpsRight;
		}
		
		return maxJumps;
		
	}
	
	private static int findGreatestJumpsPossible(String input) {
		
		ArrayList<Point> myPieces = new ArrayList<Point>();
		int numOfMyPieces = Integer.parseInt(Character.toString(input.charAt(0)));
		int i = 2;
		for(int n = 0; n < numOfMyPieces; n++) {
			int x = Integer.parseInt(Character.toString(input.charAt(i)));
			i+=2;
			int y = Integer.parseInt(Character.toString(input.charAt(i)));
			i+=2;
			myPieces.add(new Point(x, y));
		}
		
		ArrayList<Point> theirPieces = new ArrayList<Point>();
		int numOfTheirPieces = Integer.parseInt(Character.toString(input.charAt(i)));
		i+=2;
		for(int n = 0; n < numOfTheirPieces; n++) {
			int x = Integer.parseInt(Character.toString(input.charAt(i)));
			i+=2;
			int y = Integer.parseInt(Character.toString(input.charAt(i)));
			i+=2;
			theirPieces.add(new Point(x, y));
		}
		
		ArrayList<Point> allPieces = new ArrayList<Point>();
		allPieces.addAll(myPieces);
		allPieces.addAll(theirPieces);
		
		System.out.println("Mine " + myPieces.toString());
		System.out.println("Theirs " + theirPieces.toString());
		
		int maxJumps = 0;
		
		for(Point myPos: myPieces) {
			int jumpsPossible = findGreatestJumpsPossible(myPos, theirPieces, allPieces, true);
			//System.out.println(myPos.toString() + ": " + jumpsPossible);
			if(jumpsPossible > maxJumps)
				maxJumps = jumpsPossible;
		}
		
		return maxJumps;
	}
	
	private static boolean isInbounds(int x, int y) {
		return x >= 1 && x <= 8 && y >= 1 && y <= 8;
	}
	
	public static void main(String[] args) {
//		System.out.println(generateEncodedString("C1B2F3$"));
//		System.out.println(generateEncodedString("A1A1A1$"));
//		System.out.println(generateEncodedString("A1H2D3$"));
//		System.out.println(generateEncodedString("T5S4$"));
//		System.out.println(generateEncodedString("A1B1C1$"));
//		System.out.println(generateEncodedString("A2C4S5L3$"));
//		System.out.println(generateEncodedString("C5P4L3U2S1$"));
//		System.out.println(generateEncodedString("C2H3P4$"));
//		System.out.println(generateEncodedString("M1O2N3T4R5E1A2L3$"));
		
//		System.out.println(simplifyConic("x^2+y^2+4x-6y-3=0"));
//		System.out.println(simplifyConic("x^2+4y^2-6x-16y-11=0"));
//		System.out.println(simplifyConic("2x^2+2y^2+8x+12y-6=0"));
//		System.out.println(simplifyConic("4x^2-y=0"));
//		System.out.println(simplifyConic("x^2+y^2-6x+2y-39=0"));
//		System.out.println(simplifyConic("25x^2+4y^2+100x-32y+64=0"));
//		System.out.println(simplifyConic("9x^2-4y^2+18x+24y-63=0"));
//		System.out.println(simplifyConic("-25x^2+4y^2-100=0"));
		
//		System.out.println(producePalindrome("A23", 16));
//		System.out.println(producePalindrome("A345", 12));
//		System.out.println(producePalindrome("196", 10));
//		System.out.println(producePalindrome("6A", 16));
//		System.out.println(producePalindrome("5896", 13));
//		System.out.println(producePalindrome("8769", 15));
//		System.out.println(producePalindrome("46894", 10));
//		System.out.println(producePalindrome("AAB4", 12));
		
//		System.out.println(findGreatestJumpsPossible("1,1,5,2,2,6,4,6 "));
//		System.out.println(findGreatestJumpsPossible("1,6,2,3,7,3,7,5,5,7"));
//		System.out.println(findGreatestJumpsPossible("1,1,5,3,2,4,2,6,4,6"));
//		System.out.println(findGreatestJumpsPossible("2,1,3,1,5,6,2,4,2,6,4,2,4,6,6,6,4,4"));
//		System.out.println(findGreatestJumpsPossible("1,1,7,2,2,6,4,4"));
//		System.out.println(findGreatestJumpsPossible("1,2,2,5,3,3,5,3,7,3,7,5,5,7"));
//		System.out.println(findGreatestJumpsPossible("2,1,1,1,5,4,2,6,3,7,2,4,3,3"));
//		System.out.println(findGreatestJumpsPossible("2,2,2,4,8,5,3,3,5,3,7,3,7,5,5,7"));
//		System.out.println(findGreatestJumpsPossible("2,8,2,1,7,5,3,3,5,3,7,3,2,6,4,6"));
	}
}
