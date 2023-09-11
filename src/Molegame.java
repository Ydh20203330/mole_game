import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Molegame extends JFrame implements KeyListener { // JFrame, KeyListener 사용
    private Image backgroundImg; // 배경 이미지
    private Image hammerImg; // 해머 이미지
    private Image moleImg; // 두더지 이미지
    private int hammerX , hammerY; // 해머 좌표
    private HashSet<Integer> pressedKeys; // 눌린 키를 다루기 위한 해시셋
    private int molegame_score; // 점수
    private JLabel score_label; // 점수 표시할 칸

    private List<Mole> moles; // 두더지 클래스 리스트

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

        moles = new ArrayList<>();
        for (int i=0; i<3; i++){
            moles.add(new Mole());
        } // 두더지 인스턴스 배열에 추가
        for (Mole mole : moles){
            mole.timerstart();
        }


        score_label = new JLabel(); // 점수 기록용 label 생성
        score_label.setText("" + molegame_score); // 텍스트 설정
        Font score_font = new Font("궁서", Font.BOLD, 50); // 폰트 설정
        score_label.setFont(score_font); // 폰트 적용

        score_label.setBounds(0, 0, 50, 50); // 점수판 띄움
        this.add(score_label);

        ImageIcon backgroundicon = new ImageIcon("Molegamebackground.png"); // 배경 이미지 imageicon으로 획득
        backgroundImg = backgroundicon.getImage(); // 배경 이미지 저장

        ImageIcon hammericon = new ImageIcon("hammer.png"); // 망치 이미지
        hammerImg = hammericon.getImage();

        ImageIcon moleicon = new ImageIcon("molerat.png"); // 두더지 이미지
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
        if (moleImg != null){
            for (Mole mole: moles){
                g.drawImage(moleImg, mole.getX(), mole.getY(), this);
            }

        }
        if (hammerImg != null) {
            g.drawImage(hammerImg, hammerX, hammerY, this);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) { // 키 누름처리, 해시셋에 키 코드가 저장됨
        int keyCode = e.getKeyCode(); // 키코드 받아오기
        pressedKeys.add(keyCode); // 해시셋에 누른 키 포함시킴

        if (keyCode == KeyEvent.VK_SPACE){ // 스페이스를 눌렀을 경우
            for(Mole mole : moles){
                if((hammerX <= mole.getX() + 50) && (hammerX >= mole.getX() - 50) // 두더지 이미지의 좌표범위와 일치하는 경우
                        && (hammerY <= mole.getY() + 50) && (hammerY <= mole.getY() + 50)){
                    molegame_score ++; // 점수 획득
                    System.out.println("Score : " + molegame_score); // 임시 점수출력
                    score_label.setText("" + molegame_score); // 점수 우측 상단에 호출용
                    resetMole(mole); // 두더지 초기화
                }
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

    private void resetMole(Mole mole) { // 두더지를 망치로 때렸을 때 위치 변경과 타이머 초기화
        mole.setX((int)(Math.random() * (getWidth() - 50)));
        mole.setY((int)(Math.random() * (getHeight() - 50)));
        mole.reset();
    }

    class Mole { // 한마리의 두더지가 아닌 여러 마리의 두더지를 다루기 위한 Mole 클래스
        private int x, y;
        private Timer moleTimer;
        private int delay;

        public Mole() { // Mole 클래스 생성자, 좌표값과 delay 설정 후 Timer 설정
            x = -100; y = - 100; delay = 2500;
            moleTimer = new Timer(delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    x = (int)(Math.random() * (getWidth() - 50));
                    y = (int)(Math.random() * (getHeight() - 50));
                    repaint();
                }
            }); // 두더지가 타이머에 따라 랜덤한 위치로 이동함
        }
        public int getX() { return x; }
        public int getY() { return y; }
        public void setX(int x) { this.x = x; }
        public void setY(int y) { this.y = y; }
        public void reset() {
            x = -100; y = -100;
            moleTimer.restart();
            moleTimer.setInitialDelay(delay);
        }
        public void timerstart() { moleTimer.start(); }
    }



    public static void main(String[] args) {
        Molegame m = new Molegame();
    }
}