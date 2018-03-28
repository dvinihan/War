let texts = ['Welcome to Texas Holdem\'!',
						'Ready to play?',
						'This is a one-player game of Holdem.',
						'You\'ll be playing against the computer.',
						'Good luck!!',''];
let textCounter = 0;


function advanceText(){
	document.getElementById('title').innerHTML = texts[textCounter];
	
	if(textCounter >= 5) {
		loadGame();
		return;
	}
	
	textCounter++;
}

function loadGame() {
	window.location.href = "game.html";
}






