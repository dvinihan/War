
public class Player {
	
	String playerName;
	int chips;
	Hand hand;
	

	public Player(String playerName, String gameType){
		this.playerName = playerName;
		this.hand = new Hand();
	}
	
	public void addCardToHand(Card c){
		hand.add(c);
	}
	
	public Card loseCard(Card c){
		if(hand.contains(c)){
			hand.remove(c);
			return c;
		} else return null;	
	}
	
	public void getsChips(int i){
		chips += i;
	}
	
	public String toString(){
		String s = playerName + "'s hand:\n";
		
		for (int i = 0; i < hand.size(); i++){
			s += "\t" + (i+1) + ") " + hand.get(i) + "\n";
		}
		
		if(chips != 0){
			s += "# of chips: " + chips + "\n";
		}
		
		return s;
	}
	
}
