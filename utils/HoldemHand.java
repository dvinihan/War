import java.util.ArrayList;
import java.util.Collections;

public class HoldemHand {
	
	ArrayList<Card> cardArray = new ArrayList<Card>();
	int score;
	String name;
	ArrayList<Card> bestHand = new ArrayList<Card>();
	Card highestCard;
	ArrayList<Card> cardsUsedInSet = new ArrayList<Card>();
	Card highestNonUsedCard;
	
	//these will track how many cards of each value and suit are present
	int[] valueTally = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	int[] suitTally = {0, 0, 0, 0};
		
	int[] tempValueTally = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	
	public HoldemHand(Table t, Hand h){
		//you must pass in a table of 5 Cards
				
		cardArray.add(t.cardArray.get(0));
		cardArray.add(t.cardArray.get(1));
		cardArray.add(t.cardArray.get(2));
		cardArray.add(t.cardArray.get(3));
		cardArray.add(t.cardArray.get(4));
		
		cardArray.add(h.cardArray.get(0));
		cardArray.add(h.cardArray.get(1));
		
		//looks at all 7 cards and adds 1 to each spot for each value/suit 
		//order is 2 through Ace, Club through Spades (alphabetical)
		valueTally = this.setValueTally(valueTally, cardArray);
		suitTally = this.setSuitTally(suitTally, cardArray);
		
		this.sortByAsc();
		bestHand = getBestHand();
		highestCard = getHighestCard(bestHand);
		highestNonUsedCard = getHighestNonUsedCard();

	}
	
	public Card getHighestNonUsedCard(){
		ArrayList<Card> nonUsedCards = new ArrayList<Card>();
		for(int i = 0; i < bestHand.size(); i++){
			if(!cardsUsedInSet.contains(bestHand.get(i))){
				nonUsedCards.add(bestHand.get(i));
			}
		}
		return getHighestCard(nonUsedCards);
	}
	
	//sorts all 7 cards from lowest to highest
	public void sortByAsc(){
		Collections.sort(cardArray);
	}
	
	public ArrayList<Card> sortByAsc(ArrayList<Card> cards){
		Collections.sort(cards);
		return cards;
	}
		
		
	//calculates and returns the 5 card set with the highest score
	//Also set this.score to that hand's score
	public ArrayList<Card> getBestHand(){		
			if(isFlush()){
				if(isRoyalFlush()){
					score = 10;
					name = Utils.ROYAL_FLUSH_STRING;
					return getTopFiveFlush();
				} else if(isStraightFlush()){
					score = 9;
					name = Utils.STRAIGHT_FLUSH_STRING;
					return getTopFiveStraightFlush();
				}
			}
			
			if(isSomeOfAKind()){
				if(getTypeOfAKind() == 4){
					score = 8;
					name = Utils.FOUR_OF_A_KIND_STRING;
					return makeSomeOfAKind(getSomeOfAKind(4));
				}
				
			}
			
			if(isFullHouse()){
				score = 7;
				name = Utils.FULL_HOUSE_STRING;
				return getFullHouse();
			}
			
			if(isFlush()){
				score = 6;
				name = Utils.FLUSH_STRING;
				return getTopFiveFlush();
			}
			
			if(isStraight()){
				score = 5;
				name = Utils.STRAIGHT_STRING;
				return getTopFiveStraight();
			}
			
			if(isSomeOfAKind()){
				if(getTypeOfAKind() == 3){
					score = 4;
					name = Utils.THREE_OF_A_KIND_STRING;
					return makeSomeOfAKind(getSomeOfAKind(3));
				}
			}
			
			if(isTwoPair()){
				score = 3;
				name = Utils.TWO_PAIR_STRING;
				return getTwoPair();
			}
			
			if(isSomeOfAKind()){
				if(getTypeOfAKind() == 2){
					score = 2;
					name = Utils.TWO_OF_A_KIND_STRING;
					return makeSomeOfAKind(getSomeOfAKind(2));
				}
			}
			
			score = 1;
			String cardValueName = getHighestCard(getHighestFiveCards()).number;
			name = cardValueName + " " + Utils.HIGH_CARD_STRING;
			
			cardsUsedInSet.add(getHighestCard(getHighestFiveCards()));
			return getHighestFiveCards();
	}
	
