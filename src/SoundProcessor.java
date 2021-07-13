import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundProcessor {
    public SoundProcessor(){
    }

    public void processSound(Move move, Board board){
        if (board.isMute()){
            return;
        }

        // This function determines which sound should be played given the Move object
        if (board.isInCheck(board.getColorToMove())){
            playSound("check");
            return;
        }

        if (move.isSpecialMove()){
            if (move.getSpecialFlagKind().equals("ep")){
                playSound("capture");
                return;
            }
        }


        if (board.getCapturedPiece() == null){
            playSound("move");
        }
        else{
            playSound("capture");
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
