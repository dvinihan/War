let balance = 500;
let bet = 0;



function playGame() {
	let enteredBet = prompt("You have $500. Enter your bet.");
		
	//check to make sure it's a number
	while(isNaN(enteredBet)){
		enteredBet = prompt("Please enter a number.");	
		
		while(enteredBet > balance) {
			enteredBet = prompt("You don't have that much money! Try a new bet.");
		}
		
	}
	

	
	balance = balance - enteredBet;
	bet = enteredBet;
	
	
	document.getElementById('balance').innerHTML = "You have $" + balance;
	document.getElementById("current-bet").innerHTML = "Current bet: $" + bet;
	
	
	//bet has been placed, time to see who wins.
	
	
	
	
}


playGame();