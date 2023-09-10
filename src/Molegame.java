import javax.swing.*;
import java.awt.*;

public class Molegame extends JFrame{
    private Image backgroundImg;
    class KeyThread extends Thread {
        char key;
        int x, y;

        public KeyThread(char key) {
            this.key = key;
        }

        public synchronized void setXY(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public void run() {
            if (key == 'w'){
                setXY(x, y - 1);
            }
            else if (key == 'a'){
                setXY(x - 1, y);
            }
            else if (key == 'd'){
                setXY(x + 1, y);
            }
            else if (key == 's'){
                setXY(x, y + 1);
            }

        }
    }
    public Molegame() {
        setTitle("Mole Game - 20203330 YDH"); // 20203330 윤대현의 두더지 게임을 타이틀에
        setSize(800,400); // 창 크기 조절
        setResizable(false); // 창 크기 변경 불가
        setLocationRelativeTo(null); // 창 중앙에 위치

        ImageIcon backgroundicon = new ImageIcon("Molegamebackground.png");
        backgroundImg = backgroundicon.getImage();

        setVisible(true);
    }

    public void paint(Graphics g){
        super.paint(g);
        if (backgroundImg != null) {
            g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
        }
    }
    public static void main(String[] args) {
        Molegame m = new Molegame();

    }
}