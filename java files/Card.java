
public class Card implements Comparable<Card>{
	String suit;
	String number;
	int value;
	int suitValue;


	public Card(String n, String s){
		suit = s;
		number = n;
		
		switch(n){
		case "null": ;
		case Utils.TWO_STRING: this.value = 2;
					break;
		case Utils.THREE_STRING: this.value = 3;
		break;
		case Utils.FOUR_STRING: this.value = 4;
		break;
		case Utils.FIVE_STRING: this.value = 5;
		break;
		case Utils.SIX_STRING: this.value = 6;
		break;
		case Utils.SEVEN_STRING: this.value = 7;
		break;
		case Utils.EIGHT_STRING: this.value = 8;
		break;
		case Utils.NINE_STRING: this.value = 9;
		break;
		case Utils.TEN_STRING: this.value = 10;
		break;
		case Utils.JACK_STRING: this.value = 11;
		break;
		case Utils.QUEEN_STRING: this.value = 12;
		break;
		case Utils.KING_STRING: this.value = 13;
		break;
		case Utils.ACE_STRING: this.value = 14;
		break;
		}
		
		switch(s){
		case "null": ;
		case Utils.CLUBS_STRING: this.suitValue = 1; break;
		case Utils.DIAMONDS_STRING: this.suitValue = 2; break;
		case Utils.HEARTS_STRING: this.suitValue = 3; break;
		case Utils.SPADES_STRING: this.suitValue = 4; break;
		}
	}
	
	public String toString(){
		return number + " of " + suit;
	}
	
	public int compareTo(Card c){
		if(this.value < c.value){
			return -1;
		}
		else if(this.value > c.value){
			return 1;
		} else {
			return this.suit.compareTo(c.suit);
		}
	}
	
}
