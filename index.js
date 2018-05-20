let texts = ['Ready to play?',
						'There are no ties - suits add value!',
						'You\'ll be playing against the computer.',
						'Good luck!!',''];
let textCounter = 0;


function advanceText(){
	document.getElementById('title').innerHTML = texts[textCounter];
	
	if(textCounter == texts.length -1) {
		window.location.href = "game.html";
		return;
	}
	
	textCounter++; 
}

