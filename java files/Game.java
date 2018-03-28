import java.util.ArrayList;
import java.util.Scanner;

public abstract class Game {

	Scanner sc = new Scanner(System.in);

	int numPlayers;
	int startingHandNumCards;

	Deck deck;
	Table table;

	ArrayList<Player> playerArray = new ArrayList<Player>();
	int[] scoreArray;

	public Game(int numPlayers, int startingHandNumCards) {
		this.numPlayers = numPlayers;
		this.deck = new Deck();
		this.table = new Table();
		this.startingHandNumCards = startingHandNumCards;
		this.scoreArray = new int[numPlayers];
	}
	
	//what you call in the main class to start a Game
	public abstract void gameLoop();

	public void startGame(String gameType) {
		deck.shuffle();

		// initialize new players so they're ready to play
		for (int i = 0; i < numPlayers; i++) {
			// name the new player
			String newPlayerName = "Player " + (i + 1);
			// create new player
			Player newPlayer = new Player(newPlayerName, gameType);
			// add the player to the game
			playerArray.add(newPlayer);
			// deal the player cards
		}
	}

	public void deal(Player p, Deck d, int numCards) {
		for (int i = 0; i < numCards; i++) {
			Card currentCard = d.drawCard();
			p.addCardToHand(currentCard);
		}
	}

	public void printPlayers() {
		for (int i = 0; i < numPlayers; i++) {
			System.out.println(playerArray.get(i));
		}
	}

	public void printTable() {
		System.out.println(table);
	}

	public void printScore() {
		for (int i = 0; i < numPlayers; i++) {
			String name = playerArray.get(i).playerName;
			int score = scoreArray[i];
			System.out.println(name + " has " + score + " points.");
		}
		System.out.println();
	}

	public void clearScreen() {
		for (int i = 0; i < 50; i++)
			System.out.println();
	}
}

