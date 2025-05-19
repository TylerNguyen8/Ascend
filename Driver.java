// Tyler Nguyen and Andy Lai, 2024/06/15
// Class: Driver
// Description: Class which sets up basic JFrame and running of the window

import java.io.IOException;
import javax.swing.JFrame;

public class Driver {
	// Popup variable
	public static Popup popup;

	// Description: main method to run the program, create main JFrame, and create popup
	// Parameters: takes in String[] args
	// Return: nothing, but drives the program and creates windows
	public static void main(String[] args) throws IOException{
		// Create JFrame
		JFrame window = new JFrame("Ascend");

		// Create Popup
		popup = new Popup();

		// Update properties of window
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		// Create GamePanel
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		// Update properties of window
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		gamePanel.startGameThread();
	}
}
