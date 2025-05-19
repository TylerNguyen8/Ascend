// Tyler Nguyen and Andy Lai, 2024/06/15
// Class: Audio
// Description: Class which handles all the logic playing and stopping music or sound effects

import java.io.*;
import javax.sound.sampled.*;

public class Audio {
    // Variables to start/stop music
    public static boolean enableSFX = true;
    public static boolean enableMusic = true;
    static Clip clipSoundtrack;

    // Description: plays sound effects
	// Parameters: takes in a String to select which audio to play
	// Return: nothing, but plays the audio
    public static void playAudio(String audio) {
        try {
            // Load in appropriate music file
            File f = new File("./Audio/Empty.wav");
            if(enableSFX) {
                if(audio.equals("jump")) {
                    f = new File("./Audio/jump.wav");
                }
                else if(audio.equals("jump_wall_left")){
                    f = new File("./Audio/jump_wall_left.wav");
                }
                else if(audio.equals("jump_wall_right")){
                    f = new File("./Audio/jump_wall_right.wav");
                }
                else if(audio.equals("click")) {
                    f = new File("./Audio/click.wav");
                }
                else if(audio.equals("death")) {
                    f = new File("./Audio/death.wav");
                }
                else if(audio.equals("deathReverse")) {
                    f = new File("./Audio/deathReverse.wav");
                }
                else if(audio.equals("powerup")) {
                    f = new File("./Audio/powerUp.wav");
                }
                else if(audio.equals("spring")) {
                    f = new File("./Audio/spring.wav");
                }
                else if(audio.equals("dash_left")){
                    f = new File("./Audio/dash_red_left.wav");
                }
                else if(audio.equals("dash_right")){
                    f = new File("./Audio/dash_red_right.wav");
                }
                else if(audio.equals("foot_dirt_01")){
                    f = new File("./Audio/foot_00_dirt_01.wav");
                }
                else if(audio.equals("foot_dirt_02")){
                    f = new File("./Audio/foot_00_dirt_02.wav");
                }
                else if(audio.equals("foot_dirt_03")){
                    f = new File("./Audio/foot_00_dirt_03.wav");
                }
                else if(audio.equals("foot_dirt_04")){
                    f = new File("./Audio/foot_00_dirt_04.wav");
                }
                else if(audio.equals("foot_dirt_05")){
                    f = new File("./Audio/foot_00_dirt_05.wav");
                }
                else if(audio.equals("foot_dirt_06")){
                    f = new File("./Audio/foot_00_dirt_06.wav");
                }
                else if(audio.equals("foot_dirt_07")){
                    f = new File("./Audio/foot_00_dirt_07.wav");
                }
                // Play the sound effects
                AudioInputStream audioIn1 = AudioSystem.getAudioInputStream(f.toURI().toURL());  
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn1);
                clip.start();
            }
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Description: plays music soundtrack
	// Parameters: takes in a String to control the soundtrack
	// Return: nothing, but plays the audio
    public static void playSoundtrack(String audio) {
        try{
            // Stop existing processes
            if (clipSoundtrack != null && clipSoundtrack.isOpen()) {
                clipSoundtrack.stop();
                clipSoundtrack.close();
            }
            clipSoundtrack = AudioSystem.getClip();

            // Load in apppropriate soundtrack
            File f = new File("./Audio/Empty.wav");
            // Play audio
            if(audio.equals("playsoundtrack")) {
                f = new File("./Audio/Soundtrack.wav");
                // Play the sound effects
                AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(f.toURI().toURL());  
                clipSoundtrack.open(audioIn2);
                clipSoundtrack.loop(Clip.LOOP_CONTINUOUSLY);
                clipSoundtrack.start();
            }   
            // Stop audio
            else if(!enableMusic) {
                clipSoundtrack.stop();
            }
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Description: plays sound effect for walking
	// Parameters: takes in a String which determines what type of surface player is walking on
	// Return: nothing, but plays the audio
    public static void walkAudio(String groundType){
        // Check if walking on ground
		if(!Player.airborne && !Player.onWall && !Player.dashed && GamePanel.frameCount % 25 == 0){
            // Play appropriate audio
			int rand = (int)(Math.random()*(7-1+1)+1);
			if(rand == 1){
				Audio.playAudio("foot_" + groundType + "_01");
			}
			else if(rand == 2){
				Audio.playAudio("foot_" + groundType + "_02");
			}
			else if(rand == 3){
				Audio.playAudio("foot_" + groundType + "_03");
			}
			else if(rand == 4){
				Audio.playAudio("foot_" + groundType + "_04");
			}
			else if(rand == 5){
				Audio.playAudio("foot_" + groundType + "_05");
			}
			else if(rand == 6){
				Audio.playAudio("foot_" + groundType + "_06");
			}
			else if(rand == 7){
				Audio.playAudio("foot_" + groundType + "_07");
			}
		}
	}
}
