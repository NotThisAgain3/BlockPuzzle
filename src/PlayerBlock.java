import java.awt.*;

public class PlayerBlock extends Block {
    public PlayerBlock(){
        color = Color.RED;
    }
    @Override
    public void drawSelf(Graphics2D g, Dimension size) {
        g.setColor(color);
        g.fillRect(x * size.width, y * size.height, size.width, size.height);
    }

}
