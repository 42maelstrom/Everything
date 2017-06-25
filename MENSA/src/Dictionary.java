import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;


public class Dictionary {
	public final ArrayList<String> dict;
	public final int rarityCutoff;
	
	/**
	 *  To give you an idea of what the words in the various sizes look like
			here is a sample of 25 random words found only in that size:

			10: arriving assume blank contents contrast crashed encounter flashing hair
			    included invite lecture own papers presume raises sequence shop someplace
			    specifically still task technical unaware whenever 

			20: appointing ceases commissioned concealing eastern grades inhabitant
			    intent introductory makers misreads obstructs onus passion pavement pit
			    pizzas practices racial regional stuns suite thous unemployment withdraws 
			
			35: affronts blacktopped cajoles conforms decays disqualifies falsities
			    insinuates jackasses labored maligned millisecond narrowness occupant
			    pertains photoing professing psalm robins solace unfilled utters valleys
			    vaster washrooms 
			
			40: agings beeline brownish charade charmer copter deaconess dreadlocks
			    enamors genteel joyrode longtime monoliths musings nosedives
			    overextending overviews paralegals percentile pinkie recruiter skydiving
			    stepdaughters twitting uncommonly 
			
			50: birdbaths caliphs condoled countersunk deathblow dinkier firebugs flambé
			    gondolier humpbacks hymens legless maraud mournfulness perigee predecease
			    probating retouched speedups stovepipe streetwalkers tempter trysted
			    unclassified wrongheaded 
			
			55: ahchoo clonked concussed countermeasure defectively deliverable
			    dinnerware equerry feelgood fleabags gnocchi headcount housewifely humph
			    imprudently pupates reshapes serifs sirrah smasher stargazing steelworker
			    stroppiest tombolas touchily 
			
			60: amoxicillin anathematizes casehardening chortlers endoscopic ens firer
			    garroter gorily granduncle harelipped idolatress imitable innervated
			    nonsustaining pieceworker punditry purlieus reintegrated restyles
			    sissified underfur unworried vigilantist vivariums 
			
			70: acaricides bezant bloodroots breccias deoxidizing divulsion eaglewood
			    exponentiations facilitative hyponasty impressionistically martyrization
			    meadowy obturate oilseed oscitancy pieplants plowboy ropedancer saturniid
			    selenious tabard tunicle unjointed veery 
			
			80: allotheism atelectatic attitudinarians australite carnivorousnesses
			    chrematistic cottises defibrinated deploration ferrogram hippocrepian
			    hyalitis inhabitresses jossers martello mungoes niton polysynthesis
			    propylitized revestiary rocketsonde slipsoles smouse uniformitarianist
			    zander 
			
			95: adamantinoma confated eevnings electrogildings entozoologically
			    hematocatharsis ingrian jeetee lachsa lipogenic nonhypostatical
			    nummuloidal orsede paludamenta patavium platystomous prepersuading
			    proctoplegia prp recallist ruberythric sblood uncostumed unguillotined
			    unmonarchical 
	 */
	public Dictionary(int rarityCutoff) {
		this.rarityCutoff = rarityCutoff;
		dict = getDict("RarityDictionary.txt", rarityCutoff);
	}
	
	public boolean isWord(String word) {
		return dict.contains(word.toLowerCase());
	}
	
	private static ArrayList<String> getDict(String fileName, int rarity) {
		ArrayList<String>  dict = new ArrayList<String>();
		BufferedReader br;
		
		try{
			br = new BufferedReader(new FileReader(fileName)); 
			String line;
			while((line = br.readLine() ) != null) {
				String word = line.substring(0, line.indexOf(':'));
				int rar = Integer.valueOf(line.substring(line.indexOf(':') + 2));
				if(rar <= rarity)
					dict.add(word);
			}
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return dict;
	}
	
	public void addWord(String word) {
		dict.add(word.toLowerCase());
	}
}
