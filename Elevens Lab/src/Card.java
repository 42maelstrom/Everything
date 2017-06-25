import java.util.ArrayList;

/**
 * Card.java
 *
 * <code>Card</code> represents a playing card.
 */
public class Card {

	/**
	 * String value that holds the suit of the card
	 */
	private String suit;

	/**
	 * String value that holds the rank of the card
	 */
	private String rank;

	/**
	 * int value that holds the point value.
	 */
	private int pointValue;


   /**
	 * Creates a new <code>Card</code> instance.
	 *
	 * @param cardRank  a <code>String</code> value
	 *                  containing the rank of the card
	 * @param cardSuit  a <code>String</code> value
	 *                  containing the suit of the card
	 * @param cardPointValue an <code>int</code> value
	 *                  containing the point value of the card
	 */
	public Card(String cardRank, String cardSuit, int cardPointValue) {
		rank = cardRank;
		suit = cardSuit;
		pointValue = cardPointValue;
	}


	/**
	 * Accesses this <code>Card's</code> suit.
	 * @return this <code>Card's</code> suit.
	 */
	public String suit() {
		return suit;
   }

	/**
	 * Accesses this <code>Card's</code> rank.
	 * @return this <code>Card's</code> rank.
	 */
	public String rank() {
		return rank;
	}

   /**
	 * Accesses this <code>Card's</code> point value.
	 * @return this <code>Card's</code> point value.
	 */
	public int pointValue() {
		return pointValue;
	}

	/** Compare this card with the argument.
	 * @param otherCard the other card to compare to this
	 * @return true if the rank, suit, and point value of this card
	 *              are equal to those of the argument;
	 *         false otherwise.
	 */
	public boolean matches(Card otherCard) {
		return this.rank().equals(otherCard.rank()) && this.suit().equals(otherCard.suit()) && this.pointValue() == otherCard.pointValue();
	}

	/**
	 * Converts the rank, suit, and point value into a string in the format
	 *     "[Rank] of [Suit] (point value = [PointValue])".
	 * This provides a useful way of printing the contents
	 * of a <code>Deck</code> in an easily readable format or performing
	 * other similar functions.
	 *
	 * @return a <code>String</code> containing the rank, suit,
	 *         and point value of the card.
	 */
	@Override
	public String toString() {
		return rank + " of " + suit + " (point value = " + pointValue + ")";
	}
	
	private boolean isStandardCard(){
		if(suit.equals("Spades") || suit.equals("Hearts") || suit.equals("Diamonds") || suit.equals("Hearts")){
			if(    rank.equals("2") || rank.equals("3") || rank.equals("4") || rank.equals("5") || rank.equals("6")
		        || rank.equals("7") || rank.equals("8") || rank.equals("9") || rank.equals("10") || rank.equals("Jack")
		        || rank.equals("Queen") || rank.equals("King") || rank.equals("Ace")){
					return true;
			}
		}
		return false;
	}
	
	public int compareTo(Card other){
		
		if(this.isStandardCard() && other.isStandardCard() == false)
		{
			return 1;
		}else if(this.isStandardCard() == false && other.isStandardCard()){
				return -1;
		}else{
			//they're either both standard Cards or both not.
			
			//comparing ranks
			int thisRankVal = 0, otherRankVal = 0;
			
			if(this.rank.equals("1")){
				thisRankVal = 1;
			}else if(this.rank.equals("2")){
				thisRankVal = 2;
			}else if(this.rank.equals("3")){
				thisRankVal = 3;
			}else if(this.rank.equals("4")){
				thisRankVal = 4;
			}else if(this.rank.equals("5")){
				thisRankVal = 5;
			}else if(this.rank.equals("6")){
				thisRankVal = 6;
			}else if(this.rank.equals("7")){
				thisRankVal = 7;
			}else if(this.rank.equals("8")){
				thisRankVal = 8;
			}else if(this.rank.equals("9")){
				thisRankVal = 9;
			}else if(this.rank.equals("10")){
				thisRankVal = 10;
			}else if(this.rank.equals("Jack")){
				thisRankVal = 11;
			}else if(this.rank.equals("Queen")){
				thisRankVal = 12;
			}else if(this.rank.equals("King")){
				thisRankVal = 13;
			}else if(this.rank.equals("Ace")){
				thisRankVal = 14;
			}
			
			if(other.rank.equals("1")){
				otherRankVal = 1;
			}else if(other.rank.equals("2")){
				otherRankVal = 2;
			}else if(other.rank.equals("3")){
				otherRankVal = 3;
			}else if(other.rank.equals("4")){
				otherRankVal = 4;
			}else if(other.rank.equals("5")){
				otherRankVal = 5;
			}else if(other.rank.equals("6")){
				otherRankVal = 6;
			}else if(other.rank.equals("7")){
				otherRankVal = 7;
			}else if(other.rank.equals("8")){
				otherRankVal = 8;
			}else if(other.rank.equals("9")){
				otherRankVal = 9;
			}else if(other.rank.equals("10")){
				otherRankVal = 10;
			}else if(other.rank.equals("Jack")){
				otherRankVal = 11;
			}else if(other.rank.equals("Queen")){
				otherRankVal = 12;
			}else if(other.rank.equals("King")){
				otherRankVal = 13;
			}else if(other.rank.equals("Ace")){
				otherRankVal = 14;
			}
			
			if(thisRankVal > otherRankVal){
				return 1;
			}else if(thisRankVal < otherRankVal){
				return -1;
			}else{
			
				//comparing suits.
				
				//if it's not a standard card, it will be zero.
				int thisSuitVal = 0, otherSuitVal = 0;
				
				if(this.suit.equals("Clubs")){
					thisSuitVal = 1;
				}else if(this.suit.equals("Diamonds")){
					thisSuitVal = 2;
				}else if(this.suit.equals("Hearts")){
					thisSuitVal = 3;
				}else if(this.suit.equals("Spades")){
					thisSuitVal = 4;
				}
				
				if(other.suit.equals("Clubs")){
					otherSuitVal = 1;
				}else if(other.suit.equals("Diamonds")){
					otherSuitVal = 2;
				}else if(other.suit.equals("Hearts")){
					otherSuitVal = 3;
				}else if(other.suit.equals("Spades")){
					otherSuitVal = 4;
				}
				
				if(thisSuitVal > otherSuitVal){
					return 1;
				}else if(thisSuitVal < otherSuitVal){
					return -1;
				}else{
					return 0;
				}
			}
		}
	}//end compareTo method
	
	public static void sortCards(ArrayList<Card> cards){
		for(int i = 0; i < cards.size(); i++){
			for(int j = i + 1; j < cards.size(); j++){
				if(cards.get(i).compareTo(cards.get(j)) == 1){
					Card temp = cards.get(i);
					cards.set(i, cards.get(j));
					cards.set(j, temp);
				}
			}
		}
	}
	
	public static void main(String[] args){
		
		Card c1 = new Card("Queen","Spades", 12);
		Card c2 = new Card("Jack", "Spades", 12);
		Card c3 = new Card("King","Unicorns",12);
		Card c4 = new Card("Unicorn", "Spades",12);
		Card c5 = new Card("Unicorn", "Unicorns",12);
		
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(c5);
		cards.add(c4);
		cards.add(c3);
		cards.add(c2);
		cards.add(c1);
		
		Card.sortCards(cards);
		
		for(Card card: cards)
		{
			System.out.println(card.toString());
		}
		
	}

}