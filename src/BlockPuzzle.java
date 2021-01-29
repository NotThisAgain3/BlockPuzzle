import javax.swing.*;


public class BlockPuzzle {

    public static void main(String[] args) {
        JFrame frame = new JFrame("BlockPuzzle [Press Space to Restart Level]");

        BlockPuzzlePanel blockPuzzlePanel = new BlockPuzzlePanel();
        frame.setContentPane(blockPuzzlePanel);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
