import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


class GamePanel extends JPanel {
    private Image backgroundImg;
    private Image hammerImg, hitImg;
    private Image moleImg;
    private List<Mole> moles;
    private Hammer hammer1, hammer2;
    private int molegame_score1, molegame_score2;
    private JLabel score_label1, score_label2;
    private HashSet<Integer> pressedKeys;

    public GamePanel() {
        molegame_score1 = 0; molegame_score2 = 0;
        ImageIcon backgroundicon = new ImageIcon("Molegamebackground.png"); // 배경 이미지 imageicon으로 획득
        backgroundImg = backgroundicon.getImage(); // 배경 이미지 저장

        ImageIcon hammericon = new ImageIcon("hammer.png"); // 망치 이미지
        hammerImg = hammericon.getImage();
        ImageIcon hithammericon = new ImageIcon("hammer_hit.png"); // 망치 타격 이미지
        hitImg = hithammericon.getImage();

        ImageIcon moleicon = new ImageIcon("molerat.png"); // 두더지 이미지
        moleImg = moleicon.getImage();
        hammer1 = new Hammer(100, 100, hammerImg, hitImg, KeyEvent.VK_W, KeyEvent.VK_S,
                KeyEvent.VK_A, KeyEvent.VK_D); // 플레이어 1 해머 설정
        hammer2 = new Hammer(600, 100, hammerImg, hitImg, KeyEvent.VK_UP, KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT); // 플레이어 2 해머 설정
        pressedKeys = new HashSet<>();

        moles = new ArrayList<>(); // 두더지를 담을 ArrayList 생성
        for (int i=0; i<5; i++){ // 두더지 수는 유동적으로 변화 가능
            moles.add(new Mole()); // 두더지 세마리 추가
        } // 두더지 인스턴스 배열에 추가

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode(); // 키코드 받아오기
                pressedKeys.add(keyCode); // 해시셋에 누른 키 포함시킴

                hammer1.move(pressedKeys); // hammer1 움직이기
                hammer2.move(pressedKeys); // hammer2 움직이기

                if (keyCode == KeyEvent.VK_SPACE){ // 1P 전용: 스페이스를 눌렀을 경우
                    hammer1.hit();
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
                    hammer2.hit();
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
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode(); // 땐 키코드 받아옴
                pressedKeys.remove(keyCode); // 해시셋에서 해당 키코드 제거
                repaint(); // 다시 그림
            }
        });

        setFocusable(true);
        requestFocus();
    }

    @Override
    protected void paintComponent(Graphics g) {
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

    private void resetMole(Mole mole) { // 두더지를 망치로 때렸을 때 위치 변경과 타이머 초기화
        mole.setX((int)(Math.random() * (getWidth() - 50)));
        mole.setY((int)(Math.random() * (getHeight() - 50)));
        mole.reset();
    }

    public void startGame() {
        for (Mole mole : moles){ // 각 두더지별로 적용
            mole.timerstart(); // 타이머 ON
        }

        score_label1 = new JLabel(); // 1번 플레이어 점수 기록용 label 생성
        score_label2 = new JLabel(); // 2번 플레이어 점수 기록용 label 생성
        score_label1.setText("" + molegame_score1); // 텍스트 설정
        score_label2.setText("" + molegame_score2); // 텍스트 설정

        Font score_font = new Font("궁서", Font.BOLD, 20); // 폰트 설정
        score_label1.setFont(score_font); // 폰트 적용
        score_label2.setFont(score_font); // 폰트 적용

        score_label1.setBounds(20, 20, 30, 30); // 점수판 띄움
        score_label2.setBounds(740, 20, 30, 30); // 점수판 띄움
        this.add(score_label1); // 1P 점수판 JLabel JFrame에 추가
        this.add(score_label2); // 2P 점수판 JLabel JFrame에 추가
    }
    public void diffcultSet(int delay){
        for (Mole mole : moles) {
            mole.delaySet(delay);
        }
    }
}



public class Molegame extends JFrame { // JFrame, KeyListener 사용
    private JPanel startPanel; // 시작 화면 패널
    private JPanel cardPanel; // 시작 화면, 게임 화면을 나누기 위한 패널
    private CardLayout cardLayout; // 카드 패널 레이아웃
    private JButton easyButton, hardButton, exitButton; // 난이도 선택 버튼과 종료 버튼

    private GamePanel gamePanel;


    public Molegame() { // 생성자
        setTitle("Mole Game - 20203330 YDH"); // 20203330 윤대현의 두더지 게임을 타이틀에
        setSize(800,400); // 창 크기 조절


        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setPreferredSize(new Dimension(800, 400));

        initStartScreen();

        gamePanel = new GamePanel();

        this.add(cardPanel);

        setResizable(false); // 창 크기 변경 불가
        setLocationRelativeTo(null); // 창 중앙에 위치
        setLayout(null); // 레이아웃 설정 null로
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // X 누르면 프로그램 종료
        setVisible(true); // JFrame이 보이게끔 설정
        setFocusable(true);
        requestFocus(); // 키 입력 받을 수 있도록 설정
    }

    private void initStartScreen() { // 초기 화면 설정용 함수
        startPanel = new JPanel(); // 패널 할당
        startPanel.setLayout(new BorderLayout()); // 상하좌우 배치하는 Layout 설정
        startPanel.setPreferredSize(new Dimension(800, 400));


        JLabel titleLabel = new JLabel("두더지 게임"); // 타이틀 JLabel 생성
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24)); // 타이틀 폰트 설정
        titleLabel.setHorizontalAlignment(JLabel.CENTER); // 글 중앙 정렬
        startPanel.add(titleLabel,BorderLayout.NORTH); // startPanel의 북쪽에 타이틀 Label 위치시킴

        easyButton = new JButton("초보자용");
        hardButton = new JButton("상급자용");
        exitButton = new JButton("종료"); // 버튼 세개에 JButton 할당

        JPanel buttonPanel = new JPanel(); // 버튼을 담기 위한 패널 생성
        buttonPanel.setLayout(new GridLayout(3, 1)); // 격자무늬 형태의 버튼 패널 생성
        buttonPanel.add(easyButton);
        buttonPanel.add(hardButton);
        buttonPanel.add(exitButton); // 버튼 세개를 패널에 추가
        startPanel.add(buttonPanel,BorderLayout.CENTER); // starPanel의 남쪽에 버튼 Panel 위치시킴

        easyButton.addActionListener(new ActionListener() { // 초보자용 버튼 작동
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.diffcultSet(3000); // 딜레이 설정
                cardPanel.add(gamePanel, "game");
                cardLayout.show(cardPanel, "game");
                requestFocus(); // 키 입력 받을 수 있도록 포커스 요청
                gamePanel.startGame();
            }
        });
        hardButton.addActionListener(new ActionListener() { // 상급자용 버튼 작동
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.diffcultSet(1500); // 딜레이 설정
                cardPanel.add(gamePanel, "game");
                cardLayout.show(cardPanel, "game");
                requestFocus(); // 키 입력 받을 수 있도록 포커스 요청
                gamePanel.startGame();
            }
        });
        exitButton.addActionListener(new ActionListener() { // 종료 버튼 작동
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // 종료
            }
        });

        cardPanel.add(startPanel, "start");
        cardLayout.show(cardPanel, "start");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Molegame();
            }
        });
    }
}