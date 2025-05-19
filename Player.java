// Tyler Nguyen and Andy Lai, 2024/06/15
// Class: Player
// Description: Class which handles all the logic of the player (movement, death, position, etc)

import java.awt.Rectangle;

public class Player {
	// Player variables
    // Set player's default position
	static int playerX = GamePanel.maps.get(GamePanel.currentMap).getStartX();
	static int playerY = GamePanel.maps.get(GamePanel.currentMap).getStartY();

	// Player speed, velocity, acceleration, deceleration, direction, dash properties
	static double playerSpeedX = 6; // default speed x
	static double playerSpeedY = 12; // defualy speed y
	static double playerVelX = 0; // player velocity x
	static double playerVelY = 0; // player velocity y
	static double gravity = 0.8; // player gravity
	static double acceleration = 4; // acceleration when player moves and stops
	static double deceleration = 2; // deceleration when player moves and stops
	static int directionX = 0; // direction player faces x (1 - left; 2 right)
	static int directionY = 0; // direction player faces y (1 - up; 2 - down)
	static boolean airborne = false; // if player airborne
	static boolean onWall = false; // if player on wall
	static boolean dashed = false; // so that when player hits edge while dash, do continue to dash and not lach onto walls
    static int dashTimer = 20; // time it takes for a dash
    static int dashNum = 1; // number of dashes player has
	static boolean death = false; // if player died
	static Rectangle player = new Rectangle(playerX, playerY, 40, 48); // player hitbox (rectangle)

	// Description: updates the player position based on key presses
	// Parameters: nothing
	// Return: nothing (updates player position)
    public static void move() {
		if(GamePanel.keyH.crouchPressed) {} // player crouch
        if(!GamePanel.keyH.leftPressed && !GamePanel.keyH.rightPressed) { // player deceleration if nothing pressed
			if (playerVelX > 0){
				playerVelX -= deceleration; // moving right deceleration
			}
			else if(playerVelX < 0){
				playerVelX += deceleration; // moving left deceleration
			}
			else{
				playerVelX = 0; // stops moving
			}
		}
		if(GamePanel.keyH.leftPressed) { // moving left
            if(playerVelX == -playerSpeedX){
                playerVelX = -playerSpeedX; // max left speed
            }
			else if(playerVelX <= -playerSpeedX){
				playerVelX += deceleration/2; // decceleration if greater than max speed
			}
			else{
				playerVelX -= acceleration/2; // left acceleration if less than max speed
			}
			Audio.walkAudio("dirt"); // plays walking audio
		}
		if(GamePanel.keyH.rightPressed) { // moving right
            if (playerVelX == playerSpeedX){
                playerVelX = playerSpeedX ; // max right speed
            }
			else if(playerVelX >= playerSpeedX){ 
				playerVelX -= deceleration/2; // decceleration if greater than max speed
			}
			else{
				playerVelX += acceleration/2; // right acceleration if less than max speed
			}
			Audio.walkAudio("dirt"); // plays walking audio
		}
		if(onWall) { // if player is on wall
            playerVelY = -2; // player slide down on wall
			if(GamePanel.keyH.jumpPressed){ // if jump pressed on wall
				playerVelY = playerSpeedY-2; // set player vel y
				airborne = true; 
				onWall = false;
				if(playerVelX < 0){ // if player is holding left
					Audio.playAudio("jump_wall_left");
					playerVelX = 16; // invert vel x
				}
				else if (playerVelX > 0){ // if player is holding right
					Audio.playAudio("jump_wall_right");
					playerVelX = -16; // invert vel x
				}
			}
		}
		else if(airborne) { // if airborne
			playerVelY -= gravity;
		}
		else if(GamePanel.keyH.jumpPressed) { // player jump
			Audio.playAudio("jump");
			airborne = true;
			onWall = false;
			playerVelY = playerSpeedY;
		}
		else{
			playerVelY -= gravity; // if player falls of platform
		}
        if(GamePanel.keyH.dashPressed && dashNum > 0 && !dashed && (GamePanel.keyH.leftPressed || GamePanel.keyH.rightPressed || Player.directionY != 0)){ // dash controls
            dashTimer = 20; // reset dash timer
            dashNum--; // decrease num of dahses
			if(directionX == 1 || directionX == 2 || directionY == 1 || directionY == 2) {
				if(directionX == 1){ // left dash
					Audio.playAudio("dash_left");
					playerVelX = -18;
				}
				else if(directionX == 2) { // right dash
					Audio.playAudio("dash_right");
					playerVelX = 18;
				}
				else{
					if((int)(Math.random()*(2-1+1)+1) == 1){ // random dash audio
						Audio.playAudio("dash_left");
					}
					else{
						Audio.playAudio("dash_right");
					}
				}
				if(directionY == 1){ // up dash
					playerVelY = 16;
				}
				else if(directionY == 2){ // down dash
					playerVelY = -16;
				}
			}
			else {
				Audio.playAudio("dash_right");
				playerVelX = 0;
				playerVelY = 0;
			}
			
        }
        if(dashTimer > 0){ // dash cooldown counter
			GamePanel.camera.moveRaw((int)(Math.random()*(8-(-8)+1)+(-8)), (int)(Math.random()*(8-(-8)+1)+(-8))); // camera shake
            dashTimer--;
			dashed = true;
			airborne = true;
			if(directionY == 0 && directionX != 0){ // left & right dash no Y velocity
				playerVelY = 0;
			}
        }
		else{
			dashed = false; // after timer up, dahsed is false
		}
		if(GamePanel.invincible){ // if cheats enabled, can fly around map with dashes
			Player.dashTimer = 0;
			Player.dashNum = 1;
		}
		//System.out.println("VelX: " + playerVelX + "\t\tVelY: " + playerVelY);
		player.x += playerVelX;
		player.y -= playerVelY;
	}

	// Description: resets player position and map to the start of the level
	// Parameters: nothing
	// Return: nothing (resets player and map)
	public static void reset(){
		player.x = GamePanel.maps.get(GamePanel.currentMap).getStartX();
		player.y = GamePanel.maps.get(GamePanel.currentMap).getStartY();
		GamePanel.keyH.jumpPressed = false;
		GamePanel.keyH.crouchPressed = false;
		GamePanel.keyH.leftPressed = false;
		GamePanel.keyH.rightPressed = false;
		GamePanel.keyH.dashPressed = false;
		playerVelX = 0;
		playerVelY = 0;
		for (Item item : Item.itemList){
			item.setRegenTime(0);
		}
		death = true;
	}
}
