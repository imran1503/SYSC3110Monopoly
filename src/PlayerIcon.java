import javax.swing.*;
import java.awt.*;
// A simple Icon implementation that draws ovals
public class PlayerIcon implements Icon {
    private int width, height;
    private Color color;

    public PlayerIcon(int w, int h, Color color) {
        width = w;
        height = h;
        this.color = color;
    }

    public void paintIcon(Component c, Graphics g, int x, int y, Color color) {
        g.drawOval(x, y, width-1, height-1);
        g.setColor(color);
        g.fillRect(x,y, width,height);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.drawOval(x, y, width-1, height-1);
        g.setColor(color);
        g.fillRect(x,y, width,height);
    }

    public int getIconWidth() { return width; }
    public int getIconHeight() { return height; }



}
