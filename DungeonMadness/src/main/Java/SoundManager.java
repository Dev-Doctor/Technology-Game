package main.Java;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * @author DevDoctor
 */
public class SoundManager {

    Clip clip, music;
    String sounds_loc = DefaultValues.themes_location + "\\default\\sounds";
    File f;
    Long music_time;
    //String music = ;

    public SoundManager() {
    }

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

    public void StopMusic() {
        music_time = music.getMicrosecondPosition();
        music.stop();
    }

    public void ResumeMusic() {
        music.setMicrosecondPosition(music_time);
        music.start();
    }

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
