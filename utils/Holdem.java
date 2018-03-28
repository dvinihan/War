import java.util.ArrayList;

public class Holdem extends Game {
		static int startingChips = 50;
		
		int numPlayers;
		int pot;
		
		ArrayList<Integer> scoreArray = new ArrayList<Integer>();
		ArrayList<Card> highestCardArray = new ArrayList<Card>();
		ArrayList<String> handNameArray = new ArrayList<String>();
		ArrayList<Card> highestNonUsedCardArray = new ArrayList<Card>();
	
	public Holdem(int numPlayers){
		super(numPlayers, 2);
		this.numPlayers = numPlayers;
		super.startingHandNumCards = 2;
		
		}
	
	//what you call in the main class to start a Holdem Game
	public void gameLoop() {
		
		this.startGame(Utils.HOLDEM_STRING);
		
		//give each player starting chips
		for(int i = 0; i < numPlayers; i++){
			Player currentPlayer = this.playerArray.get(i);
			currentPlayer.chips = startingChips;
			}
		playTrick();
		
		//we're going to keep playing tricks and removing losers until there's one winner left
		while (playerArray.size() != 1){
			System.out.println(Utils.NEXT_TRICK);
			System.out.println();
			playTrick();
			
			Player nullPlayer = new Player("null", "null");
			//set loser player indices to null
			for(int i = 0; i< playerArray.size(); i++){
				if(playerArray.get(i).chips == 0){
					System.out.println(playerArray.get(i).playerName + " is out.");
					playerArray.set(i, nullPlayer);
				}
			}
			//remove null players
			while(playerArray.contains(nullPlayer)){
				playerArray.remove(nullPlayer);
			}
			//losers are out; start next trick		
		}
		
		System.out.println(playerArray.get(0).playerName + " has all the chips! " + playerArray.get(0).playerName + " wins!");
	}
		
	//plays one trick, from betting through awarding chips to the winner;
	public void playTrick(){	
		for(int i = 0; i < playerArray.size(); i++){
			deal(playerArray.get(i),deck,startingHandNumCards);
		}
		
		this.layToTable(3);
		System.out.println(Utils.FIRST_BET);
		this.allBet();
		this.layToTable(1);
		System.out.println(Utils.SECOND_BET);
		this.allBet();
		this.layToTable(1);
		System.out.println(Utils.FINAL_BET);
		this.allBet();
		
		for(int i = 0; i < playerArray.size(); i++){
			HoldemHand thisHand = new HoldemHand(table, playerArray.get(i).hand);
			System.out.println(playerArray.get(i).playerName + "'s hand: " + thisHand.bestHand);
			System.out.println(thisHand.name + ".");
			System.out.println();
			scoreArray.add(thisHand.score);
			handNameArray.add(thisHand.name);
			highestCardArray.add(thisHand.highestCard);
			highestNonUsedCardArray.add(thisHand.highestNonUsedCard);
		}
		

		
		//run through each score to see which index wins
		int trickWinnerScore = scoreArray.get(0);
		int trickWinnerIndex = 0;
		ArrayList<Player> tiedWinnersArray = new ArrayList<Player>();
		
		for(int i = 1; i < scoreArray.size(); i++){
			if(scoreArray.get(i) > trickWinnerScore){
				trickWinnerScore = scoreArray.get(i);
				trickWinnerIndex = i;
			} else if(scoreArray.get(i) == trickWinnerScore){
				//compare second highest card 
				//Two scenarios:
				//1)All five cards are used, and the highest card in the hand is used to determine winner
				//2)1-4 cards are used, wherein the other card(s) are used to determine winner
				if(trickWinnerScore == 10 ||
						trickWinnerScore == 9 ||
						trickWinnerScore == 7 ||
						trickWinnerScore == 6 ||
						trickWinnerScore == 5){
					//find the highest card
					if(highestCardArray.get(i).value > highestCardArray.get(trickWinnerIndex).value){
						trickWinnerIndex = i;
						trickWinnerScore = scoreArray.get(i);
					} else if (highestCardArray.get(i).value == highestCardArray.get(trickWinnerIndex).value){
						if(isSuitHigher(highestCardArray.get(i), highestCardArray.get(trickWinnerIndex))){
							trickWinnerIndex = i;
							trickWinnerScore = scoreArray.get(i);
						}
					}
				} else {
					//highest non-used card wins
					if(highestNonUsedCardArray.get(i).value > highestNonUsedCardArray.get(trickWinnerIndex).value){
						trickWinnerIndex = i;
						trickWinnerScore = scoreArray.get(i);

					} else if (highestNonUsedCardArray.get(i).value == highestNonUsedCardArray.get(trickWinnerIndex).value){
						if(isSuitHigher(highestNonUsedCardArray.get(i), highestNonUsedCardArray.get(trickWinnerIndex))){
							trickWinnerIndex = i;
							trickWinnerScore = scoreArray.get(i);

						} else {
							if(!tiedWinnersArray.contains(playerArray.get(i))){
								tiedWinnersArray.add(playerArray.get(i));
							}
							if(!tiedWinnersArray.contains(playerArray.get(trickWinnerIndex))){
								tiedWinnersArray.add(playerArray.get(trickWinnerIndex));
							}
						}
					}
				}
			}
		}
		
		//if its a tie:
		if(tiedWinnersArray.size() != 0){
			for(int i = 0; i < (tiedWinnersArray.size()-1); i++){
				System.out.print(tiedWinnersArray.get(i).playerName + " and ");
			}
			System.out.println(tiedWinnersArray.get((tiedWinnersArray.size()-1)).playerName + " tie with " + handNameArray.get(trickWinnerIndex) + "!");
			int chipsEach = pot/tiedWinnersArray.size();
			
			for(int i = 0; i < tiedWinnersArray.size(); i++){
				tiedWinnersArray.get(i).chips += chipsEach;
			}
		} else{
			Player trickWinner = playerArray.get(trickWinnerIndex);
			trickWinner.chips += pot;
		
			System.out.println(trickWinner.playerName + " wins with " + handNameArray.get(trickWinnerIndex) + "!");
		}
		
		for(int i =0; i < playerArray.size(); i++){
			System.out.println(playerArray.get(i).playerName + " has " + playerArray.get(i).chips + " chips.");
		}
		System.out.println();
		newTrickCleanup();
	}
	
