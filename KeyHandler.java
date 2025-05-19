// Tyler Nguyen and Andy Lai, 2024/06/15
// Class: KeyHandler
// Description: Class which handles the key inputs of the user

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	boolean jumpPressed;
	boolean crouchPressed;
	boolean leftPressed;
	boolean rightPressed;
	boolean dashPressed;

	// Default keybinds
	public static int moveRight = KeyEvent.VK_RIGHT;
	public static int moveLeft = KeyEvent.VK_LEFT;
	public static int moveUp = KeyEvent.VK_UP;
	public static int moveDown = KeyEvent.VK_DOWN;
	public static int moveDash = KeyEvent.VK_X;
	public static int moveJump = KeyEvent.VK_C;
	
	public void keyTyped(KeyEvent e) {}

	// Description: gets if users presses key  
	// Parameters: takes in KeyEvent e
	// Return: nothing
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode(); // get the key code of input
		if(GamePanel.gameState == 1) { // main screen
			if(code == moveDash || code == moveJump) { // if player wants to start game
				Driver.popup.setVisible(true); // displays pop up where user enters username
			}
		}
		else if(GamePanel.gameState == 2) { // in game
			if(code == moveRight) { // player presses move right key
				rightPressed = true; 
				Player.directionX = 2; // set direction right
				GamePanel.camera.cameraDirectionX = 2; // set direction right
			}
			if(code == moveLeft) { // player presses move left key
				leftPressed = true;
				Player.directionX = 1; // set direction left
				GamePanel.camera.cameraDirectionX = 1; // set direction left
			} 
			if(code == moveDown) { // player presses move down key
				crouchPressed = true;
				Player.directionY = 2;  // set direction down
				GamePanel.camera.cameraDirectionY = 2;  // set direction down
			}
			if(code == moveUp) { // player presses move up key
				Player.directionY = 1;  // set direction up
				GamePanel.camera.cameraDirectionY = 1; // set direction up
			}
			if(code == moveJump) { // player presses jump key
				jumpPressed = true;
			}
			if(code == moveDash){ // player presses dash key
				dashPressed = true;
			}
			if(code == KeyEvent.VK_ESCAPE){ // player presses escape
				GamePanel.previousGameState = GamePanel.gameState;
				GamePanel.gameState = 3; // pause screen
			}

			// Debugging
			// if(code == 192) { // (tilda key)
			// 	// back tick is the key and reset timer
			// 	GamePanel.timer = 0;
			// 	GamePanel.currentMap = 0;
			// 	Player.reset();
			// 	System.out.println("Reset");
			// }
			if(code == KeyEvent.VK_DELETE) { // if player presses delete -> cheats enabled (invicibility && infinite dashes (can fly basically))
				GamePanel.invincible = !GamePanel.invincible;
				System.out.println("Invincible: " + GamePanel.invincible);
			}
		}
		else if(GamePanel.gameState == 3){ // pause screen
			if(code == KeyEvent.VK_ESCAPE){ // if player presses escape returns back to game
				GamePanel.previousGameState = GamePanel.gameState;
				GamePanel.gameState = 2;
			}
		}
		else if(GamePanel.gameState == 4) { // options
			if(code == KeyEvent.VK_ESCAPE){ 
				if(GamePanel.previousGameState == 3){ // return back to pause screen
					GamePanel.previousGameState = GamePanel.gameState;
					GamePanel.gameState = 3;
				}
				else{ // return back to main screen
					GamePanel.previousGameState = GamePanel.gameState;
					GamePanel.gameState = 1;
				}
				GamePanel.changeLeft = false;
				GamePanel.changeDown = false;
				GamePanel.changeRight = false;
				GamePanel.changeUp = false;
				GamePanel.changeDash = false;
				GamePanel.changeDown = false;
			}
			// Keybind changes
			if(GamePanel.changeLeft) {
				moveLeft = code;
				GamePanel.changeLeft = false;
			}
			else if(GamePanel.changeDown) {
				moveDown = code;
				GamePanel.changeDown = false;
			}
			else if(GamePanel.changeRight) {
				moveRight = code;
				GamePanel.changeRight = false;
			}
			else if(GamePanel.changeUp) {
				moveUp = code;
				GamePanel.changeUp = false;
			}
			else if(GamePanel.changeDash) {
				moveDash = code;
				GamePanel.changeDash = false;
			}
			else if(GamePanel.changeJump) {
				moveJump = code;
				GamePanel.changeJump = false;
			}
		}
		else if(GamePanel.gameState == 5) { // leaderboard 
			if(code == KeyEvent.VK_ESCAPE){ // return to main
				GamePanel.gameState = 1;
			}
		}
		else if(GamePanel.gameState == 6) { // about
			if(code == KeyEvent.VK_ESCAPE){ // return to main
				GamePanel.gameState = 1;
			}
		}
	}

	// Description: gets if users releases key  
	// Parameters: takes in KeyEvent e
	// Return: nothing 
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(GamePanel.gameState == 2) {
			if(code == moveRight) { // release right key
				rightPressed = false;
				Player.directionX = 0;
			}
			if(code == moveLeft) { // release left key
				leftPressed = false;
				Player.directionX = 0;
			}
			if(code == moveDown) { // release down key
				crouchPressed = false;
				Player.directionY = 0;
				GamePanel.camera.cameraDirectionY = 0;
			}
			if(code == moveUp) { // release up key
				Player.directionY = 0;
				GamePanel.camera.cameraDirectionY = 0;
			}
			if(code == moveJump) { // release jump key
				jumpPressed = false;
			}
			if(code == moveDash){ // release dash key
				dashPressed = false;
			}
		}
	}
}