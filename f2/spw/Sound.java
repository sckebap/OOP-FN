package f2.spw;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Sound {

    public void playSound(String soundName) {
		try {
            //String soundName = "f2/spw/1.wav";    
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
   		}
   		catch(Exception ex){
     		System.out.println("Error with playing sound.");
   		}
 	}
}