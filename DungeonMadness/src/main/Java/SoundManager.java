package main.Java;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/** @author DevDoctor */
public class SoundManager {
    
    /**The audios one for the music and one for every other one*/
    Clip clip, music;
    /**Location of the sound files*/
    String sounds_loc = DefaultValues.themes_location + "\\default\\sounds";
    /**Empty File*/
    File f;
    /**NOT USED: time passed for the music*/
    Long music_time;
    //String music = ;

    public SoundManager() {
    }

    /**
     * @brief Play the theme
     * 
     * Plays the music of the dungeon and set it to loop
     */
    public void PlayMusic() {
        f = new File(sounds_loc + "\\theme.wav");
        // ONLY SET
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(f);
            music = AudioSystem.getClip();
            music.open(ais);
        } catch (Exception ex) {
        }
        FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(.2));
        // ONLY PLAY
        music.start();
        music.loop(Clip.LOOP_CONTINUOUSLY);
        // ONLY LOOP
    }
    
    /**
     * @brief stops the music
     * 
     * NOT USED
     * stops the music and save the current time. 
     */
    public void StopMusic() {
        music_time = music.getMicrosecondPosition();
        music.stop();
    }

    /**
     * @brief resume the music
     * 
     * NOT USED
     * resume the music from the saved time. 
     */
    public void ResumeMusic() {
        music.setMicrosecondPosition(music_time);
        music.start();
    }
    /**
     * @brief Plays the passed sound
     * 
     * Play the sound passed as parameter
     * @param SoundName name of the sound to play
     */
    public void PlaySound(String SoundName) {
        f = new File(sounds_loc + "\\" + SoundName);
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(f);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception ex) { }

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(.2));

        clip.start();
    }
}
