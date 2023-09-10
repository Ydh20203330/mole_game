import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

public class Molegame extends JFrame implements KeyListener { // JFrame, KeyListener 사용
    private Image backgroundImg; // 배경 이미지
    private Image hammerImg; // 해머 이미지
    private int hammerX , hammerY; // 해머 좌표
    private HashSet<Integer> pressedKeys;

    class HammerThread extends Thread {
        public void run() {
            while(true){
                try {
                    Thread.sleep(100); // 일정 간격으로 업데이트
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public Molegame() {
        hammerX = 100; hammerY = 100;
        pressedKeys = new HashSet<>();
        setTitle("Mole Game - 20203330 YDH"); // 20203330 윤대현의 두더지 게임을 타이틀에
        setSize(800,400); // 창 크기 조절


        ImageIcon backgroundicon = new ImageIcon("Molegamebackground.png"); // 배경 이미지 imageicon으로 획득
        backgroundImg = backgroundicon.getImage(); // 배경 이미지 저장

        ImageIcon hammericon = new ImageIcon("hammer.png");
        hammerImg = hammericon.getImage();

        setResizable(false); // 창 크기 변경 불가
        setLocationRelativeTo(null); // 창 중앙에 위치
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
    }

    public void paint(Graphics g){
        if (backgroundImg != null) {
            g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this); // 배경 이미지 설정
        }
        if (hammerImg != null) {
            g.drawImage(hammerImg, hammerX, hammerY, this);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        pressedKeys.add(keyCode);

        KeyInput();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e){
        int keyCode = e.getKeyCode();
        pressedKeys.remove(keyCode);

        KeyInput();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e){ }

    private void KeyInput(){
        if (pressedKeys.contains(KeyEvent.VK_S)){
            if((hammerY + 10) < 350){
                hammerY += 10;
            }
        }
        if (pressedKeys.contains(KeyEvent.VK_W)){
            if((hammerY - 10) >= -50){
                hammerY -= 10;
            }
        }
        if (pressedKeys.contains(KeyEvent.VK_D)){
            if((hammerX + 10) < 750){
                hammerX += 10;
            }
        }
        if (pressedKeys.contains(KeyEvent.VK_A)){
            if((hammerX - 10) >= -50){
                hammerX -= 10;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Molegame m = new Molegame();
        });

    }
}