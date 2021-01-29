import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
//Todo List


//Move Blocks by counting all stacked blocks and counting those as one block
//If other block exert no force on player
class BlockPuzzlePanel extends JPanel {

    int[][] level = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,1,0,0,0,0,1,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
            {1,0,0,0,0,0,2,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    Dimension levelSize = new Dimension(16, 16);
    Dimension blockSize = new Dimension(600 / levelSize.width, 600 / levelSize.height);
    private final ArrayList<Block> blocks = new ArrayList<>();
    private PlayerBlock playerBlock;
    private static final int DELAY_MS = 80;

    public BlockPuzzlePanel() {
        Timer timer = new Timer(DELAY_MS, e -> repaint());
        timer.start();

        loadLevel();

        addKeyListener(new BlockPuzzlePanelKeyAdapter());
        setFocusable(true);
        requestFocus();
    }

    private void loadLevel() {
        blocks.clear();
        playerBlock = new PlayerBlock();
        blocks.add(playerBlock);

        for(int x = 0; x < levelSize.width; x++){
            for(int y = 0; y < levelSize.height; y++){
                switch (level[y][x]) {
                    case 1 -> {
                        Block solidBlock = new SolidBlock();
                        solidBlock.x = x;
                        solidBlock.y = y;

                        blocks.add(solidBlock);
                    }

                    case 2 -> {
                        playerBlock.x = x;
                        playerBlock.y = y;
                    }

                    case 0 -> blocks.add(null);
                }
            }
        }
    }

    private void findAndMoveNeighbour(Block.DIRECTION direction) {
        for (Block n : blocks){
            if(n != null && !(n instanceof PlayerBlock)){
                if(n.x == playerBlock.x && n.y == playerBlock.y + 1 && direction == Block.DIRECTION.BOTTOM)
                    n.y++;
                if(n.x == playerBlock.x && n.y == playerBlock.y - 1 && direction == Block.DIRECTION.TOP)
                    n.y--;
                if(n.x == playerBlock.x + 1 && n.y == playerBlock.y && direction == Block.DIRECTION.RIGHT)
                    n.x++;
                if(n.x == playerBlock.x - 1 && n.y == playerBlock.y && direction == Block.DIRECTION.LEFT)
                    n.x--;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        for(Block block : blocks){
            if(block != null)
                block.drawSelf((Graphics2D) g, blockSize);
        }
    }

    class BlockPuzzlePanelKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE)
                loadLevel();

            Vector2D movement = new Vector2D(0, 0);

            Block.DIRECTION direction = Block.DIRECTION.NONE;

            switch(e.getKeyCode()) {
                case KeyEvent.VK_UP -> {
                    direction = Block.DIRECTION.TOP;
                    movement.y--;
                }
                case KeyEvent.VK_LEFT -> {
                    direction = Block.DIRECTION.LEFT;
                    movement.x--;
                }
                case KeyEvent.VK_DOWN -> {
                    direction = Block.DIRECTION.BOTTOM;
                    movement.y++;
                }
                case KeyEvent.VK_RIGHT -> {
                    direction = Block.DIRECTION.RIGHT;
                    movement.x++;
                }
            }

            findAndMoveNeighbour(direction);

            playerBlock.x += movement.x;
            playerBlock.y += movement.y;
        }
    }
}
