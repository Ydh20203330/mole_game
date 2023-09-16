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
    private HashSet<Integer> pressedKeys; // 눌린 키를 다루기 위한 해시셋
    private int molegame_score1, molegame_score2; // 점수 1P, 2P
    private JLabel score_label1, score_label2; // 점수 표시할 칸 1,2
    private List<Mole> moles; // 두더지 클래스 리스트
    private Hammer hammer1, hammer2; // 플레이어 1, 2

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
        molegame_score1 = 0; molegame_score2 = 0;
        ImageIcon backgroundicon = new ImageIcon("Molegamebackground.png"); // 배경 이미지 imageicon으로 획득
        backgroundImg = backgroundicon.getImage(); // 배경 이미지 저장

        ImageIcon hammericon = new ImageIcon("hammer.png"); // 망치 이미지
        hammerImg = hammericon.getImage();

        ImageIcon moleicon = new ImageIcon("molerat.png"); // 두더지 이미지
        moleImg = moleicon.getImage();
        hammer1 = new Hammer(100, 100, hammerImg, KeyEvent.VK_W, KeyEvent.VK_S,
                KeyEvent.VK_A, KeyEvent.VK_D); // 플레이어 1 해머 설정
        hammer2 = new Hammer(600, 100, hammerImg, KeyEvent.VK_UP, KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT); // 플레이어 2 해머 설정
        pressedKeys = new HashSet<>();
        setTitle("Mole Game - 20203330 YDH"); // 20203330 윤대현의 두더지 게임을 타이틀에
        setSize(800,400); // 창 크기 조절

        moles = new ArrayList<>(); // 두더지를 담을 ArrayList 생성
        for (int i=0; i<5; i++){ // 두더지 수는 유동적으로 변화 가능
            moles.add(new Mole()); // 두더지 세마리 추가
        } // 두더지 인스턴스 배열에 추가
        for (Mole mole : moles){ // 각 두더지별로 적용
            mole.timerstart(); // 타이머 ON
        }

        score_label1 = new JLabel(); // 1번 플레이어 점수 기록용 label 생성
        score_label2 = new JLabel(); // 2번 플레이어 점수 기록용 label 생성
        score_label1.setText("" + molegame_score1); // 텍스트 설정
        score_label2.setText("" + molegame_score2); // 텍스트 설정

        Font score_font = new Font("궁서", Font.BOLD, 30); // 폰트 설정
        score_label1.setFont(score_font); // 폰트 적용
        score_label2.setFont(score_font); // 폰트 적용

        score_label1.setBounds(20, 20, 30, 30); // 점수판 띄움
        score_label2.setBounds(740, 20, 30, 30); // 점수판 띄움
        this.add(score_label1); // 1P 점수판 JLabel JFrame에 추가
        this.add(score_label2); // 2P 점수판 JLabel JFrame에 추가

        setResizable(false); // 창 크기 변경 불가
        setLocationRelativeTo(null); // 창 중앙에 위치
        setLayout(null); // 레이아웃 설정 null로
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // X 누르면 프로그램 종료
        setVisible(true); // JFrame이 보이게끔 설정
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
                g.drawImage(moleImg, mole.getX(), mole.getY(), this); // 두더지들 그려냄
            }
        }
        if (hammerImg != null){
            hammer1.draw(g); // 1P 해머 그리기
            hammer2.draw(g); // 2P 해머 그리기
        }
        if (score_label1 != null) {
            score_label1.repaint(); // 점수판 1 repaint
        }
        if (score_label2 != null) {
            score_label2.repaint(); // 점수판 2 repaint
        }
    }

    @Override
    public void keyPressed(KeyEvent e) { // 키 누름처리, 해시셋에 키 코드가 저장됨
        int keyCode = e.getKeyCode(); // 키코드 받아오기
        pressedKeys.add(keyCode); // 해시셋에 누른 키 포함시킴

        hammer1.move(pressedKeys); // hammer1 움직이기
        hammer2.move(pressedKeys); // hammer2 움직이기

        if (keyCode == KeyEvent.VK_SPACE){ // 1P 전용: 스페이스를 눌렀을 경우
            for(Mole mole : moles){
                if((hammer1.getX() <= mole.getX() + 50) && (hammer1.getX() >= mole.getX() - 50) // 1p의 망치가 두더지 이미지의 좌표범위와 일치하는 경우
                        && (hammer1.getY() <= mole.getY() + 50) && (hammer1.getY() <= mole.getY() + 50)){
                    molegame_score1 ++; // 점수 획득
                    System.out.println("1P Score : " + molegame_score1); // 임시 점수출력
                    score_label1.setText("" + molegame_score1); // 1P 점수 설정
                    resetMole(mole); // 두더지 초기화
                }
            }
        }
        if (keyCode == KeyEvent.VK_ENTER){ // 2P 전용: 엔터를 눌렀을 경우
            for(Mole mole : moles){
                if((hammer2.getX() <= mole.getX() + 50) && (hammer2.getX() >= mole.getX() - 50) // 2p의 망치가 두더지 이미지의 좌표범위와 일치하는 경우
                        && (hammer2.getY() <= mole.getY() + 50) && (hammer2.getY() <= mole.getY() + 50)){
                    molegame_score2 ++; // 점수 획득
                    System.out.println("2P Score : " + molegame_score2); // 임시 점수출력
                    score_label2.setText("" + molegame_score2); // 2P 점수 설정
                    resetMole(mole); // 두더지 초기화
                }
            }
        }

        repaint(); // repaint
    }

    @Override
    public void keyReleased(KeyEvent e){ // 키 땜처리, 해시셋에 키 코드가 삭제됨
        int keyCode = e.getKeyCode(); // 땐 키코드 받아옴
        pressedKeys.remove(keyCode); // 해시셋에서 해당 키코드 제거
        repaint(); // 다시 그림
    }

    @Override
    public void keyTyped(KeyEvent e){ }

    private void resetMole(Mole mole) { // 두더지를 망치로 때렸을 때 위치 변경과 타이머 초기화
        mole.setX((int)(Math.random() * (getWidth() - 50)));
        mole.setY((int)(Math.random() * (getHeight() - 50)));
        mole.reset();
    }

    class Mole { // 한마리의 두더지가 아닌 여러 마리의 두더지를 다루기 위한 Mole 클래스
        private int x, y; // 두더지 좌표
        private Timer moleTimer; // 두더지에게 적용되는 타이머
        private int delay; // 타이머 주기

        public Mole() { // Mole 클래스 생성자, 좌표값과 delay 설정 후 Timer 설정
            x = -100; y = - 100; delay = 2500;
            moleTimer = new Timer(delay, (ActionEvent e) -> { // 타이머 동작, 딜레이마다 위치 재조정
                x = (int)(Math.random() * (getWidth() - 50)); // 랜덤 x좌표
                y = (int)(Math.random() * (getHeight() - 50)); // 랜덤 y좌표
                repaint(); // 다시 그림
            }); // 두더지가 타이머에 따라 랜덤한 위치로 이동함
        }
        public int getX() { return x; } // private된 두더지의 x좌표 획득
        public int getY() { return y; } // private된 두더지의 y좌표 획득
        public void setX(int x) { this.x = x; } // x좌표 설정
        public void setY(int y) { this.y = y; } // y좌표 설정
        public void reset() { // 두더지를 초기화시키는 함수 reset()
            x = -100; y = -100; // 화면 밖으로 옮김
            moleTimer.restart(); // 타이머 재시작함
            moleTimer.setInitialDelay(delay); // 딜레이 재설정
        }
        public void timerstart() { moleTimer.start(); } // 타이머 킴
    }


    public static void main(String[] args) {
        Molegame m = new Molegame();
    }
}