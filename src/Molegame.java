import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

public class Molegame extends JFrame implements KeyListener { // JFrame, KeyListener 사용
    private Image backgroundImg; // 배경 이미지
    private Image hammerImg; // 해머 이미지
    private Image moleImg;
    private int hammerX , hammerY; // 해머 좌표
    private int moleX, moleY; // 두더지 좌표
    private Timer moleTimer; // 두더지를 일정 시간마다 움직이게 하기 위한 Timer
    private HashSet<Integer> pressedKeys; // 눌린 키를 다루기 위한 해시셋
    private int molegame_score; // 점수

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
        molegame_score = 0;
        hammerX = 100; hammerY = 100;
        pressedKeys = new HashSet<>();
        setTitle("Mole Game - 20203330 YDH"); // 20203330 윤대현의 두더지 게임을 타이틀에
        setSize(800,400); // 창 크기 조절

        moleX = -100; moleY = -100;

        moleTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moleX = (int)(Math.random() * (getWidth() - 50));
                moleY = (int)(Math.random() * (getHeight() - 50));
                repaint();
            }
        });
        moleTimer.start();

        ImageIcon backgroundicon = new ImageIcon("Molegamebackground.png"); // 배경 이미지 imageicon으로 획득
        backgroundImg = backgroundicon.getImage(); // 배경 이미지 저장

        ImageIcon hammericon = new ImageIcon("hammer.png");
        hammerImg = hammericon.getImage();

        ImageIcon moleicon = new ImageIcon("molerat.png");
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

    private void resetMole() {
        moleX = (int)(Math.random() * (getWidth() - 50));
        moleY = (int)(Math.random() * (getHeight() - 50));
        moleTimer.restart();
    }

    public void paint(Graphics g){ // 배경은 유지, 해머와 두더지만 계속 갱신
        if (backgroundImg != null) {
            g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this); // 배경 이미지 설정
        }
        if (moleImg != null){
            g.drawImage(moleImg, moleX, moleY, this);
        }
        if (hammerImg != null) {
            g.drawImage(hammerImg, hammerX, hammerY, this);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) { // 키 누름처리, 해시셋에 키 코드가 저장됨
        int keyCode = e.getKeyCode();
        pressedKeys.add(keyCode);

        if (keyCode == KeyEvent.VK_SPACE){
            if((hammerX <= moleX + 50) && (hammerX >= moleX - 50)
                    && (hammerY <= moleY + 50) && (hammerY <= moleY + 50)){
                molegame_score ++;
                System.out.println("Score : " + molegame_score);
                resetMole();
            }
        }

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
            if((hammerY + 20) < 350){
                hammerY += 20;
            }
        }
        if (pressedKeys.contains(KeyEvent.VK_W)){ // W 누르면 상단으로 10만큼 이동
            if((hammerY - 20) >= -50){
                hammerY -= 20;
            }
        }
        if (pressedKeys.contains(KeyEvent.VK_D)){ // D 누르면 우측으로 10만큼 이동
            if((hammerX + 20) < 750){
                hammerX += 20;
            }
        }
        if (pressedKeys.contains(KeyEvent.VK_A)){ // A 누르면 좌측으로 10만큼 이동
            if((hammerX - 20) >= -50){
                hammerX -= 20;
            }
        }
    }



    public static void main(String[] args) {
        Molegame m = new Molegame();
    }
}