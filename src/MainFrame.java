import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.ClassLoader.getSystemClassLoader;

public class MainFrame {
    public static final int width = 700;
    public static final int height = 600;
    public static final Color NIGHT_COLOR = new Color(0x055451);
    public static final Color DAY_COLOR = new Color(0x5CDCD7);
    int brickSize = 10;
    Timer timer;

    int x = 20, y = 40, mid = MainFrame.width / 2 + 20;
    ArrayList<JLabel> night = new ArrayList<>();
    ArrayList<JLabel> day = new ArrayList<>();
    Ball day_ball, night_ball;
    Image dayBall, nightBall;
    JFrame frame;
    Font font;

    public MainFrame() {
        font = new Font("Poppins", Font.BOLD, 15);
        try {
            dayBall = ImageIO.read(Objects.requireNonNull(getSystemClassLoader().getResource("res/DayBall.png")));
            nightBall = ImageIO.read(Objects.requireNonNull(getSystemClassLoader().getResource("res/NighBall.png")));
        } catch (Exception ignored) {
        }

        frame = new JFrame();
        frame.setSize(width + 60, height + 80);
        frame.setLayout(null);
        day_ball = new Ball(MainFrame.width - Ball.SIZE * 2, MainFrame.height / 2 - brickSize, dayBall);
        night_ball = new Ball(x, MainFrame.height / 2 - brickSize, nightBall);
        frame.add(day_ball);
        frame.add(night_ball);
        addingBricks();
        JLabel back = new JLabel() {
            @Override
            public void paint(Graphics g) {
                super.paintComponents(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, DAY_COLOR.darker(), width + 60, height + 80, NIGHT_COLOR.darker(),true));
                g2.fillRect(0, 0, width + 60, height + 80);
                g2.setFont(font);
                FontMetrics fm = g2.getFontMetrics(font);
                String score = "day  200  |  night  200";
                int yy = height + 50 - fm.getHeight();
                int xx = 760 / 2 - (fm.stringWidth(score))/2;
                g2.setColor(new Color(0x2F2F2F));
                g2.drawString(score, xx, yy);
            }
        };
        back.setBounds(frame.getBounds());
        frame.add(back);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        timer = new Timer(10, e -> {
            day_ball.move();
            night_ball.move();
            day_ball.checkCollision(night_ball);
            checkCollisionWithBall();
            frame.repaint();
        });
        timer.start();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

    public void addingBricks() {
        for (int i = 0; i < (MainFrame.width / 2) / brickSize; i++) {
            JLabel label = new JLabel();
            label.setBounds(x, y, brickSize, brickSize);
            label.setOpaque(true);
            label.setBackground(MainFrame.DAY_COLOR);
            day.add(label);
            frame.add(label);

            label = new JLabel();
            label.setBounds(mid, y, brickSize, brickSize);
            label.setOpaque(true);
            label.setBackground(MainFrame.NIGHT_COLOR);
            night.add(label);
            frame.add(label);
            //TODO: check the height of the game properly
            for (int a = 1; a < 56; a++) {
                y += brickSize;
                label = new JLabel();
                label.setBounds(x, y, brickSize, brickSize);
                label.setOpaque(true);
                label.setBackground(MainFrame.DAY_COLOR);
                day.add(label);
                frame.add(label);

                label = new JLabel();
                label.setBounds(mid, y, brickSize, brickSize);
                label.setOpaque(true);
                label.setBackground(MainFrame.NIGHT_COLOR);
                night.add(label);
                frame.add(label);
            }
            y = 40;
            x += brickSize;
            mid += brickSize;
        }
    }

    public void checkCollisionWithBall() {
        for (JLabel l : day) {
            if (l.getBounds().intersects(day_ball.getBounds()) && l.getBackground().equals(MainFrame.DAY_COLOR)) {
                l.setOpaque(true);
                l.setBackground(MainFrame.NIGHT_COLOR);
                day_ball.x_speed = -day_ball.x_speed;
                day_ball.y_speed = -day_ball.y_speed;
//                break;
            }
            if (l.getBounds().intersects(night_ball.getBounds()) && !l.getBackground().equals(MainFrame.DAY_COLOR)) {
                l.setOpaque(true);
                l.setBackground(MainFrame.DAY_COLOR);
                night_ball.x_speed = -night_ball.x_speed;
                night_ball.y_speed = -night_ball.y_speed;
//                break;
            }
        }

        for (JLabel l : night) {
            if (l.getBounds().intersects(night_ball.getBounds()) && l.getBackground().equals(MainFrame.NIGHT_COLOR)) {
                l.setOpaque(true);
                l.setBackground(MainFrame.DAY_COLOR);
                night_ball.x_speed = -night_ball.x_speed;
                night_ball.y_speed = -night_ball.y_speed;
//                break;
            }
            if (l.getBounds().intersects(day_ball.getBounds()) && !l.getBackground().equals(MainFrame.NIGHT_COLOR)) {
                l.setOpaque(true);
                l.setBackground(MainFrame.NIGHT_COLOR);
                day_ball.x_speed = -day_ball.x_speed;
                day_ball.y_speed = -day_ball.y_speed;
//                break;
            }
        }
    }
}