	//takes in the 14-long int array, counts tallies for each value 2 - Ace
	public int[] setValueTally(int[] valueTally, ArrayList<Card> cards){
		for (int i = 0; i < cards.size(); i++){
			int currentValue = cards.get(i).value;
					
			switch(currentValue){
				case 2: valueTally[0]++; break;
				case 3: valueTally[1]++; break;
				case 4: valueTally[2]++; break;
				case 5: valueTally[3]++; break;
				case 6: valueTally[4]++; break;
				case 7: valueTally[5]++; break;
				case 8: valueTally[6]++; break;
				case 9: valueTally[7]++; break;
				case 10: valueTally[8]++; break;
				case 11: valueTally[9]++; break;
				case 12: valueTally[10]++; break;
				case 13: valueTally[11]++; break;
				case 14: valueTally[12]++; break;
			}
		}
		return valueTally;
	}
	
	//takes the 4-long int array, counts tallies for each suit - clubs, diamonds, hearts, spades
	public int[] setSuitTally(int[] suitTally, ArrayList<Card> cards){
		for (int i = 0; i < cards.size(); i++){
			int currentValue = cards.get(i).suitValue;
			
			switch(currentValue){
				case 1: suitTally[0]++; break;
				case 2: suitTally[1]++; break;
				case 3: suitTally[2]++; break;
				case 4: suitTally[3]++; break;
			}
		}
		return suitTally;
	}
	
