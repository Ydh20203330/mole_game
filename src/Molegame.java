import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

public class Molegame extends JFrame implements KeyListener { // JFrame, KeyListener 사용
    private Image backgroundImg; // 배경 이미지
    private Image hammerImg; // 해머 이미지
    private Image moleImg;
    private int hammerX , hammerY; // 해머 좌표
    private HashSet<Integer> pressedKeys; // 눌린 키를 다루기 위한 해시셋

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
    public Molegame() { // 생성자
        hammerX = 100; hammerY = 100;
        pressedKeys = new HashSet<>();
        setTitle("Mole Game - 20203330 YDH"); // 20203330 윤대현의 두더지 게임을 타이틀에
        setSize(800,400); // 창 크기 조절


        ImageIcon backgroundicon = new ImageIcon("Molegamebackground.png"); // 배경 이미지 imageicon으로 획득
        backgroundImg = backgroundicon.getImage(); // 배경 이미지 저장

        ImageIcon hammericon = new ImageIcon("hammer.png");
        hammerImg = hammericon.getImage();

        ImageIcon moleicon = new ImageIcon("molerat");
        moleImg = moleicon.getImage();

        setResizable(false); // 창 크기 변경 불가
        setLocationRelativeTo(null); // 창 중앙에 위치
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
    }

    public void paint(Graphics g){ // 배경은 유지, 해머와 두더지만 계속 갱신
        if (backgroundImg != null) {
            g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this); // 배경 이미지 설정
        }
        if (hammerImg != null) {
            g.drawImage(hammerImg, hammerX, hammerY, this);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) { // 키 누름처리, 해시셋에 키 코드가 저장됨
        int keyCode = e.getKeyCode();
        pressedKeys.add(keyCode);

        KeyInput();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e){ // 키 땜처리, 해시셋에 키 코드가 삭제됨
        int keyCode = e.getKeyCode();
        pressedKeys.remove(keyCode);

        KeyInput();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e){ }

    private void KeyInput(){ // 여러 키를 다루기 위한 함수 KeyInput()
        if (pressedKeys.contains(KeyEvent.VK_S)){ // S 누르면 하단으로 10만큼 이동
            if((hammerY + 10) < 350){
                hammerY += 10;
            }
        }
        if (pressedKeys.contains(KeyEvent.VK_W)){ // W 누르면 상단으로 10만큼 이동
            if((hammerY - 10) >= -50){
                hammerY -= 10;
            }
        }
        if (pressedKeys.contains(KeyEvent.VK_D)){ // D 누르면 우측으로 10만큼 이동
            if((hammerX + 10) < 750){
                hammerX += 10;
            }
        }
        if (pressedKeys.contains(KeyEvent.VK_A)){ // A 누르면 좌측으로 10만큼 이동
            if((hammerX - 10) >= -50){
                hammerX -= 10;
            }
        }
    }

    public static void main(String[] args) {
        Molegame m = new Molegame();
    }
}