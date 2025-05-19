// Tyler Nguyen and Andy Lai, 2024/06/15
// Class: MouseHandler
// Description: Class which is responsible for making all of the UI work with the user's mouse

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.MouseInfo;

public class MouseHandler implements MouseListener {
    // Get mouse position
    double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
    double mouseY = MouseInfo.getPointerInfo().getLocation().getX();

    // Description: mousePressed method to make all of the UI mouse interactions work
	// Parameters: takes in MouseEvent e
	// Return: nothing, but ensures all UI mouse interactions work
    public void mousePressed(MouseEvent e) {
        // Main screen
        if(GamePanel.gameState == 1) {
            // Play button
            if((e.getX() >= 592 && e.getX() <= 690) && (e.getY() >= 449 && e.getY() <= 477)) {
                Audio.playAudio("click");
                Driver.popup.setVisible(true);
            }
            // Options button
            else if((e.getX() >= 564 && e.getX() <= 717) && (e.getY() >= 505 && e.getY() <= 533)) {
                Audio.playAudio("click");
                GamePanel.previousGameState = GamePanel.gameState;
                GamePanel.gameState = 4;
            }
            // Leaderboard button
            else if((e.getX() >= 510 && e.getX() <= 774) && (e.getY() >= 563 && e.getY() <= 590)) {
                Audio.playAudio("click");
                GamePanel.previousGameState = GamePanel.gameState;
                GamePanel.gameState = 5;
            }
            // About button
            else if((e.getX() >= 579 && e.getX() <= 699) && (e.getY() >= 617 && e.getY() <= 645)) {
                Audio.playAudio("click");
                GamePanel.previousGameState = GamePanel.gameState;
                GamePanel.gameState = 6;
            }
        }
        // Pause screen
        else if(GamePanel.gameState == 3){
            // Resume button
            if(e.getX() >= 498 && e.getX() <= 781 && e.getY() >= 210 && e.getY() <= 264){
                GamePanel.previousGameState = GamePanel.gameState;
                GamePanel.gameState = 2;
            }
            // Retry button
            else if(e.getX() >= 526 && e.getX() <= 753 && e.getY() >= 303 && e.getY() <= 354){
                GamePanel.gameState = 2;
                Player.reset();
            }
            // Options button
            else if(e.getX() >= 475 && e.getX() <= 801 && e.getY() >= 400 && e.getY() <= 454){
                GamePanel.previousGameState = GamePanel.gameState;
                GamePanel.gameState = 4;
            }
            // Restart button
            else if(e.getX() >= 428 && e.getX() <= 854 && e.getY() >= 498 && e.getY() <= 554){ 
                GamePanel.previousGameState = GamePanel.gameState;
                GamePanel.gameState = 2;
                GamePanel.timer = 0;
				GamePanel.currentMap = 0;
                Player.reset();
            }
            // Exit button
            else if(e.getX() >= 428 && e.getX() <= 850 && e.getY() >= 596 && e.getY() <= 652){
                GamePanel.previousGameState = GamePanel.gameState;
                GamePanel.gameState = 1;
                GamePanel.timer = 0;
				GamePanel.currentMap = 0;
                Player.reset();
            }
        }
        // Settings screen
        else if(GamePanel.gameState == 4) {
            // Variables to see if user is changing keybind
            GamePanel.changeLeft = false;
            GamePanel.changeDown = false;
            GamePanel.changeRight = false;
            GamePanel.changeUp = false;
            GamePanel.changeDash = false;
            GamePanel.changeJump = false;
            // Change left movement key
            if((e.getX() >= 421 && e.getX() <= 480) && (e.getY() >= 383 && e.getY() <= 441)) {
                Audio.playAudio("click");
                GamePanel.changeLeft = true;
            }
            // Change down movement key
            else if((e.getX() >= 494 && e.getX() <= 552) && (e.getY() >= 383 && e.getY() <= 441)) {
                Audio.playAudio("click");
                GamePanel.changeDown = true;
            }
            // Change right movement key
            else if((e.getX() >= 567 && e.getX() <= 627) && (e.getY() >= 383 && e.getY() <= 441)) {
                Audio.playAudio("click");
                GamePanel.changeRight = true;
            }
            // Change up movement key
            else if((e.getX() >= 494 && e.getX() <= 553) && (e.getY() >= 309 && e.getY() <= 369)) {
                Audio.playAudio("click");
                GamePanel.changeUp = true;
            }
            // Change dash movement key
            else if((e.getX() >= 698 && e.getX() <= 757) && (e.getY() >= 383 && e.getY() <= 441)) {
                Audio.playAudio("click");
                GamePanel.changeDash = true;
            }
            // Change jump movement key
            else if((e.getX() >= 797 && e.getX() <= 855) && (e.getY() >= 383 && e.getY() <= 441)) {
                Audio.playAudio("click");
                GamePanel.changeJump = true;
            }
            // Toggle music button
            if((e.getX() >= 541 && e.getX() <= 610) && (e.getY() >= 547 && e.getY() <= 611)) {
                Audio.playAudio("click");
                Audio.enableMusic = !Audio.enableMusic;
                if(!Audio.enableMusic) {
                    Audio.playSoundtrack("stop");
                }
                else {
                    Audio.playSoundtrack("playsoundtrack");
                }
            }
            // Toggle SFX button
            if((e.getX() >= 541 && e.getX() <= 608) && (e.getY() >= 619 && e.getY() <= 683)) {
                Audio.playAudio("click");
                Audio.enableSFX = !Audio.enableSFX;
            }
        }
    }

    // Miscellaneous unused methods
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
