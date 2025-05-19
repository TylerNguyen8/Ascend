// Tyler Nguyen and Andy Lai, 2024/06/15
// Class: Map
// Description: Class which handles all the logic with loading and displaying maps

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Map {
	private int mapNum; // which map number correlates to this map
	private String[] startPosition;
	private int startX; // start position of player x
	private int startY; // start position of player y
	private String[] returnPosition;
	private int returnX; // return position of player x
	private int returnY; // return position of player y
	private String[] mapSize;
	private int[][] map; // numbered map
	private Rectangle[][] mapRectangle; // tiled map

	
	/* 
	no tile -> 0
	normal tile -> 1
	spike bottom -> 2
	spike top -> 3
	spike left -> 4
	spike right -> 5
	bouncer -> 6
	item -> 7
	->
	previous lvl Left -> 9
	next lvl right -> 10
	*/

	// Description: creates map based on text file
	// Parameters: the map number
	// Return: nothing (used to initialize a map object)
	public Map(int mapNum) {
		this.mapNum = mapNum;
		try {
			Scanner s = new Scanner(new File("./Maps/" + mapNum + ".txt")); // scanner to read in the txt file for the map
			mapSize = s.nextLine().split(" "); // first line of text file is the map size (# of col, # of rows)
			map = new int[Integer.parseInt(mapSize[0])][Integer.parseInt(mapSize[1])]; // sets size of 2d array (numbered)
			mapRectangle = new Rectangle[Integer.parseInt(mapSize[0])][Integer.parseInt(mapSize[1])]; // sets size of 2d array (tiled) 
			startPosition = s.nextLine().split(" "); // seconds line of text file is the start position
			startX = Integer.parseInt(startPosition[0]); // x start position
			startY = Integer.parseInt(startPosition[1]); // y start position
			returnPosition = s.nextLine().split(" "); // third line of text file is the return position
			returnX = Integer.parseInt(returnPosition[0]); // x return position
			returnY = Integer.parseInt(returnPosition[1]); // y return position
			for(int row = 0; row < map.length; row++){ // loads in text file into the 2d array (numbered)
				StringTokenizer line = new StringTokenizer(s.nextLine(), " ");
				for(int col = 0; col < map[0].length; col++){
					map[row][col] = Integer.parseInt(line.nextToken());
				}
			}
			for(int row = 0; row < map.length; row++){ // loads in tiles into the tiled 2d array based on numbered 2d array
				for(int col = 0; col < map[0].length; col++){
					if(map[row][col] == 1){
						mapRectangle[row][col] = new Rectangle(col*GamePanel.tileSize, row*GamePanel.tileSize, GamePanel.tileSize, GamePanel.tileSize);
					}
					else if(map[row][col] == 2){
						mapRectangle[row][col] = new Rectangle(col*GamePanel.tileSize+6, row*GamePanel.tileSize + GamePanel.tileSize/2, GamePanel.tileSize-12, GamePanel.tileSize/2);
					}
					else if(map[row][col] == 3){
						mapRectangle[row][col] = new Rectangle(col*GamePanel.tileSize+6, row*GamePanel.tileSize, GamePanel.tileSize-12, GamePanel.tileSize/2);
					}
					else if(map[row][col] == 4){
						mapRectangle[row][col] = new Rectangle(col*GamePanel.tileSize, row*GamePanel.tileSize+6, GamePanel.tileSize/2, GamePanel.tileSize-12);
					}
					else if(map[row][col] == 5){
						mapRectangle[row][col] = new Rectangle(col*GamePanel.tileSize + GamePanel.tileSize/2, row*GamePanel.tileSize+6, GamePanel.tileSize/2, GamePanel.tileSize-12);
					}
					else if(map[row][col] == 6){
						mapRectangle[row][col] = new Rectangle(col*GamePanel.tileSize+7, row*GamePanel.tileSize + GamePanel.tileSize/2, GamePanel.tileSize-14, GamePanel.tileSize/2);
					}
					else if(map[row][col] == 7){
						mapRectangle[row][col] = new Rectangle(col*GamePanel.tileSize, row*GamePanel.tileSize, GamePanel.tileSize, GamePanel.tileSize);
					}
					else if(map[row][col] == 8){
						mapRectangle[row][col] = new Rectangle(col*GamePanel.tileSize, row*GamePanel.tileSize, GamePanel.tileSize, GamePanel.tileSize);
					}
					else if(map[row][col] == 9){
						mapRectangle[row][col] = new Rectangle(col*GamePanel.tileSize, row*GamePanel.tileSize, GamePanel.tileSize/2, GamePanel.tileSize);
					}
					else if(map[row][col] == 10){
						mapRectangle[row][col] = new Rectangle(col*GamePanel.tileSize+GamePanel.tileSize/2, row*GamePanel.tileSize, GamePanel.tileSize/2, GamePanel.tileSize);
					}
				}
			}
			s.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("File Not Found.");
		}
	}

	// Description: start x position getter
	// Parameters: nothing
	// Return: the start x position of this map
	public int getStartX() {
		return this.startX;
	}

	// Description: start y position getter
	// Parameters: nothing
	// Return: the start y position of this map
	public int getStartY() {
		return this.startY;
	}

	// Description: return x position getter
	// Parameters: nothing
	// Return: the return x position of this map
	public int getReturnX() {
		return this.returnX;
	}

	// Description: return y position getter
	// Parameters: nothing
	// Return: the return y position of this map
	public int getReturnY() {
		return this.returnY;
	}

	// Description: getter method for the numbered map
	// Parameters: nothing
	// Return: the 2d array of the numbered map
	public int[][] getMap(){
		return this.map;
	}

	// Description: setter method for the numbered map
	// Parameters: 2d array map to replace the original map
	// Return: nothing
	public void setMap(int[][] map) {
		this.map = map;
	}
	
	// Description: getter method for the tiled map
	// Parameters: nothing
	// Return: the 2d array of the tiled map
	public Rectangle[][] getMapRectangle(){
		return this.mapRectangle;
	}
}
