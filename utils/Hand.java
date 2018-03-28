import java.util.ArrayList;

public class Hand {
	
		ArrayList<Card> cardArray = new ArrayList<Card>();
		
		int handValue = 0;
		String handName = "";
			
		public Hand(){
		}
		
		public void showHand(Hand h){
			System.out.println(h);
		}
		
		public void clear(){
			cardArray.clear();
		}
		
		public void add(Card c){
			cardArray.add(c);
		}
		
		public void remove(Card c){
			cardArray.remove(c);
		}
		
		public boolean contains(Card c){
			boolean doesContain = false;
			for(int i=0; i< cardArray.size(); i++){
				if(cardArray.contains(c)){
					doesContain = true;
				}
			}
			return doesContain;
		}
		
		public Card get(int pos){
			return cardArray.get(pos);
		}
		
		public int size(){
			return cardArray.size();
		}
		
		public int getHandValue(){
			return handValue;
		}
				
		public String getHandName(){
			return handName;
		}
			
		
					
}
