import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class BlockPuzzleCanvas extends Canvas {

    int[][] level = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,1,0,0,0,0,1,0,0,0,1},
            {1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,1,0,0,0,0,1,0,0,0,1},
            {1,0,0,1,1,1,1,0,1,0,0,0,0,0,0,1},
            {1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,0,1,1,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,1,1,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
            {1,0,0,0,0,0,2,0,1,0,0,0,0,0,0,1},
            {1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    Dimension levelSize = new Dimension(16, 16);
    Dimension blockSize = new Dimension(600 / levelSize.width, 600 / levelSize.height);
    private final ArrayList<Block> blocks = new ArrayList<>();
    private PlayerBlock playerBlock;
    private static final int DELAY_MS = 80;

    public BlockPuzzleCanvas() {
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
                        Block movableBlock = new MovableBlock();
                        movableBlock.x = x;
                        movableBlock.y = y;

                        blocks.add(movableBlock);
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
    private boolean findAndMoveNeighbours(Block.DIRECTION direction, Block block) {
        for (Block n : blocks){
            if(n != null && !(n instanceof PlayerBlock)){
                if(n.x == block.x && n.y == block.y + 1 && direction == Block.DIRECTION.BOTTOM) {
                    findAndMoveNeighbours(direction, n);
                    n.y++;
                }
                if(n.x == block.x && n.y == block.y - 1 && direction == Block.DIRECTION.TOP) {
                    findAndMoveNeighbours(direction, n);
                    n.y--;
                }
                if(n.x == block.x + 1 && n.y == block.y && direction == Block.DIRECTION.RIGHT) {
                    findAndMoveNeighbours(direction, n);
                    n.x++;
                }
                if(n.x == block.x - 1 && n.y == block.y && direction == Block.DIRECTION.LEFT) {
                    findAndMoveNeighbours(direction, n);
                    n.x--;
                }
            }
        }
        return true;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
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

            Vector2D playerMovement = new Vector2D(0, 0);

            Block.DIRECTION direction = Block.DIRECTION.NONE;

            switch(e.getKeyCode()) {
                case KeyEvent.VK_UP -> {
                    direction = Block.DIRECTION.TOP;
                    playerMovement.y--;
                }
                case KeyEvent.VK_LEFT -> {
                    direction = Block.DIRECTION.LEFT;
                    playerMovement.x--;
                }
                case KeyEvent.VK_DOWN -> {
                    direction = Block.DIRECTION.BOTTOM;
                    playerMovement.y++;
                }
                case KeyEvent.VK_RIGHT -> {
                    direction = Block.DIRECTION.RIGHT;
                    playerMovement.x++;
                }
            }

            if(findAndMoveNeighbours(direction, playerBlock)) {
                playerBlock.x += playerMovement.x;
                playerBlock.y += playerMovement.y;
            }
        }
    }
}
