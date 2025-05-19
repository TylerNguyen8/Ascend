// Tyler Nguyen and Andy Lai, 2024/06/15
// Class: GamePanel
// Description: main class that puts all the logic of the other classes together

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	// SCREEN SETTINGS
	final static int originalTileSize = 16; // 16x16 tile
	final static int scale = 3;
	
	final static int tileSize = originalTileSize * scale; // 48x48 tile
	final static int screenWidth = 1280; // 1280;
	final static int screenHeight = 720; // 720;
	
	// FPS
	int FPS = 60;
	static int frameCount = 0;
	static long timer = 0;
	static Timer time = new Timer(0);

	// Misc
	public static int gameState = 1; // set game states (1 - main, 2 - in game, 3 - pause, 4 - options, 5 - leaderboard, 6 - about)
	public static int previousGameState = 0;
	public static double a = 0; // Angle of Sine Wave
	public static boolean wave = true;
	public static boolean nameAdded = false;
	public static boolean invincible = false; // player invincibility
	public static int alpha = 0; // alpha of ending screen
	public static int transitionTime = 13; // time of ending screen
	public static Font customFont20;
	public static Font customFont50;

	// Leaderboard
	BufferedImage leaderboard;
	BufferedReader fileIn;
	PrintWriter fileOut;
	public static TreeMap<Score, Long> scores = new TreeMap<>();
	public static String username = "null";

	// Settings
	BufferedImage settings;
	public static boolean changeRight = false;
	public static boolean changeDown = false;
	public static boolean changeLeft = false;
	public static boolean changeUp = false;
	public static boolean changeDash = false;
	public static boolean changeJump = false;
	public static boolean sfxOnOff = true;

	// About
	BufferedImage about;

	// Images
	BufferedImage mainScreen;
	BufferedImage title;
	BufferedImage pauseScreen;
	BufferedImage ending;

	// Maps
	public static ArrayList<Map> maps = new ArrayList<>();
	public static int currentMap = 0;
	public static int numOfMaps = 11;
	
	static Camera camera = new Camera(screenWidth, screenHeight); // create camera object
	static float camPosX; // for later on in paint component to set camera position x
	static float camPosY; // for later on in paint component to set camera position y
	public static KeyHandler keyH = new KeyHandler(); // initialize keyhandler object
	public static MouseHandler mouseH = new MouseHandler(); // intiialize mousehandler object
	Thread gameThread;
	
	// Description: creates new Gamepanel that can be inserted intro driver window
	// Parameters: nothing
	// Return: nothing (will be used to display in-game graphics)
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.addMouseListener(mouseH);
		this.setFocusable(true);
	}
	
	// Description: starts game
	// Parameters: nothing
	// Return: nothing (starts the game)
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void run() {
		// Stuff to do before program run
		map(); // load in all the maps
		camera.setZoom(1.3f); // set the in-game zoom of the game
		// load in images
		try {
			mainScreen = ImageIO.read(new File("./Images/MainScreen.png"));
			title = ImageIO.read(new File("./Images/Title.png"));
			about = ImageIO.read(new File("./Images/about.png"));
			settings = ImageIO.read(new File("./Images/settings.png"));
			leaderboard = ImageIO.read(new File("./Images/leaderboard.png"));
			pauseScreen = ImageIO.read(new File("./Images/pauseScreen.png"));
			ending = ImageIO.read(new File("./Images/Ending.png"));
			Audio.playSoundtrack("playsoundtrack");
			customFont20 = Font.createFont(Font.TRUETYPE_FONT, new File("./editundo.ttf")).deriveFont(20f);
			customFont50 = Font.createFont(Font.TRUETYPE_FONT, new File("./editundo.ttf")).deriveFont(50f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			//register the font
			ge.registerFont(customFont20);
			ge.registerFont(customFont50);
		} catch (IOException e) {
			System.out.println("Could not find images.");
		} catch (FontFormatException e) {
			System.out.println("Font error");
		}

		double drawInterval = 1000000000/FPS; // amount of time to draw frame
		double nextDrawTime = System.nanoTime() + drawInterval; // amount of time till next frame drawn
		while(gameThread != null) {
			update(); // update information
			repaint(); // draw the screen with updated information
			if(gameState == 2 && nameAdded){
				timer += drawInterval/1000000; // add time to timer to keep track of player time
				time.refactor(timer); // reorganizes information given by timer
			}
			try {
				double remainingTime = (nextDrawTime - System.nanoTime())/1000000; // remaining time left to draw frame
				if(remainingTime < 0) { // if update took more time than drawInterval
					remainingTime = 0;
				}
				Thread.sleep((long)remainingTime); // sleep
				nextDrawTime += drawInterval;
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
	
	// Description: updates everything every frame (player movement, collisions, items)
	// Parameters: nothing
	// Return: nothing (updates the game so that the game runs smoothly)
	public void update() {
		Player.move();
		if(frameCount % 60 == 0 && gameState == 7) {
			transitionTime--;
		}
		if(gameState == 7){
			alpha += 10;
			if(alpha >= 255){
				alpha = 255;
			}
		}
		Collisions.checkCollisions();
		Item.update(maps.get(currentMap).getMap(), maps.get(currentMap).getMapRectangle());
		frameCount++;
	}
	
	// Description: does all the graphics for the game
	// Parameters: nothing
	// Return: nothing (displays, moves, translates, and adjusts graphics accordingly)
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		if(gameState == 1) {
			// sin wave for title screen
			if(a == 90){
				wave = false;
			}
			else if (a == -90){
				wave = true;
			}
			if (wave == true){
				a+=1;
			}
			else if (wave == false){
				a-=1;
			}
			g2.drawImage(mainScreen, 0, 0, null);
			g2.drawImage(title, screenWidth / 2 - 190 , (int)(Math.sin(Math.toRadians(a))*100)/4 + 50, null);
		}
		else if(gameState == 2) {
			camera.update(); // updates camera position
			camera.applyTransformation(g2); // applies transformations on graphics
			if(maps.get(currentMap).getMap()[0].length*tileSize > screenWidth/camera.getZoom()){ // if map size x is greater than screen
				if(Player.player.x + Player.player.getWidth()/2 <= screenWidth/2/camera.getZoom() + tileSize){ // if player is on the left side of the map
					camPosX = screenWidth/2/camera.getZoom() + tileSize; // sets camera one more than tile of the left
				}
				else if(Player.player.x + Player.player.getWidth()/2 >= maps.get(currentMap).getMap()[0].length*tileSize-screenWidth/2/camera.getZoom() - tileSize){ // if player is on the right side of the map
					camPosX = maps.get(currentMap).getMap()[0].length*tileSize-screenWidth/2/camera.getZoom() - tileSize; // // sets camera one more than tile than the right
				}
				else{
					camPosX = Player.player.x + (float)Player.player.getWidth()/2; // follow player
					if(camera.cameraDirectionX == 1){
						camPosX -= tileSize; // offsets camera so that player can see one more tile in front of them
					}
					else if(camera.cameraDirectionX == 2){
						camPosX += tileSize; // offsets camera so that player can see one more tile in front of them
					}
				}
			}
			else{ // if screen x is larger than map size x
				camPosX = screenWidth/2/camera.getZoom();
			}
			if(maps.get(currentMap).getMap().length*tileSize > screenHeight/camera.getZoom()){ // if map size y is greater than screen
				if(Player.player.y + Player.player.getHeight()/2 <= screenHeight/2/camera.getZoom() + tileSize){ // if player is on the top side of the map
					camPosY = screenHeight/2/camera.getZoom() + tileSize; // sets camera one more than the top
				}
				else if(Player.player.y + Player.player.getHeight()/2 >= maps.get(currentMap).getMap().length*tileSize - screenHeight/2/camera.getZoom() - tileSize){ // if player is on the bottom side of the map
					camPosY = maps.get(currentMap).getMap().length*tileSize - screenHeight/2/camera.getZoom() - tileSize; // sets camera on more than the bottom
				}
				else{
					camPosY = Player.player.y + (float)Player.player.getHeight()/2; // follow player
					if(camera.cameraDirectionY == 1){
						camPosY -= tileSize; // offsets camera so that player can see one more tile in front of them
					}
					else if(camera.cameraDirectionY == 2){
						camPosY += tileSize; // offsets camera so that player can see one more tile in front of them
					}
				}
			}
			else{ // if screen y is larger than map size y
				camPosY = screenHeight/2/camera.getZoom();
			}
			camera.setPosition(camPosX, camPosY); // set position of camera
		}
		else if(gameState == 3){ // pause state
			camera.update(); // for the background
			camera.applyTransformation(g2);
			g2.drawImage(pauseScreen, 0, 0, null); // draws pause screen
		}
		else if(gameState == 4) { // options
			g2.drawImage(settings, 0, 0, null); // draws settings screen
			g2.setFont(customFont50); // custom pixel font
			g2.setColor(Color.white);

			// Draws based on player keybind input
			if(KeyHandler.moveRight == KeyEvent.VK_RIGHT) {
				g2.drawString(">", 578, 430);
			}
			else {
				g2.drawString((char)KeyHandler.moveRight + "", 578, 430);
			}

			if(KeyHandler.moveDown == KeyEvent.VK_DOWN) {
				g2.drawString("v", 505, 430);
			}
			else {
				g2.drawString((char)KeyHandler.moveDown + "", 505, 430);
			}

			if(KeyHandler.moveLeft == KeyEvent.VK_LEFT) {
				g2.drawString("<", 433, 430);
			}
			else {
				g2.drawString((char)KeyHandler.moveLeft + "", 433, 430);
			}

			if(KeyHandler.moveUp == KeyEvent.VK_UP) {
				g2.drawString("^", 505, 358);
			}
			else {
				g2.drawString((char)KeyHandler.moveUp + "", 505, 358);
			}
			g2.drawString((char)KeyHandler.moveDash + "", 712, 430);
			g2.drawString((char)KeyHandler.moveJump + "", 810, 430);

			// Draws base on whether music enabled or not
			if(Audio.enableMusic) {
				g2.drawString("x", 567, 595);
			}
			// Draws base on whether sfx enabled or not
			if(Audio.enableSFX) {
				g2.drawString("x", 567, 665);
			}
		}
		else if(gameState == 5) { // leaderboard
			try {
				g2.drawImage(leaderboard, 0, 0, null);

				String line;
				int num = 0;

				FontMetrics metrics = g.getFontMetrics(customFont20);
				
				int x;
				int y;

				fileIn = new BufferedReader(new FileReader("scores.txt"));

				g2.setColor(Color.WHITE);
				g2.setFont(customFont20);

				// Read in text file
				while((line = fileIn.readLine()) != null) {
					String lineSplit[] = line.split(" ");
					Score temp = new Score(lineSplit[0], Long.parseLong(lineSplit[1]));
					scores.put(temp, temp.getScore());
				}

				// Display Score
				for (Entry<Score, Long> entry : scores.entrySet()) {
					x = screenWidth / 2 - (metrics.stringWidth(entry.getKey().getName() + " - " + new Timer(entry.getValue()).toString()) / 2);
					y = screenHeight / 2 - (metrics.getHeight() / 2 + metrics.getAscent());
					num ++;
					if(num <= 5) {
						g.drawString(entry.getKey().getName() + " - " + new Timer(entry.getValue()).toString(), x, y + num * 50);
					}
				}				

				// Save score
				fileOut = new PrintWriter(new FileWriter("scores.txt"));
				for (Score score : scores.keySet()) {
					fileOut.println(score.getName() + " " + score.getScore());
				}		
				fileOut.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		else if(gameState == 6) { // about screen
			g2.drawImage(about, 0, 0, null);
		}
		else if(gameState == 7){ // ending screen
			camera.update();
			camera.applyTransformation(g2);
			g2.setColor(new Color(255, 255, 255, alpha));
			g2.fill(new Rectangle(0, 0, 1280, 720));
			if(transitionTime <= 0){ // if timer hits zero, reset game and put user back on beginning screen
				previousGameState = GamePanel.gameState;
                gameState = 1;
                timer = 0;
				currentMap = 0;
				alpha = 0;
				transitionTime = 13;
				Player.reset();
			}
			if(transitionTime <= 9){ // if has less than 15 seconds less, display message
				g2.setColor(Color.BLACK);
				g2.setFont(customFont50);
				g2.drawString("Thank you for playing!", 1280/2, 720/2);
				g2.drawImage(ending, 50, 150, null);
			}
			if(transitionTime <= 6){ // if has less than 5 seconds less, display game time completion
				g2.setColor(Color.BLACK);
				g2.setFont(customFont50);
				g2.drawString("Your Time: " + time, 1280/2, 720/2 + 65);
			}
		}
		g.dispose(); // disposes all images (to save memory and make game run more smoothly)
	}

	// Description: loads in all the maps specified
	// Parameters: nothing
	// Return: nothing (loads in all maps and adds them to the maps arraylist)
	public void map(){
		for(int i = 0; i < numOfMaps; i++){
			maps.add(new Map(i));
		}
	}
}