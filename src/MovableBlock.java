import java.awt.*;

public class MovableBlock extends Block {
    public MovableBlock(){
        color = Color.BLUE;
    }
    @Override
    public void drawSelf(Graphics2D g, Dimension size) {
        g.setColor(color);
        g.fillRect(x * size.width, y * size.height, size.width, size.height);
    }

}
