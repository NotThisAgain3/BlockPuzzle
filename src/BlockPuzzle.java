import javax.swing.*;
import java.awt.*;


public class BlockPuzzle {

    public static void main(String[] args) {

        JFrame frame = new JFrame("BlockPuzzle [Press Space to Restart Level]");

        BlockPuzzleCanvas blockPuzzleCanvas = new BlockPuzzleCanvas();
        frame.add(blockPuzzleCanvas);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
