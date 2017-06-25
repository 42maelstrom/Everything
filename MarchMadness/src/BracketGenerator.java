import java.util.ArrayList;
import java.util.Arrays;


public class BracketGenerator {
	/**
	 * List of team names. Each name goes down the names on the left and then the right. play in games ignored
	 */
	String[] teamNames;
	/**
	 * 6 entries for each team, going down the list of team names.
	 */
	double[] data;
	IrratRandom iRand;
	
	/**
	 * @param dataFilePath - the path to the csv file downloaded from FiveThirtyEight
	 */
	public BracketGenerator(String dataFilePath, IrratRandom iRand) {
		
		this.iRand = iRand;
	}
	
	public void printPredictions() {
		ArrayList<Team> teams = new ArrayList<Team>(64);
		
		for(int i = 0; i < 64; i++)
			teams.add(new Team(teamNames[i], Arrays.copyOfRange(data,7*i,7*i + 7)));
		
		iRand.setDigit(8);

		int round = 1;
		
		while(teams.size() > 1) {
			ArrayList<Team> nextRound = new ArrayList<Team>(teams.size() / 2);
			System.out.println("Round " + round + "\n");
			
			for(int i = 0; i < teams.size(); i+=2) {
				
				System.out.println(teams.get(i).name + " vs " + teams.get(i+1).name);
				
				double t1percent = (teams.get(i).data[round] / teams.get(i).data[round - 1]);
				double t2percent = (teams.get(i + 1).data[round] / teams.get(i + 1).data[round - 1]);
			//	System.out.println(t1percent + ", " + t2percent);
				
				t1percent /= t1percent + t2percent;
				t2percent /=  t1percent + t2percent;
			//	System.out.println(t1percent + ", " + t2percent);
				
				double val = (double)iRand.nextInt() / 100;
			//	System.out.println(iRand.name + " value: " + val);
				System.out.print("Predicted winner: ");
				
				if(val < t1percent) {
					System.out.println(teams.get(i).name);
					nextRound.add(teams.get(i));
				} else {
					System.out.println(teams.get(i+1).name);
					nextRound.add(teams.get(i+1));
				}
				
				System.out.println();
			}
			teams = nextRound;
			round++;
		}
	}
	
	public static void main(String[] args) {
		BracketGenerator piGenerator = new BracketGenerator() 
	}
}