	public ArrayList<Card> getHighestFiveCards(){
		//this is what we'll return
		ArrayList<Card> highestArray = new ArrayList<Card>();
		
		//this is where we'll keep the remaining cards once one is added to highestArray
		ArrayList<Card> runningArray = new ArrayList<Card>();
		
		for(int i = 0; i < cardArray.size(); i++){
			runningArray.add(cardArray.get(i));
		}
		//runningArray now contains all 7 cards
		
		while(highestArray.size() < 5){
			Card highestCard = getHighestCard(runningArray);
			runningArray.remove(highestCard);
			highestArray.add(highestCard);
		}
		return highestArray;		
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
	
	public boolean isTwoPair(){
		int twoOfAKindValue = 0;
		Card nullCard = new Card("null", "null");
		ArrayList<Card> tempHolderArray = new ArrayList<Card>();
		
		if(isSomeOfAKind()){
			if(getTypeOfAKind() == 2){
				twoOfAKindValue = getSomeOfAKind(2).get(0).value;
				ArrayList<Card> checkArray = new ArrayList<Card>();
				
				for(int i = 0; i < cardArray.size(); i++){
					checkArray.add(cardArray.get(i));
				}
				 
		
				//remove from checkArray the first 2-of-a-kind value cards
				for(int i = 0; i < checkArray.size(); i++){
					if(checkArray.get(i).value == twoOfAKindValue){
						//add the card to tempHolderArray before removing it, so we can get it back later
						tempHolderArray.add(checkArray.get(i));
						checkArray.set(i, nullCard);
					}
				}
				while(checkArray.contains(nullCard)){
					checkArray.remove(nullCard);
				}
			
				clear(tempValueTally);
				setValueTally(tempValueTally, checkArray);
								
				//now check for another 2 of a kind
				if(isSomeOfAKind(tempValueTally)){
					if(getTypeOfAKind(tempValueTally) == 2){
						for(int i = 0; i < checkArray.size(); i++){
							//add the card to tempHolderArray before removing it, so we can get it back later
							tempHolderArray.add(checkArray.get(i));
							checkArray.set(i, nullCard);
						}
					}
					
					while(checkArray.contains(nullCard)){
						checkArray.remove(nullCard);
					}
				
					clear(tempValueTally);
					setValueTally(tempValueTally, checkArray);
				
					//now check for a third 2 of a kind
					if(isSomeOfAKind(tempValueTally)){
						if(getTypeOfAKind(tempValueTally) == 2){
							for(int i = 0; i < checkArray.size(); i++){
								//add the card to tempHolderArray before removing it, so we can get it back later
								tempHolderArray.add(checkArray.get(i));
								checkArray.set(i, nullCard);
							}
						}
					}
				
					//tempHolderArray should now have 4 or 6 cards- 2 or 3 pairs
					clear(tempValueTally);	
					setValueTally(tempValueTally, tempHolderArray);
					return true;
				}
			}	
		}
		return false;
	}
	
	//only call if isTwoPair returns true
	public ArrayList<Card> getTwoPair(){			
		int firstTwoOfAKindValue = -1;
		int secondTwoOfAKindValue = -1;
		int thirdTwoOfAKindValue = -1;
		
		ArrayList<Card> twoPairCards = new ArrayList<Card>();
		
		//determines which value has 2 cards present
		for(int i = 0; i < tempValueTally.length; i++){
			if(tempValueTally[i] == 2){
				firstTwoOfAKindValue = i + 2;
			}
		}
		
		
		//determines which OTHER value has 2 cards present
		for(int i = 0; i < tempValueTally.length; i++){
			if(tempValueTally[i] == 2 && (i+2 != firstTwoOfAKindValue)){
				secondTwoOfAKindValue = i + 2;
			}
		}
		
		
		//if there's a third pair, find that value too
		for(int i = 0; i < tempValueTally.length; i++){
			if(tempValueTally[i] == 2 && (i+2 != firstTwoOfAKindValue) && (i+2 != secondTwoOfAKindValue)){
				thirdTwoOfAKindValue = i + 2;
			}
		}
		
		//adds cards of those values to twoPairCards arraylist 
		for(int i = 0; i < cardArray.size(); i++){
			Card currentCard = cardArray.get(i);
			
			if(currentCard.value == firstTwoOfAKindValue || currentCard.value == secondTwoOfAKindValue || currentCard.value == thirdTwoOfAKindValue){
				twoPairCards.add(currentCard);
			}
		}
		
		//we now have an arraylist of 4 or 6 cards- 2 or 3 pairs
		
		//if 6 cards, remove the lowest pair
		if(twoPairCards.size() == 6){
			twoPairCards = sortByAsc(twoPairCards);
			twoPairCards.remove(0);
			twoPairCards.remove(0);
		}
		
				
		//now should have 4 cards- add the highest card not already in there
		cardsUsedInSet.add(twoPairCards.get(0));
		cardsUsedInSet.add(twoPairCards.get(1));
		cardsUsedInSet.add(twoPairCards.get(2));
		cardsUsedInSet.add(twoPairCards.get(3));
		
		while(twoPairCards.size() < 5){
			//add highest card not yet used
			int highestCardIndex = -1;
			int highestCardValue = -1;
			
			for(int i = 0; i < cardArray.size(); i++){
				if(!twoPairCards.contains(cardArray.get(i)) && cardArray.get(i).value >= highestCardValue){
					highestCardIndex = i;
					highestCardValue = cardArray.get(i).value;
				}
			}
			
			Card highestCard = cardArray.get(highestCardIndex);
			twoPairCards.add(highestCard);
						
		}
		return twoPairCards;
	}

	//only call if isFullHouse returns true
	public ArrayList<Card> getFullHouse(){
		for (int i = 0; i < tempValueTally.length; i++){
		}
		
		int threeKindValue = -1;
		int twoKindValue = -1;
		ArrayList<Card> fullHouseCards = new ArrayList<Card>();
		
		//determines which value has 3 cards present
		for(int i = 0; i < tempValueTally.length; i++){
			if(tempValueTally[i] == 3){
				threeKindValue = i + 2;
			}
		}
		
		//determines which value has 2 cards present
		for(int i = 0; i < tempValueTally.length; i++){
			if(tempValueTally[i] >= 2 && (i+2 != threeKindValue)){
				twoKindValue = i + 2;
			}
		}
		

		//adds cards of those values to fullHouseCards arraylist 
		for(int i = 0; i < cardArray.size(); i++){
			Card currentCard = cardArray.get(i);
			
			if(currentCard.value == threeKindValue || currentCard.value == twoKindValue){
				fullHouseCards.add(currentCard);
			}
		}
		
		//we now have an arraylist of 5 or 6 cards
		if(fullHouseCards.size() != 5){
			fullHouseCards.remove(0);
		}
		
		return fullHouseCards;
	}
	
	public boolean isFullHouse(){
		int threeOfAKindValue = 0;
		Card nullCard = new Card("null", "null");
		ArrayList<Card> tempHolderArray = new ArrayList<Card>();
		
		
		if(isSomeOfAKind()){
			if(getTypeOfAKind() == 3){
				threeOfAKindValue = getSomeOfAKind(3).get(0).value;
				
				//store all 7 cards in a checkArray, so we don't alter the cardArray
				ArrayList<Card> checkArray = new ArrayList<Card>();
				for(int i = 0; i < cardArray.size(); i++){
					checkArray.add(cardArray.get(i));
				}
		
				//remove from fullHouseCheck the 3 of a kind value cards
				for(int i = 0; i < checkArray.size(); i++){
					if(checkArray.get(i).value == threeOfAKindValue){
						//add the card to tempHolderArray before removing it, so we can get it back later
						tempHolderArray.add(checkArray.get(i));
						checkArray.set(i, nullCard);
					}
				}
				while(checkArray.contains(nullCard)){
					checkArray.remove(nullCard);
				}
				
				clear(tempValueTally);
				setValueTally(tempValueTally, checkArray);
								
				//now check for another 3 or 2 of a kind
				if(isSomeOfAKind(tempValueTally)){
					if(getTypeOfAKind(tempValueTally) == 2 || getTypeOfAKind(tempValueTally) == 3){
						//put the 3ofAKind cards back into the fullHouseCheck array, and more importantly, the tempValueTally
						for(int i = 0; i < tempHolderArray.size(); i++){
							checkArray.add(tempHolderArray.get(i));
						}	
						clear(tempValueTally);
						setValueTally(tempValueTally, checkArray);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//only call this after calling isStraight returns true
	//should have already sorted in ascending order. This method removes the lowest 2 cards. 
	public ArrayList<Card> getTopFiveStraight(){
		ArrayList<Card> straightArray = getStraight();
			
		while (straightArray.size() > 5){
			straightArray.remove(0);
		}
		return straightArray;
	}
	
	//only call this after calling isStraight returns true
	//returns 5-7 cards in the straight
	public ArrayList<Card> getStraight(){
		ArrayList<Card> straightCards = new ArrayList<Card>();	
		
		//this list will track which indices are in the straight
			ArrayList<Integer> indexArray = new ArrayList<Integer>(); 
			
			
			for(int i = 0; i < valueTally.length; i++){
				if(valueTally[i] != 0){
					indexArray.add(i);
				}
			}
			//indexArray now contains the indices of all values that are present
			
			for(int i = indexArray.size()-1; i >= 4; i--){

				if(indexArray.get(i)-1 ==  indexArray.get(i-1) &&
						indexArray.get(i-1)-1 ==  indexArray.get(i-2) &&
						indexArray.get(i-2)-1 ==  indexArray.get(i-3) &&
						indexArray.get(i-3)-1 ==  indexArray.get(i-4)){
					return makeStraight(indexArray);

				}
			}
			return straightCards;
	}
	
	//helper method for getStraight
	public ArrayList<Card> makeStraight(ArrayList<Integer> indexArray){
		//at this point we know there is a straight.
		//The indices of which values are in the straight are put in as the argument of this method.
		ArrayList<Card> straightCards = new ArrayList<Card>();	
		
		//runs through each index in the array, starting with the highest card's indexValue
		for(int k = indexArray.size()-1; k >= 0; k--){
			//get value of the given card
			int currentValue = indexArray.get(k) + 2; 
			
			//make sure the card's value is not already in the final list
			//If it is, skip to the next index
			if(!containsValue(straightCards,currentValue)){
				straightCards.add(highestCardOfValue(cardArray, currentValue));
			}
				
		}
		return sortByAsc(straightCards);
	
	}
	
	public boolean containsValue(ArrayList<Card> cards, int value){
		for(int i = 0; i < cards.size(); i++){
			if(cards.get(i).value == value){
				return true;
			}
		}
		return false;
	}
	
	public Card highestCardOfValue(ArrayList<Card> cards, int value){
		ArrayList<Card> arrayOfValue = new ArrayList<Card>();
		
		for(int i = 0; i < cards.size(); i++){
			if (cards.get(i).value == value){
				arrayOfValue.add(cards.get(i));
			}
		}
		//we now have an arraylist of all cards of Value value
		arrayOfValue = sortByAsc(arrayOfValue);
		while(arrayOfValue.size() != 1){
			arrayOfValue.remove(0);
		}
		
		return arrayOfValue.get(0);
	}
	
	public boolean isStraight(){
		//this list will track which indices are in the supposed straight
		ArrayList<Integer> indexArray = new ArrayList<Integer>(); 
		
		
		for(int i = 0; i < valueTally.length; i++){
			if(valueTally[i] != 0){
				indexArray.add(i);
			}
		}
		
		//indexArray now contains the indices of all values that are present
		//if there aren't at least 5 different values present, there can be no straight
		if(indexArray.size() < 5){
			return false;
		}
		
		//starting at the end of the array, check if there are 5 in a row at any point
		for(int i = indexArray.size()-1; i >= 4; i--){

			if(indexArray.get(i)-1 ==  indexArray.get(i-1) &&
					indexArray.get(i-1)-1 ==  indexArray.get(i-2) &&
					indexArray.get(i-2)-1 ==  indexArray.get(i-3) &&
					indexArray.get(i-3)-1 ==  indexArray.get(i-4)){
				return true;
			}
			
		}
		
		return false;
	}
	
	//only call this after calling isFlush returns true
	//should have already sorted in ascending order. This method removes the lowest 2 cards. 
	public ArrayList<Card> getTopFiveFlush(){
		ArrayList<Card> flushArray = getFlush();
		
		while (flushArray.size() > 5){
			flushArray.remove(0);
		}
		return flushArray;
	}
	
//	only call this after isFlush returns true
	public ArrayList<Card> getTopFiveStraightFlush(){
		ArrayList<Card> straightFlushArray = getFlush();
		ArrayList<Card> fiveCards = new ArrayList<Card>();
		
		for(int i = (straightFlushArray.size()-5); i >= 0; i--){
			if(straightFlushArray.get(i+4).value - 1 == straightFlushArray.get(i+3).value &&
					straightFlushArray.get(i+3).value - 1 == straightFlushArray.get(i+2).value &&
					straightFlushArray.get(i+2).value - 1 == straightFlushArray.get(i+1).value &&
					straightFlushArray.get(i+1).value - 1 == straightFlushArray.get(i).value){
				fiveCards.add(straightFlushArray.get(i));
				fiveCards.add(straightFlushArray.get(i+1));	
				fiveCards.add(straightFlushArray.get(i+2));	
				fiveCards.add(straightFlushArray.get(i+3));	
				fiveCards.add(straightFlushArray.get(i+4));	
				return fiveCards;
			}
		}
		return fiveCards;
	}
	
	//only call this after calling isFlush returns true
	public boolean isRoyalFlush(){
		ArrayList<Card> flushArray = getFlush();
		
		while (flushArray.size() > 5){
			flushArray.remove(0);
		}
		
		if(flushArray.get(0).value == 10 &&
				flushArray.get(1).value == 11 &&
				flushArray.get(2).value == 12 &&
				flushArray.get(3).value == 13 &&
				flushArray.get(4).value == 14){
			return true;
		}	
		return false;	
	}
	
	//only call this after calling isFlush returns true
	public boolean isStraightFlush(){
		ArrayList<Card> flushArray = getFlush();
		
		for(int i = 0; i < (flushArray.size()-4); i++){
			if(flushArray.get(i+4).value - 1 == flushArray.get(i+3).value &&
					flushArray.get(i+3).value - 1 == flushArray.get(i+2).value &&
					flushArray.get(i+2).value - 1 == flushArray.get(i+1).value &&
					flushArray.get(i+1).value - 1 == flushArray.get(i).value){
				return true;	
			}
		}
		return false;
	}
	
	//only call this after calling isFlush returns true
	//returns 5-7 cards of the same suit
	public ArrayList<Card> getFlush(){
		int flushSuit = -1;
		ArrayList<Card> onSuitCards = new ArrayList<Card>();		
	
		for(int i = 0; i < suitTally.length; i++){
			if(suitTally[i] >= 5){
				flushSuit = i;
			}
		}
					
		for(int i = 0; i < cardArray.size(); i++){
			Card currentCard = cardArray.get(i);
					
			if((currentCard.suitValue - 1) == flushSuit){
				onSuitCards.add(currentCard);
			}
		}
		return onSuitCards;
	}
	
	public boolean isFlush(){
		for(int i = 0; i < suitTally.length; i++){
			if(suitTally[i] >= 5){
				return true;
			}
		}
		return false;
		
	}
	
	//this method adds cards to make a hand of 5
	public ArrayList<Card> makeSomeOfAKind(ArrayList<Card> cards){
		//we now have an arraylist of 2, 3, or 4 cards 
		//this method adds cards to make a hand of 5
			
		while(cards.size() < 5){
			//add highest card not yet used
			int highestCardIndex = -1;
			int highestCardValue = -1;
						
			//for each card in the total group of 7
			for(int i = 0; i < cardArray.size(); i++){
				//if the card is not already in cards array, and its value is higher than the current highValue,that value is now the highest
				if(!cards.contains(cardArray.get(i)) && cardArray.get(i).value >= highestCardValue){
					highestCardIndex = i;
					highestCardValue = cardArray.get(i).value;
				}
			}
			
			//we now have the index of the highest card not already in cards array
			Card highestCard = cardArray.get(highestCardIndex);
			cards.add(highestCard);				
		}
		
		return cards;
	}
	
	//input the type of kind - i.e. 2, 3, or 4 of a kind
	//returns the 2, 3, or 4 of a kind
	public ArrayList<Card> getSomeOfAKind(int count){
		int kindValue = -1;
		ArrayList<Card> onValueCards = new ArrayList<Card>();
		
		//determines which is the highest cardValue that has \count\ cards present 
		for(int i = 0; i < valueTally.length; i++){
			if(valueTally[i] == count){
				kindValue = i + 2;
			}
		}
		
		//adds cards of that value to onValueCards arraylist 
		for(int i = 0; i < cardArray.size(); i++){
			Card currentCard = cardArray.get(i);
			
			if(currentCard.value == kindValue){
				onValueCards.add(currentCard);
			}
		}
		
		//we now have an arraylist of 2, 3, or 4 cards
		
		for(int i = 0; i < onValueCards.size(); i++){
			cardsUsedInSet.add(onValueCards.get(i));
		}
		
		return onValueCards;
	}
	
	//returns 2, 3, or 4,to show how many of a kind
	//only call if isSomeOfAKind returns true
	public int getTypeOfAKind(){
		if(contains(valueTally, 4)){
			return 4;
		} else if(contains(valueTally, 3)){
			return 3;
		} else if(contains(valueTally, 2)){
			return 2;
		} else return 0;
	}
	
	//for use with tempValueTally rather than cardArray's valueTally
	public int getTypeOfAKind(int[] tallyArray){
		if(contains(tallyArray, 4)){
			return 4;
		} else if(contains(tallyArray, 3)){
			return 3;
		} else if(contains(tallyArray, 2)){
			return 2;
		} else return 0;
	}
	
	public boolean isSomeOfAKind(){
		for(int i = 0; i < valueTally.length; i++){
			if(valueTally[i] >= 2){
				return true;
			}
		}
		return false;
	}
	
	public boolean isSomeOfAKind(int[] tallyArray){
		for(int i = 0; i < tallyArray.length; i++){
			if(tallyArray[i] >= 2){
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(int[] intArray, int key){
		for(int i = 0; i < intArray.length; i++){
			if(intArray[i] == key){
				return true;
			}
		}
		return false;
	}
		
	public void clear(int[] array){
		for (int i = 0; i < array.length; i++){
			array[i] = 0;
		}
	}
	
	
}