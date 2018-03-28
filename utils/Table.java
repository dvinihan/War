import java.util.ArrayList;
	
public class Table {

		ArrayList<Card> cardArray = new ArrayList<Card>();


		public Table(){
		}

		public void add(Card c){
			cardArray.add(c);
		}
		
		public String toString(){
			String s = "TABLE: \n";
			
			for(int i = 0; i < cardArray.size(); i++){
				s += "\t" + (i+1) + ")" + " " + cardArray.get(i) + "\n";
			}
			
			return s;
		}
		
		public void discard(Card c){
			try{
			cardArray.remove(c);
			} catch(IndexOutOfBoundsException e){	
				System.out.println("No table cards to discard");
			}
		}
		
		public void clear(){
			cardArray.clear();
			}
		}
	

