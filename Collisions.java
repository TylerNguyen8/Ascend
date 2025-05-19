// Tyler Nguyen and Andy Lai, 2024/06/15
// Class: Collisions
// Description: Class which handles all the logic with player collisions with the map

import java.awt.Rectangle;

public class Collisions {
    /* 
	no tile -> 0
	normal tile -> 1
	spike bottom -> 2
	spike top -> 3
	spike left -> 4
	spike right -> 5
	bouncer -> 6
	item -> 7
    BrokenTile -> 8
	*/

    // Description: checks wall and platform collisions of player
	// Parameters: nothing
	// Return: nothing (makes sure player cannot move through walls and platforms)
    public static void checkCollisions(){
        Player.onWall = false;
        platformCollisions(GamePanel.maps.get(GamePanel.currentMap).getMapRectangle(), GamePanel.maps.get(GamePanel.currentMap).getMap());
    }

    // Description: platform collisions of player
	// Parameters: nothing
	// Return: nothing (makes sure player cannot move through walls and platforms)
    public static void platformCollisions(Rectangle[][] mapRectangle, int[][] map) {
        // Loops through each tile of the map to see if player intersects a tile
        for(int i = 0; i < mapRectangle.length; i++) {
            for(int j = 0; j < mapRectangle[0].length; j++) {
                if(mapRectangle[i][j] == null){} // if tile does not exist
                else if (Player.player.intersects(mapRectangle[i][j])){ // if player intersects tile
                    double left1 = Player.player.getX(); // playerX
                    double right1 = Player.player.getX() + Player.player.getWidth(); // player width + x pos
                    double top1 = Player.player.getY(); // playerY
                    double bottom1 = Player.player.getY() + Player.player.getHeight(); // player height + x y pos
                    double left2 = mapRectangle[i][j].getX(); // tile x position
                    double right2 = mapRectangle[i][j].getX() + mapRectangle[i][j].getWidth(); // tile width + x pos
                    double top2 = mapRectangle[i][j].getY(); // tile y position
                    double bottom2 = mapRectangle[i][j].getY() + mapRectangle[i][j].getHeight(); // tile height + y pos
                    if(map[i][j] == 2 || map[i][j] == 3 || map[i][j] == 4 || map[i][j] == 5){ // if player intersects a tile
                        if(!GamePanel.invincible) { // if player is not invincible, player resets
                            Audio.playAudio("death");
                            Audio.playAudio("deathReverse");
                            Player.reset();
                        }
                    }
                    else if(map[i][j] == 6){ // if player jumps on spring
                        Audio.playAudio("spring");
                        Player.onWall = false;
                        Player.playerVelY = 18;
                        Player.dashNum = 1;
                        Player.airborne = true;
                    }
                    else if(map[i][j] == 7) { // if player touches powerup (dash refresh)
                        Audio.playAudio("powerup");
                        map[i][j] = 0;
                        mapRectangle[i][j] = null;
                        Item.itemList.add(new Balloon(7, GamePanel.currentMap, i, j, 5));
                        Player.dashNum = 1;
                    }
                    else if(map[i][j] == 9){ // player goes back to previous level
                        GamePanel.currentMap--;
                        Player.playerVelX = 0;
                        Player.player.x = GamePanel.maps.get(GamePanel.currentMap).getReturnX();
                        Player.player.y = GamePanel.maps.get(GamePanel.currentMap).getReturnY();
                    }
                    else if(map[i][j] == 10){ // player goes to next level
                        nextLvl();
                    }
                    else if(right1 >= left2 && left1 <= left2 && right1 - left2 <= bottom1 - top2 && right1 - left2 <= bottom2 - top1){
                        //rect collides from left side of the wall
                        Player.player.x = mapRectangle[i][j].x - Player.player.width;
                        if(!Player.dashed && Player.airborne){
                            Player.onWall = true;
                        }
                    }
                    else if(left1 <= right2 && right1 >= right2 && right2 - left1 <= bottom1 - top2 && right2 - left1 <= bottom2 - top1)
                    {
                        //rect collides from right side of the wall
                        Player.player.x = mapRectangle[i][j].x + mapRectangle[i][j].width;
                        if(!Player.dashed && Player.airborne){
                            Player.onWall = true;
                        }
                    }
                    else if(bottom1 >= top2 && top1 <= top2)
                    {
                        //rect collides from top side of the wall
                        if(!Player.dashed){
                            Player.playerVelY = 0;
                        }
                        Player.player.y = mapRectangle[i][j].y - Player.player.height;
                        Player.dashNum = 1;
                        Player.airborne = false;
                        Player.onWall = false;
                        if(map[i][j] == 8){ // if player interacts with broken tile, tile breaks
                            map[i][j] = 0;
                            mapRectangle[i][j] = null;
                            Item.itemList.add(new BrokenTile(8, GamePanel.currentMap, i, j, 5, 2));
                        }
                    }
                    else if(top1 <= bottom2 && bottom1 >= bottom2)
                    {
                        //rect collides from bottom side of the wall
                        Player.playerVelY = 0;
                        Player.airborne = true;
                        Player.onWall = false;
                        Player.player.y = mapRectangle[i][j].y + mapRectangle[i][j].height;
                    }
                }
            }
        }
    }

    // Description: sends player to next level and checks if the reached the final level (if so saves completion time)
	// Parameters: nothing
	// Return: nothing (increments level and puts player in the starting position unless final level, if so then saves completion time and switches game state to 7)
    public static void nextLvl(){
        if(GamePanel.numOfMaps-1 > GamePanel.currentMap){ // if there are more levels, increment level
            GamePanel.currentMap++;
        }
        else { // if there are no more levels in the game, save score and switch to ending screen
            Score temp = new Score(GamePanel.username, GamePanel.timer);
            GamePanel.scores.put(temp, temp.getScore());
            System.out.println(GamePanel.timer + " " + GamePanel.username);
            GamePanel.keyH.jumpPressed = false;
            GamePanel.keyH.crouchPressed = false;
            GamePanel.keyH.leftPressed = false;
            GamePanel.keyH.rightPressed = false;
            GamePanel.keyH.dashPressed = false;
            GamePanel.gameState = 7;
        }
        // resets player position as the move to the next map
        Player.player.x = GamePanel.maps.get(GamePanel.currentMap).getStartX();
        Player.player.y = GamePanel.maps.get(GamePanel.currentMap).getStartY();
        Player.playerVelX = 0;
        Player.playerVelY = 0;
    }
}
