let balance = 500;
let bet = 0;
let deck = [];	

main();

function main(){
		populateDeck();
		playGame();

}

function playGame() {
	
	getBet();
	
	document.getElementById('balance').innerHTML = "You have $" + balance;
	document.getElementById("current-bet").innerHTML = "Current bet: $" + bet;
	
	//bet has been placed, time generate cards.
	
	let opponentCard = getRandomCard()[0];
	let playerCard = getRandomCard()[0];
	
	updateView(opponentCard, playerCard);
	
	let opponentScore = calculateScore(opponentCard);
	let playerScore = calculateScore(playerCard);
	
	
	//see who wins!
	if(playerScore > opponentScore){
		//player wins
		document.getElementsByClassName('outcome-text')[0].innerHTML = "You win!";
		
		balance += (balance * 2);
			
	} else {
		//player loses
		document.getElementsByClassName('outcome-text')[0].innerHTML = "You lose.";

	}
	
}

function calculateScore(card){
	let weightedValue = card.value * 4;

	let weightedSuit = 0;

	switch(card.suit){
		case "clubs": break;
		case "diamonds": weightedSuit = 1;
			break;
		case "hearts": weightedSuit = 2;
			break;
		case "spades": weightedSuit = 3;
			break;
	}

	return weightedValue + weightedSuit;
}

function populateDeck(){
	for(let i = 2; i < 15; i++){
		for(let j = 0; j < 4; j++){
			switch(j){
				case 0: deck.push({"value": i, "suit": "clubs"});
					break;
				case 1: deck.push({"value": i, "suit": "diamonds"});
					break;
				case 2: deck.push({"value": i, "suit": "hearts"});
					break;
				case 3: deck.push({"value": i, "suit": "spades"});
					break;		
			}	
		}	
	}
}

function getBet(){
	let enteredBet = prompt("You have $500. Enter your bet.");
		
	//check to make sure it's a number
	while(enteredBet.trim() == ""){
				console.log('df');
				enteredBet = prompt("Enter your bet.");
			
		while(isNaN(enteredBet)){
			enteredBet = prompt("Please enter a number.");	

			while(enteredBet > balance) {
				enteredBet = prompt("You don't have that much money! Try a new bet.");
			}
		}
	}
	
	balance = balance - enteredBet;
	bet = enteredBet;
}

function getRandomCard(){
	let randomIndex = Math.floor(Math.random(0,1) * deck.length);
	return deck.splice(randomIndex, 1);
}

function updateView(opponentCard, playerCard){
	let oppCardValue = '';
	let plCardValue = '';
	
	switch(opponentCard.value){
		case 11: oppCardValue = 'J';
			break;
		case 12: oppCardValue = 'Q';
			break;
		case 13: oppCardValue = 'K';
			break;
		case 14: oppCardValue = 'A';
			break;
		default: oppCardValue = opponentCard.value;
	}
	
	switch(playerCard.value){
		case 11: plCardValue = 'J';
			break;
		case 12: plCardValue = 'Q';
			break;
		case 13: plCardValue = 'K';
			break;
		case 14: plCardValue = 'A';
			break;
		default: plCardValue = playerCard.value;
	}
	
	document.getElementsByClassName("opp-value")[0].innerHTML = oppCardValue;
	document.getElementsByClassName("opp-suit")[0].innerHTML = opponentCard.suit.charAt(0).toUpperCase();
	document.getElementsByClassName("pl-value")[0].innerHTML = plCardValue;
	document.getElementsByClassName("pl-suit")[0].innerHTML = playerCard.suit.charAt(0).toUpperCase();

	document.getElementsByClassName("opp-value")[1].innerHTML = oppCardValue;
	document.getElementsByClassName("opp-suit")[1].innerHTML = opponentCard.suit.charAt(0).toUpperCase();
	document.getElementsByClassName("pl-value")[1].innerHTML = plCardValue;
	document.getElementsByClassName("pl-suit")[1].innerHTML = playerCard.suit.charAt(0).toUpperCase();
}

