import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Main {
	static int playersThisGame = 3;

	public Main(){
		
	}
	
	public static void main(String[] args) {
		Holdem h = new Holdem(playersThisGame);
		h.gameLoop();
		
		
	}
	
	
}
