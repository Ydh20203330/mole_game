import javax.swing.*;
import java.awt.event.ActionEvent;

public class Mole extends JFrame{ // 한마리의 두더지가 아닌 여러 마리의 두더지를 다루기 위한 Mole 클래스
    private int x, y; // 두더지 좌표
    private Timer moleTimer; // 두더지에게 적용되는 타이머
    private int delay; // 타이머 주기

    public Mole() { // Mole 클래스 생성자, 좌표값과 delay 설정 후 Timer 설정
        x = -100; y = - 100; delay = 3000;
        moleTimer = new Timer(delay, (ActionEvent e) -> { // 타이머 동작, 딜레이마다 위치 재조정
            x = (int)(Math.random() * (800 - 50)); // 랜덤 x좌표
            y = (int)(Math.random() * (400 - 50)); // 랜덤 y좌표
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
    public void delaySet(int delay) {this.delay = delay;} // 난이도 선택 시 두더지 이동 시간 설정
    public void timerstart() { moleTimer.start(); } // 타이머 킴
}
