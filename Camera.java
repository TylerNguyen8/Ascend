// Tyler Nguyen and Andy Lai, 2024/06/15
// Class: Camera
// Description: Class which handles all the logic with the camera movement in relation to the player in game

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class Camera {
    // camera variables
    private float cameraX; // sets camera x
    private float cameraY; // sets camera y
    private float screenX; // size of screen x
    private float screenY; // size of screen y
    private float targetX; // sets target x for camera shift (interploation)
    private float targetY; // sets target y for camera shift (interploation)
    private float zoom; // zoom settings (how zoomed in map is)
    private float lerpFactor; // Interpolation factor (for smooth camera movement)
    public int cameraDirectionX; // x direction of camera (1 == left; 2 == right)
    public int cameraDirectionY; // y direction of camera (1 == up; 2 == down)

    // Images
    private BufferedImage background1;
    private BufferedImage background2;
    private BufferedImage foreground1;
    private BufferedImage foreground2;
    private BufferedImage foreground3;
    private BufferedImage foreground4;
    private BufferedImage deathScreen;
    private BufferedImage map0;
    private BufferedImage map1;
    private BufferedImage map2;
    private BufferedImage map3;
    private BufferedImage map4;
    private BufferedImage map5;
    private BufferedImage map6;
    private BufferedImage map7;
    private BufferedImage map8;
    private BufferedImage map9;
    private BufferedImage map10;

    // Player images
    private BufferedImage idle_blink0;
    private BufferedImage idle_blink1;
    private BufferedImage idle_blink2;
    private BufferedImage look_intro0;
    private BufferedImage look_intro1;
    private BufferedImage look_intro2;
    private BufferedImage look_intro3;
    private BufferedImage look_intro4;
    private BufferedImage look_intro5;
    private BufferedImage look_intro6;
    private BufferedImage look_blink0;
    private BufferedImage look_blink1;
    private BufferedImage look_blink2;
    private BufferedImage look_back0;
    private BufferedImage look_back1;
    private BufferedImage look_back2;
    private BufferedImage look_back3;
    private BufferedImage look_back4;
    private BufferedImage run0;
    private BufferedImage run1;
    private BufferedImage run2;
    private BufferedImage run3;
    private BufferedImage run4;
    private BufferedImage run5;
    private BufferedImage run6;
    private BufferedImage run7;
    private BufferedImage run8;
    private BufferedImage run9;
    private BufferedImage dash0;
    private BufferedImage dash1;
    private BufferedImage dash2;
    private BufferedImage dash3;
    private BufferedImage jump0;
    private BufferedImage jump1;
    private BufferedImage jump2;
    private BufferedImage jump3;
    private BufferedImage fall0;
    private BufferedImage fall1;
    private BufferedImage fall2;
    private BufferedImage fall3;
    private BufferedImage wall_slide0;
    private BufferedImage wall_slide1;
    private BufferedImage wall_slide2;
    private BufferedImage wall_slide3;

    // Tile and Item Images
    private BufferedImage gem0;
    private BufferedImage gem1;
    private BufferedImage gem2;
    private BufferedImage gem3;
    private BufferedImage bouncePad;
    private BufferedImage brokenTile;

    // Variables for images
    private BufferedImage maps[];
    private BufferedImage[] runSprite;
    private int runNum = 0;
    private BufferedImage[] idleBlink;
    private int idleBlinkNum = 0;
    private BufferedImage[] idleAnimation;
    private int idleAnimationNum = 0;
    private boolean runIdleAnimation;
    private BufferedImage[] dash;
    private int dashNum = 0;
    private BufferedImage[] jump;
    private int jumpNum = 0;
    private BufferedImage[] fall;
    private int fallNum = 0;
    private BufferedImage[] wallSlide;
    private int wallSlideNum = 0;
    private BufferedImage[] gemSprite;
    private int gemNum;

    // Other
    private int deathScreenX = -2675; // position of death screen

    // Description: Intializes camera object
	// Parameters: the screenX and screenY (1280 x 720)
	// Return: creates camera object and intializes all images used in game
    public Camera(float screenX, float screenY){
        this.screenX = screenX;
        this.screenY = screenY;
        this.zoom = 1.0f;
        this.cameraX = 0;
        this.cameraY = 0;
        this.targetX = 0;
        this.targetY = 0;
        this.lerpFactor = 0.1f;
        this.cameraDirectionX = 2;
        this.cameraDirectionY = 0;

        // Loads Images
        try {
            // Background, maps, and death screen images
            background1 = ImageIO.read(new File("./Images/backgroundCave1.png"));
            background2 = ImageIO.read(new File("./Images/backgroundTree.png"));
            foreground1 = ImageIO.read(new File("./Images/foregroundCave1.png"));
            foreground2 = ImageIO.read(new File("./Images/foregroundCave2.png"));
            foreground3 = ImageIO.read(new File("./Images/foregroundCave3.png"));
            foreground4 = ImageIO.read(new File("./Images/foregroundCave4.png"));
            deathScreen = ImageIO.read(new File("./Images/deathScreen.png"));
            map0 = ImageIO.read(new File("./Images/Map0.png"));
            map1 = ImageIO.read(new File("./Images/Map1.png"));
            map2 = ImageIO.read(new File("./Images/Map2.png"));
            map3 = ImageIO.read(new File("./Images/Map3.png"));
            map4 = ImageIO.read(new File("./Images/Map4.png"));
            map5 = ImageIO.read(new File("./Images/Map5.png"));
            map6 = ImageIO.read(new File("./Images/Map6.png"));
            map7 = ImageIO.read(new File("./Images/Map7.png"));
            map8 = ImageIO.read(new File("./Images/Map8.png"));
            map9 = ImageIO.read(new File("./Images/Map9.png"));
            map10 = ImageIO.read(new File("./Images/Map10.png"));
            maps = new BufferedImage[]{map0, map1, map3, map4, map5, map2, map6, map7, map8, map9, map10};
            
            // Player images
            idle_blink0 = ImageIO.read(new File("./Images/idle_blink0.png"));
            idle_blink1 = ImageIO.read(new File("./Images/idle_blink1.png"));
            idle_blink2 = ImageIO.read(new File("./Images/idle_blink2.png"));
            idleBlink = new BufferedImage[]{idle_blink0, idle_blink0, idle_blink1, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2, idle_blink2};
            look_intro0 = ImageIO.read(new File("./Images/look_intro0.png"));
            look_intro1 = ImageIO.read(new File("./Images/look_intro1.png"));
            look_intro2 = ImageIO.read(new File("./Images/look_intro2.png"));
            look_intro3 = ImageIO.read(new File("./Images/look_intro3.png"));
            look_intro4 = ImageIO.read(new File("./Images/look_intro4.png"));
            look_intro5 = ImageIO.read(new File("./Images/look_intro5.png"));
            look_intro6 = ImageIO.read(new File("./Images/look_intro6.png"));
            look_blink0 = ImageIO.read(new File("./Images/look_blink0.png"));
            look_blink1 = ImageIO.read(new File("./Images/look_blink1.png"));
            look_blink2 = ImageIO.read(new File("./Images/look_blink2.png"));
            look_back0 = ImageIO.read(new File("./Images/look_back0.png"));
            look_back1 = ImageIO.read(new File("./Images/look_back1.png"));
            look_back2 = ImageIO.read(new File("./Images/look_back2.png"));
            look_back3 = ImageIO.read(new File("./Images/look_back3.png"));
            look_back4 = ImageIO.read(new File("./Images/look_back4.png"));
            idleAnimation = new BufferedImage[]{look_intro0, look_intro1, look_intro2, look_intro3, look_intro4, look_intro5, look_intro6, look_intro6, look_intro6, look_intro6, look_blink0, look_blink1, look_blink2, look_blink2, look_back0, look_back0, look_back1, look_back1, look_back2, look_back3, look_back4};
            run0 = ImageIO.read(new File("./Images/run0.png"));
            run1 = ImageIO.read(new File("./Images/run1.png"));
            run2 = ImageIO.read(new File("./Images/run2.png"));
            run3 = ImageIO.read(new File("./Images/run3.png"));
            run4 = ImageIO.read(new File("./Images/run4.png"));
            run5 = ImageIO.read(new File("./Images/run5.png"));
            run6 = ImageIO.read(new File("./Images/run6.png"));
            run7 = ImageIO.read(new File("./Images/run7.png"));
            run8 = ImageIO.read(new File("./Images/run8.png"));
            run9 = ImageIO.read(new File("./Images/run9.png"));
            runSprite = new BufferedImage[]{run0, run1, run2, run3, run4, run5, run6, run7, run8, run9};
            dash0 = ImageIO.read(new File("./Images/dash0.png"));
            dash1 = ImageIO.read(new File("./Images/dash1.png"));
            dash2 = ImageIO.read(new File("./Images/dash2.png"));
            dash3 = ImageIO.read(new File("./Images/dash3.png"));
            dash = new BufferedImage[]{dash0, dash1, dash2, dash3};
            jump0 = ImageIO.read(new File("./Images/jump0.png"));
            jump1 = ImageIO.read(new File("./Images/jump1.png"));
            jump2 = ImageIO.read(new File("./Images/jump2.png"));
            jump3 = ImageIO.read(new File("./Images/jump3.png"));
            jump = new BufferedImage[]{jump0, jump1, jump2, jump3};
            fall0 = ImageIO.read(new File("./Images/fall0.png"));
            fall1 = ImageIO.read(new File("./Images/fall1.png"));
            fall2 = ImageIO.read(new File("./Images/fall2.png"));
            fall3 = ImageIO.read(new File("./Images/fall3.png"));
            fall = new BufferedImage[] {fall0, fall1, fall2, fall3};
            wall_slide0 = ImageIO.read(new File("./Images/wall_slide0.png"));
            wall_slide1 = ImageIO.read(new File("./Images/wall_slide1.png"));
            wall_slide2 = ImageIO.read(new File("./Images/wall_slide2.png"));
            wall_slide3 = ImageIO.read(new File("./Images/wall_slide3.png"));
            wallSlide = new BufferedImage[]{wall_slide0, wall_slide1, wall_slide2, wall_slide3};

            // Tile and item images
            gem0 = ImageIO.read(new File("./Images/gem0.png"));
            gem1 = ImageIO.read(new File("./Images/gem1.png"));
            gem2 = ImageIO.read(new File("./Images/gem2.png"));
            gem3 = ImageIO.read(new File("./Images/gem3.png"));
            gemSprite = new BufferedImage[]{gem0, gem1, gem2, gem3};    
            bouncePad = ImageIO.read(new File("./Images/bounce.png"));
            brokenTile = ImageIO.read(new File("./Images/brokenTile.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Description: sets the targeted position of where the camera will move to
	// Parameters: the intended position of cameraX and cameraY
	// Return: nothing (sets the targeted position of the camera)
    public void setPosition(float cameraX, float cameraY){
        this.targetX = cameraX;
        this.targetY = cameraY;
    }

    // Description: increments the target position of the camera a certain amount in the x and y position
	// Parameters: the intended increment of x and y to move the camera x pixels and y pixels
	// Return: nothing (adds the increments to the targeted position of the camera)
    public void move(float dx, float dy){
        this.targetX += dx;
        this.targetY += dy;
    }

    // Description: increments camera position a certain amount in the x and y position
	// Parameters: the incremented position of cameraX and cameraY
	// Return: nothing (increments the camera position)
    public void moveRaw(float dx, float dy){
        this.cameraX += dx;
        this.cameraY += dy;
    }
    // Description: setter method for the zoom of camera
	// Parameters: the zoom factor of the camera
	// Return: nothing (increments the camera position)
    public void setZoom(float zoom){
        this.zoom = zoom;
    }

    // Description: interpolation factor
	// Parameters: the desired interploation factor
	// Return: nothing (sets interpolation factor of camera)
    public void setLerp(float lerpFactor){
        this.lerpFactor = lerpFactor;
    }

    // Description: updates the camerax and y position towards the targed x and y position with inbterpolation factor
	// Parameters: nothing (call to update camera x and y position by increments (smooth))
	// Return: nothing (increments the camera position towards the targeted camera position for the camera system to be smooth)
    public void update(){
        this.cameraX += (targetX - this.cameraX) * lerpFactor;
        this.cameraY += (targetY - this.cameraY) * lerpFactor;
    }
    // Description: applies the camera transformations onto the game
	// Parameters: the graphics 2d from paint component in gamepanel
	// Return: nothing (paints the map transformed by the camera system onto the screen)
    public void applyTransformation(Graphics2D g2) {
        // Save current transform
        AffineTransform savedTransform = g2.getTransform();

        // Drawing not affected by orthographic porjection (bottom layer)
        if(GamePanel.currentMap == 10 || GamePanel.currentMap == 0 || GamePanel.currentMap == 7 || GamePanel.currentMap == 8){
            g2.drawImage(background2, 0, 0, null);
        }
        else{
            g2.drawImage(background1, 0, 0, null);
        }

        // Apply orthographic projection
        g2.translate(screenX/2, screenY/2); // center camera
        g2.scale(zoom, zoom); // adjust images by zoom factor
        g2.translate(-cameraX, -cameraY); // translate camera by new cameraX and Y

        // Drawing
        // middle ground drawing (between foreground and background)
        if(GamePanel.currentMap == 10){
            g2.drawImage(foreground1, 0, 0, null);
        }
        else if(GamePanel.currentMap == 0){
            g2.drawImage(foreground2, 0, 0, null);
        }
        else if(GamePanel.currentMap == 7){
            g2.drawImage(foreground3, 0, 0, null);
        }
        else if(GamePanel.currentMap == 8){
            g2.drawImage(foreground4, 0, 0, null);
        }

        // Sprites/animation reset
        if(runNum == runSprite.length){
            runNum = 0;
        }
        if(idleAnimationNum == idleAnimation.length){
            idleAnimationNum = 0;
            runIdleAnimation = false;
        }
        if(dashNum == dash.length){
            dashNum = 0;
        }
        if(jumpNum == jump.length){
            jumpNum = 0;
        }
        if(fallNum == fall.length){
            fallNum = 0;
        }
        if(wallSlideNum == wallSlide.length){
            wallSlideNum = 0;
        }
        if(idleBlinkNum == idleBlink.length){
            idleBlinkNum = 0;
        }
        if(gemNum == gemSprite.length){
            gemNum = 0;
        }
        
        // draws tiles/items in the desired location on the map
        for(int row = 0; row < GamePanel.maps.get(GamePanel.currentMap).getMap().length; row++){
            for(int col = 0; col < GamePanel.maps.get(GamePanel.currentMap).getMap()[0].length; col++){
                if(GamePanel.maps.get(GamePanel.currentMap).getMap()[row][col] == 0){}
                else{
                    if(GamePanel.maps.get(GamePanel.currentMap).getMap()[row][col] == 1){
                        g2.setColor(new Color(0, 0, 0, 0));
                        g2.fill(GamePanel.maps.get(GamePanel.currentMap).getMapRectangle()[row][col]);
                    }
                    else if(GamePanel.maps.get(GamePanel.currentMap).getMap()[row][col] == 2 || GamePanel.maps.get(GamePanel.currentMap).getMap()[row][col] == 3 || GamePanel.maps.get(GamePanel.currentMap).getMap()[row][col] == 4 || GamePanel.maps.get(GamePanel.currentMap).getMap()[row][col] == 5){
                        g2.setColor(new Color(0, 0, 0, 0));
                        g2.fill(GamePanel.maps.get(GamePanel.currentMap).getMapRectangle()[row][col]);
                    }
                    else if(GamePanel.maps.get(GamePanel.currentMap).getMap()[row][col] == 6){
                        g2.setColor(new Color(0, 0, 0, 0));
                        g2.drawImage(bouncePad, GamePanel.maps.get(GamePanel.currentMap).getMapRectangle()[row][col].x - 7, GamePanel.maps.get(GamePanel.currentMap).getMapRectangle()[row][col].y, null);
                    }
                    else if(GamePanel.maps.get(GamePanel.currentMap).getMap()[row][col] == 7){
                        g2.setColor(new Color(0, 0, 0, 0));
                        g2.drawImage(gemSprite[gemNum], GamePanel.maps.get(GamePanel.currentMap).getMapRectangle()[row][col].x, GamePanel.maps.get(GamePanel.currentMap).getMapRectangle()[row][col].y, null);
                    }
                    else if(GamePanel.maps.get(GamePanel.currentMap).getMap()[row][col] == 8){
                        g2.setColor(new Color(0, 0, 0, 0));
                        g2.drawImage(brokenTile, GamePanel.maps.get(GamePanel.currentMap).getMapRectangle()[row][col].x, GamePanel.maps.get(GamePanel.currentMap).getMapRectangle()[row][col].y, null);
                    }
                    else if(GamePanel.maps.get(GamePanel.currentMap).getMap()[row][col] == 9){
                        g2.setColor(new Color(0, 0, 0, 0));
                        g2.fill(GamePanel.maps.get(GamePanel.currentMap).getMapRectangle()[row][col]);
                    }
                    else if(GamePanel.maps.get(GamePanel.currentMap).getMap()[row][col] == 10){
                        g2.setColor(new Color(0, 0, 0, 0));
                        g2.fill(GamePanel.maps.get(GamePanel.currentMap).getMapRectangle()[row][col]);
                    }
                }
            }
        }

        // Setting which Player Sprites on what action
        // player dashes horizontally
        if(Player.dashed && Player.directionY == 0){
            if(cameraDirectionX == 1){ // if player is facing left
                g2.drawImage(dash[dashNum], Player.player.x + Player.player.width, Player.player.y, -Player.player.width, Player.player.height, null);
            }
            else if(cameraDirectionX == 2){ // if player is facing right
                g2.drawImage(dash[dashNum], Player.player.x, Player.player.y, Player.player.width, Player.player.height, null);
            }
        }
        // player on wall
        else if(Player.onWall){
            if(Player.playerVelX < 0){ // if player is on the left wall
                g2.drawImage(wallSlide[wallSlideNum], Player.player.x, Player.player.y-15, Player.player.width-10, Player.player.height+15, null);
            }
            else if(Player.playerVelX > 0){ // if player is on the right wall
                g2.drawImage(wallSlide[wallSlideNum], Player.player.x + Player.player.width, Player.player.y-15, -(Player.player.width-10), Player.player.height+15, null);
            }
        }
        // player jumps
        else if(Player.playerVelY > 0){
            if(Player.playerVelX < 0){ // if player is facing left
                g2.drawImage(jump[jumpNum], Player.player.x + Player.player.width-5, Player.player.y-10, -(Player.player.width-5), Player.player.height+10, null);
            }
            else if(Player.playerVelX > 0){ // if player is facing right
                g2.drawImage(jump[jumpNum], Player.player.x-5, Player.player.y-10, Player.player.width-5, Player.player.height+10, null);
            }
            else{
                if(cameraDirectionX == 1){ // if player is facing left (in case bugged)
                    g2.drawImage(jump[jumpNum], Player.player.x + Player.player.width-5, Player.player.y-10, -(Player.player.width-5), Player.player.height+10, null);
                }
                else if(cameraDirectionX == 2){ // if player is facing right (in case bugged)
                    g2.drawImage(jump[jumpNum], Player.player.x-5, Player.player.y-10, Player.player.width-5, Player.player.height+10, null);
                }
            }
        }
        // player falling
        else if(Player.playerVelY < -1 || Player.airborne){
            if(Player.playerVelX < 0){ // if player is facing left
                g2.drawImage(fall[fallNum], Player.player.x + Player.player.width, Player.player.y-15, -(Player.player.width+10), Player.player.height+15, null);
            }
            else if(Player.playerVelX > 0){ // if player is facing right
                g2.drawImage(fall[fallNum], Player.player.x-10, Player.player.y-15, Player.player.width+10, Player.player.height+15, null);
            }
            else{
                if(cameraDirectionX == 1){ // if player is facing left (in case bugged)
                    g2.drawImage(fall[fallNum], Player.player.x + Player.player.width, Player.player.y-15, -(Player.player.width+10), Player.player.height+15, null);
                }
                else if(cameraDirectionX == 2){ // if player is facing right (in case bugged)
                    g2.drawImage(fall[fallNum], Player.player.x-10, Player.player.y-15, Player.player.width+10, Player.player.height+15, null);
                }
            }
        }
        // player not moving
        else if(Player.playerVelX <= 1 && Player.playerVelX >= -1){
            if(GamePanel.frameCount % 420 == 0){ // runs idle animation if player is nomt moving and condition is met
                runIdleAnimation = true;
            }
            if(cameraDirectionX == 1){ // if player is facing left
                if(runIdleAnimation){ // if idle animation is playing
                    g2.drawImage(idleAnimation[idleAnimationNum], Player.player.x + Player.player.width, Player.player.y, -Player.player.width, Player.player.height, null);
                }
                else{ // default animation
                    g2.drawImage(idleBlink[idleBlinkNum],  Player.player.x + Player.player.width, Player.player.y, -Player.player.width, Player.player.height, null);
                }
            }
            else if(cameraDirectionX == 2){ // if player is facing right
                if(runIdleAnimation){ // if idle animation is playing
                    g2.drawImage(idleAnimation[idleAnimationNum], Player.player.x, Player.player.y, Player.player.width, Player.player.height, null);
                }
                else{ // default animation
                    g2.drawImage(idleBlink[idleBlinkNum], Player.player.x, Player.player.y, Player.player.width, Player.player.height, null);
                }
            }
        }
        // Player moving left
        else if(Player.playerVelX < 0){
            g2.drawImage(runSprite[runNum], Player.player.x + Player.player.width+10, Player.player.y-10, -(Player.player.width+10), Player.player.height+10, null);
        }
        // Player moving right
        else if(Player.playerVelX > 0){
            g2.drawImage(runSprite[runNum], Player.player.x-10, Player.player.y-10, Player.player.width+10, Player.player.height+10, null);
        }

        // Player increment sprite every 5 frames
        if(GamePanel.frameCount%5 == 0){
            runNum++;
            dashNum++;
            jumpNum++;
            fallNum++;
            wallSlideNum++;
            idleBlinkNum++;
            if(GamePanel.frameCount%10 == 0){
                gemNum++;
            }
            if(runIdleAnimation){
                idleAnimationNum++;
            }
        }

        if(GamePanel.currentMap != 8){
            g2.drawImage(maps[GamePanel.currentMap], 0, 0, null);
        }
        else{
            g2.drawImage(maps[GamePanel.currentMap], 5, 0, null);
        }

        // Restore transform
        g2.setTransform(savedTransform);

        // Drawing not affected by orthographic porjection (top layer)
        // Player death animation screen
        if(Player.death){
            if(deathScreenX < GamePanel.screenWidth){
                g2.drawImage(deathScreen, deathScreenX, 0, null);
                deathScreenX += 90;
            }
            else{ // resets death screen
                Player.death = false;
                deathScreenX = -2675;
            }
        }
        // Timer
        g2.setColor(Color.white);
        g2.setFont(GamePanel.customFont50);
        g2.drawString(GamePanel.time.toString(), 10, 40);
    }

    // Description: getter method for camera x position
	// Parameters: nothing
	// Return: the x axis camera position
    public float getX(){
        return cameraX;
    }

    // Description: getter method for camera y position
	// Parameters: nothing
	// Return: the y axis camera position
    public float getY(){
        return cameraY;
    }

    // Description: getter method for camera zoom
	// Parameters: nothing
	// Return: the zoom applied on camera
    public float getZoom(){
        return zoom;
    }
}