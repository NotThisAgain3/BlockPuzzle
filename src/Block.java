import java.awt.*;

public abstract class Block {
    public enum DIRECTION {
        TOP,
        LEFT,
        RIGHT,
        BOTTOM,
        NONE
    }
    public Color color;
    int x;
    int y;
    public abstract void drawSelf(Graphics2D g, Dimension size);
}