	public void newTrickCleanup(){
		pot = 0;
		table.clear();
		highestNonUsedCardArray.clear();
		highestCardArray.clear();
		handNameArray.clear();
		scoreArray.clear();
		
		for(int i = 0; i < playerArray.size(); i++){
			playerArray.get(i).hand.clear();
		}
		
		int totalCardsNeededForTrick = (playerArray.size() * 2) + 5;
		if(deck.cardsInDeck.size() < totalCardsNeededForTrick){
			deck = new Deck();
		}
		
	}
		
	public Card getHighestCard(ArrayList<Card> cards){
		Card highestCard = cards.get(0);
		
		//then compare each other card to see if it's higher
		for(int i = 1; i < cards.size(); i++){
			if(cards.get(i).value > highestCard.value){
				highestCard = cards.get(i);
			} else if (cards.get(i).value == highestCard.value){
				if(isSuitHigher(cards.get(i), highestCard)){
					highestCard = cards.get(i);
				}
			}
		}
		return highestCard;
	}
	
	public boolean isSuitHigher(Card c1, Card c2){
		if(c1.suitValue > c2.suitValue){
			return true;
		}
		else return false;
	}
	
	//calls makeBet on each player
 	public void allBet(){
		for(int i = 0; i < playerArray.size(); i++){
			makeBet(playerArray.get(i));
		}
	}
	
	
	//player p gets queried and inputs the amount they want to bet; returns that amount
	public int makeBet(Player p){
		System.out.println(table);
		System.out.println();
		
		int bet = 0;
		boolean done = false;
		System.out.println(p);
		System.out.println(pot + " chips in pot.");
		
		//take in bet amount	
		while (!done){
			System.out.println("How much would you like to bet?");
			
			try {
				bet = Integer.parseInt(sc.next());
				
				if(bet < 0){
					System.out.println("You can't bet a negative amount.");
					} 
				else if (bet > p.chips){
					System.out.println("Not enough chips, stranger.");
					}
				else done = true;
				}
			
			catch(Exception e){
				System.out.println("You must enter a number.");
				}
			
		}
		
		pot += bet;
		p.chips -= bet;
		return bet;
	}
	
	
//	puts cards on the table of number a (from the deck)
	public void layToTable(int a){
		
		for(int i = 0; i < a; i++){
			Card c = deck.drawCard();
			table.add(c);		
		}
		
	}	
	
}
