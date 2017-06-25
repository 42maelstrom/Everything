import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Feb16 {
	public static final String DESCRIPTION = "Place stars in six cells of this"
			+ " grid so that every row, every column, and every outlined region"
			+ " contains exactly one star. Stars must never be located in adjacent"
			+ " cells, not even diagonally.";
	
	public static final ArrayList<Class> HELPER_CLASSES = new ArrayList<Class>(
			Arrays.asList(Region.class, Space.class));

	private Region[] regions;
	
	public Feb16(Region... regions) {
		this.regions = regions;
	}
	
	public void solve() {
		boolean isSolved = false;
		int count = 0;
		newTry:
		while(isSolved == false) {
			count++;
			System.out.println(count);
			Random rand = new Random();
			boolean[] rowHasStar = new boolean[6];
			boolean[] colHasStar = new boolean[6];
			Space[] spaces = new Space[6];
			int i = 0;
			
			for(Region r: regions) {
				int numSpaces = r.spaces.length;
				int randSpaceNum = rand.nextInt(numSpaces);
				r.spaces[randSpaceNum].ch = '*';
				rowHasStar[r.spaces[randSpaceNum].r] = true;
				colHasStar[r.spaces[randSpaceNum].c] = true;
				spaces[i] = r.spaces[randSpaceNum]; 
				i++;
			}
			
			for(i = 0; i < 6; i++) {
				if(rowHasStar[i] == false)
					continue newTry;
				
				if(colHasStar[i] == false)
					continue newTry;
				
				Space s = spaces[i];
				for(int j = i + 1; j < 6; j++) {
					Space s2 = spaces[j];
					if(Math.abs(s.r - s2.r) < 2 && Math.abs(s.c - s2.c)< 2)
						continue newTry;
				}
			}
			
			isSolved = true;
			System.out.println("Solution found");
			for(int r = 0; r < 6; r++) {
				System.out.println();
				for(int c = 0; c < 6; c++) {
					boolean hadOne = false;
					for(i = 0; i < 6; i++) {
						if(spaces[i].r == r && spaces[i].c == c) {
							System.out.print("* ");
							hadOne = true;
						}
					}
					if(!hadOne)
						System.out.print("O ");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Region r1 = new Region(new Space(0,0), new Space(0, 1), new Space(1, 0),
				new Space(1, 1), new Space(2, 0), new Space(2, 1), new Space(3, 0),
				new Space(3, 1));
		Region r2 = new Region(new Space(4, 0), new Space(5, 0), new Space(5, 1),
				new Space(5, 2), new Space(5, 3), new Space(5, 4));
		Region r3 = new Region(new Space(4, 1), new Space(4, 2), new Space(4, 3),
				new Space(4, 4), new Space(4, 5), new Space(5, 5), new Space(3, 5),
				new Space(3, 4), new Space(2, 4), new Space(2, 5), new Space(1, 5),
				new Space(0, 5));
		Region r4 = new Region(new Space(3, 2), new Space(3, 3), new Space(2, 3));
		Region r5 = new Region(new Space(2, 2), new Space(1, 2), new Space(1, 3));
		Region r6 = new Region(new Space(0, 2), new Space(0, 3), new Space(0, 4), new Space(1, 4));
		
		new Feb16(r1, r2, r3, r4, r5, r6).solve();
		
	}
}
