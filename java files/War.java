import java.util.ArrayList;

public class War extends Game {
	
	public War(int numPlayers, int numTurns){
		super(numPlayers, numTurns);
	}
	
	//what you call in the main class to start a War Game
	public void gameLoop(){
		this.startGame(Utils.WAR_STRING);
		
		//the number of turns is equal to the number of cards each player starts with.
		//Don't include the last trick, because that will be played automatically
		for(int i = 0; i < this.startingHandNumCards-1; i++){
			System.out.println("ROUND " + (i+1));
			this.playTrick();
		}
		
		//play last trick. 
		this.finalTrick();
		
		//pick and display round winner
		this.pickWinner();
	}
	
	//this executes one trick (turn), allowing all players to lay a card
	public void playTrick(){				

		System.out.println();
		
		//Each player gets a turn.
		//Calls cardToLay for each player- lets them input which card they want to play
		//Then calls layCard for each player- they lay the card to the table
		for (int i = 0; i < playerArray.size(); i++){
			Card cardToLay = chooseCardToLay(playerArray.get(i));
			layCard(this.playerArray.get(i), cardToLay);
			clearScreen();
		}
		
		System.out.println();
		printTable();
		
		Player trickWinner = assessHand();
		
		System.out.println(trickWinner.playerName + " wins this hand.\n");
		printScore();
		table.clear();
		
		String input;
		boolean done = false;
		while(!done){
			System.out.println();
			System.out.println("Input \"go\" to continue.");

			try {
				input = sc.next();
				
				if(input.equals("go")){
					done = true;				
				}
			}
				
		
			catch(Exception e){
				System.out.println("You must enter a number.");
				}
			}
		}

	
	
	//player lays a card to the table
	public void layCard(Player p, Card c){
		//add the card to the table
		this.table.add(c);
		//remove the card from the player's hand
		p.hand.remove(c);
		
	}
	
	//asks for input of which card to play- can be any number 1 through (number of cards in hand)
	public Card chooseCardToLay(Player p){
		int cardPosition = 0;
		boolean done = false;
		System.out.println(p);
				
		System.out.println("Which card would you like to play?");
		System.out.println("Choose a number 1 through " + (p.hand.size()));
		//take in card position (starting at 1)	
		while (!done){
			try {
				cardPosition = Integer.parseInt(sc.next());
				
				if(cardPosition < 1 || cardPosition > p.hand.size()){
					System.out.println("Not valid. Choose the position of the card you would like to play, 1 through " + p.hand.size());
					} 
				else done = true;
				}
	
			catch(Exception e){
				System.out.println("You must enter a number.");
				}
			}
		
		return p.hand.cardArray.get(cardPosition-1);
	}
	
	//looks at the cards on the table and determine's whose card is highest
	public Player assessHand(){		
		//check each card against the value of the highest card. If it's higher, make that the new highest card.
		//initially, the first card is the highest. 
		Card highCard = table.cardArray.get(0);
		int highValue = highCard.value;
		int winningCardIndex = 0;
		
		for (int i = 1; i < playerArray.size(); i++){
			Card currentCheck = table.cardArray.get(i);
			int currentCheckValue = currentCheck.value;		
			
			if (highValue < currentCheckValue){
				highCard = currentCheck;
				highValue = currentCheckValue;
				winningCardIndex = i;
			}
			else if (highValue == currentCheckValue) {
				if (isSuitHigher(currentCheck, highCard)){
					highCard = currentCheck;
					winningCardIndex = i;
				} 	
			}
		}
		
		//highCard is now set to the highest, winning card
		System.out.println(highCard + " is highest.");
		
		Player trickWinner = playerArray.get(winningCardIndex);
				
		scoreArray[winningCardIndex]++;
		return trickWinner;
		
	}
		
	//returns true if the suit of the first card is higher than the suit of the second card
	public boolean isSuitHigher(Card c1, Card c2){
		if(c1.suitValue > c2.suitValue){
			return true;
		}
		else return false;
	}
	
	public void pickWinner(){		

		ArrayList<Player> winnersArray = new ArrayList<Player>();
		int winningScore = 0;
		
		for (int i = 0; i < (numPlayers); i++){
			if(scoreArray[i] > winningScore){
				winningScore = scoreArray[i];
				winnersArray.clear();
				winnersArray.add(playerArray.get(i));
			}
			else if (scoreArray[i] == winningScore){
				winnersArray.add(playerArray.get(i));
			}
		}
		
		if(winnersArray.size() == 1){
		System.out.println(winnersArray.get(0).playerName + " wins with " + winningScore + " points."); 
		}
		else{
			for(int i=0; i < winnersArray.size(); i++){
				if(i !=0){ System.out.print(" and ");}
			System.out.print(winnersArray.get(i).playerName);
			}
			System.out.print(" tie with " );
			if(winningScore == 1){
				System.out.print("1 point each.");
			} else{
				System.out.print(winningScore + " points each.");
		}
		}
	}

	public void finalTrick(){
		//play last trick. No need to ask players which card they want to lay.
				System.out.println("FINAL ROUND");
				for (int i = 0; i < playerArray.size(); i++){
					Card cardToLay = playerArray.get(i).hand.get(0);
					layCard(this.playerArray.get(i), cardToLay);
				}
				System.out.println();
				printTable();
				
				Player trickWinner = assessHand();
				
				System.out.println(trickWinner.playerName + " wins this hand.\n");
				printScore();
				
	}
}
	

