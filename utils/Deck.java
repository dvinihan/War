import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	Card[] cardArray;
	String[] suitList = new String[]{Utils.CLUBS_STRING, Utils.DIAMONDS_STRING, Utils.HEARTS_STRING, Utils.SPADES_STRING};
	String[] valueList = new String[]{Utils.TWO_STRING, Utils.THREE_STRING, Utils.FOUR_STRING, Utils.FIVE_STRING, Utils.SIX_STRING, 
			Utils.SEVEN_STRING, Utils.EIGHT_STRING, Utils.NINE_STRING, Utils.TEN_STRING, Utils.JACK_STRING, Utils.QUEEN_STRING, Utils.KING_STRING, Utils.ACE_STRING};	
	int deckLength = suitList.length * valueList.length;
	
	//array list representing the cards currently in the deck, with an order
	//array lists have variable lengths
	ArrayList<Card> cardsInDeck = new ArrayList<Card>();
	
	public Deck(){
		int index = 0;
		cardArray = new Card[deckLength];
		
		for (String suit: suitList){
			for (String value: valueList){
				cardArray[index] = new Card(value, suit);
				index++;
			}
		}
		
		for (int i = 0; i < deckLength; i++){
			cardsInDeck.add(cardArray[i]);
		}
		
		Collections.shuffle(cardsInDeck);
	}
	

	public void printDeck(){
		for (int i = 0; i < cardArray.length; i++){
		System.out.println(cardArray[i]);
		}
	}
	
	public void printCardsInDeck(){
		for (int i = 0; i < cardsInDeck.size(); i++){
			System.out.println(cardsInDeck.get(i));
			}
	}
	
	public void shuffle(){
		Collections.shuffle(cardsInDeck);
	}
	
	public Card drawCard(){
		if (cardsInDeck.size() == 0){
			System.out.println("Deck is empty.");
		}
		
		Card currentCard = new Card("null", "null");
		
		try{
		currentCard = cardsInDeck.get(0);
		cardsInDeck.remove(0);
		} catch(Exception e){
		}
			
		return currentCard;
	}
	
}
