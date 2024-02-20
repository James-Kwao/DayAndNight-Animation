import javax.swing.*;
import java.awt.*;

public class Ball extends JLabel {
    static final int SIZE = 20;
    static int C = 0;
    int y_speed;
    int x_speed = 12;
    int x_pos, y_pos;

    public Ball(int x, int y, Image img) {
        x_pos = x;
        y_pos = y;
        y_speed = 8 + (int) (Math.random() * 3);
        x_speed = 12 - (int) (Math.random() * 2);
        img = img.getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(img));
        this.setBounds(x, y, SIZE, SIZE);
        C++;
        if (C > 1) {
            x_speed = -x_speed - 4;
            y_speed = -y_speed + 4;
        }
    }

    public void move() {
        if (getBounds().x + SIZE >= MainFrame.width + 20 || getBounds().x <= 20) {
//            if (x_pos <= 20) x_pos = 21;
//            if (x_pos + SIZE  >= MainFrame.width) x_pos = MainFrame.width-2;
            x_speed = -x_speed;
        }
        if (getBounds().y + SIZE * 2 >= MainFrame.height + 20 || getBounds().y <= 40) {
//            if (y_pos <= 50) y_pos = 51;
            y_speed = -y_speed;
        }
        x_pos += x_speed;
        y_pos += y_speed;
        setBounds(x_pos, y_pos, SIZE, SIZE);
    }

    public void checkCollision(Ball b) {
        if (this.getBounds().intersects(b.getBounds())) {
            int tmpY = y_speed;
            int tmpX = x_speed;
            x_speed = b.y_speed;
            y_speed = b.x_speed;
            b.x_speed = -tmpX;
            b.y_speed = -tmpY;
        }
    }
}
