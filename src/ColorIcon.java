import javax.swing.*;
import java.awt.*;

//用于给windows风格设置按钮背景颜色的类
public class ColorIcon implements Icon {
    private final Color color;
    private final int width;
    private final int height;

    public ColorIcon(Color color, int width, int height) {
        this.color = color;
        this.width = width;
        this.height = height;
    }

    public int getIconWidth() {
        return width;
    }

    public int getIconHeight() {
        return height;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}