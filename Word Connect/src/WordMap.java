import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class WordMap {
	//don't change this
	private static final int[] RARITY_LIST_NUMS = {10,20,35,40,50,55,60,70,80,95};
	//change this to alter how heavy words from the different sizes are
	private static final int[] WEIGHTS = {1, 1, 1, 1, 2, 8, 90, 90, 90, 90};
	/* From SCOWL readme: 
     * To give you an idea of what the words in the various sizes look like
     * here is a sample of 25 random words found only in that size:
     * 
     * 10: actual appreciating assembly case clear comparable continuation delay
     *     died display experiencing feeling introduces means merely midnight
     *     misleading prove respects stuff unable understanding warn watching
     *     whether 
     * 
     * 20: adjustments buggers defeating developer disappoints disclaimer forbade
     *     framework hash islands libel lips misrepresent museums prayer resolved
     *     sauce scum seldom sickened targets terrorist transaction waving wraps 
     * 
     * 35: assassinations champs consul deodorized discoursing fondness furl glades
     *     groom hasting infix jalopy multiplicity obediently oversimplification
     *     pantomimed promenaded provocations psychiatry roughing sanserif snipped
     *     spellbound tunnelings upturning 
     * 
     * 40: barbells beholders bestsellers breathlessly envision goalpost gridlocks
     *     halftimes hypodermics immortalize insularity jiggered jumpsuit phallic
     *     polygamist proscribed selfishly shushes smrgsbords stinkers teargas
     *     tics tykes warmonger zaps 
     * 
     * 50: abuts aquaplanes aught aviary backstopped beekeeper daylights disgorges
     *     disinclined flange handcrafted ideograms insole knucklehead leaseholders
     *     leis nostrums pedometer pyromaniac royalist saline slavered unmissed
     *     valuations youngish 
     * 
     * 55: apparatchik bodysuit bungee defectiveness deselect druggie duffing
     *     freephone frontbenchers gestalts guesthouses harrumphing injudiciously
     *     insanitary orangeness pistes proximate pukka radicalized raptor slowcoach
     *     stakeholders tombolas tosser troglodyte 
     * 
     * 60: ephedrine extender floorwalkers groomers isometrically kaddishes knurling
     *     mealiness moue nonstrategic placekicker preadolescences professorially
     *     reemploying rephotograph rerecord ringings singletree sous sportiness
     *     springily telephotography trainable unadventurous unsymmetrical 
     * 
     * 70: abiogenetic adenitises agon appellative chincapin coonhounds crosslet
     *     dissipator dropline dualities indult megadose monometallic monovalent
     *     polymerases predella repercussive saprophagous sestet spandrel surveilled
     *     tapetum troche tufty uveitis 
     * 
     * 80: bethanked brusher ceilers cocultures conformationally endophagy
     *     fulgencies gigantomachies handballers handpresses helibus ngaios
     *     overcredulous palladianism predictabilities santir scarabaean schavs
     *     scrine skreegh sostenuti tamoxifens thunderation unmitering xanthomata 
     * 
     * 95: absinthiin amphigenetic ashir autocephalia babingtonites baltimore
     *     barenecked dargles dashel deprevation halapepe imputting kyphosus liniya
     *     locable luckly minitrains monadistic mullenize nonelectrized
     *     nonmalleableness printerlike subdiscoid trifly typothere 
	 * 
	 */
	
	public final HashMap<String, Word> WORD_MAP;
	
	public WordMap(String mapFilePath) {
		System.out.println("Loading words...");
		WORD_MAP = new HashMap<String, Word>();
		try {
			BufferedReader bf = new BufferedReader(new FileReader(mapFilePath));
			String line;

			while((line = bf.readLine()) != null) {
				int i = line.indexOf(':');
				int rarity = Integer.parseInt(line.substring(i + 2, i + 4));
				String word = line.substring(0, i);
				Word wordObject = new Word(word, getWeight(rarity));
				WORD_MAP.put(word, wordObject);
			}
			
			bf.close();
			bf = new BufferedReader(new FileReader(mapFilePath));
			
			while((line = bf.readLine()) != null) {
				int i = line.indexOf(':');
				String word = line.substring(0, i);
				Word w = WORD_MAP.get(word);
				ArrayList<Word> neighbors = new ArrayList<Word>();
				i+=6;
				int i2;
				
				while(line.length() > i) {
					i2 = line.indexOf(',', i);
					String neighbor = line.substring(i, i2);
					//if(neighbor.length() == w.toString().length())
					neighbors.add(WORD_MAP.get(neighbor));
					i = i2 + 2;
				}
				w.setNeighbors(neighbors);
			}
			bf.close();
			
		} catch(IOException e) {
			System.out.println("Uh oh... something went wrong loading the word connect stuff:");
			System.out.println(e.getMessage());
			System.out.println("Come back next time when the wrong is righted.");
			System.exit(0);
		}
		
		System.out.println("Ready.\n");
	}
	
	private static int getWeight(int rarity) {
		for(int i = 0; i < WEIGHTS.length; i++) {
			if(RARITY_LIST_NUMS[i] == rarity)
				return WEIGHTS[i];	
		}
		throw new IllegalArgumentException();
	}
	
	public boolean isWord(String word) {
		return WORD_MAP.containsKey(word);
	}
	
	public boolean isWord(Word word) {
		return WORD_MAP.containsKey(word.toString());
	}
	
	public Word get(String word) {
		return WORD_MAP.get(word);
	}
	
	public void reset() {
		for(Word w: WORD_MAP.values()) {
			w.setVisited(false);
			w.setTentative(false);
			w.setShortestPath(null);
			w.setShortestPathWeight(0);
		}
	}
}
