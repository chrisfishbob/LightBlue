import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundProcessor {
    public SoundProcessor(){
    }

    public void processSound(Move move, Board board){
        if (LightBlueMain.isMute){
            return;
        }

        // This function determines which sound should be played given the Move object
        if (!move.isSpecialMove()){
            if (board.getBoardArray()[move.getTargetSquare()] == null){
                playSound("move");
            }
            else{
                playSound("capture");
            }
        }
        else{
            if (move.getSpecialFlagKind().equals("ep")){
                playSound("capture");
            }

            else{
                playSound("move");
            }
        }
    }

    public void playSound(String soundName){
        // This function plays the actual sound file according to the results of processSound
        try{
            File file = new File("sound/" + soundName + ".wav");
            AudioInputStream move = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(move);
            clip.start();
        }

        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
