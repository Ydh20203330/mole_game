import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

class Hammer { // 2인용을 구현하기 위한 hAMMER 클래스
    private int hammerX, hammerY;
    private int up,down,left,right;
    private Image hammerImg, hammerImgbackup, hitImg;
    private Timer hitTimer;

    Hammer(int hammerX, int hammerY, Image hammerImg, Image hitImg,
           int up, int down, int left, int right){
        this.hammerX = hammerX; this.hammerY = hammerY; // 좌표 설정
        this.hammerImg = hammerImg; this.hitImg = hitImg; // 이미지 설정
        this.up = up;   this.down = down; // 상하 키 설정
        this.left = left;   this.right = right; // 좌우 키 설정

        hitTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // 타이머가 켜진 후 0.5초 뒤에 꺼짐
                release();
                hitTimer.stop();
            }
        });
        hitTimer.setRepeats(false); // 한번만 동작하게끔 타이머 설정
    }

    public void move(HashSet<Integer> pressedKeys) {
        if (pressedKeys.contains(down)){ // 하단 키 누르면 하단으로 20만큼 이동
            if((hammerY + 20) < 350){
                hammerY += 20;
            }
        }
        if (pressedKeys.contains(up)){ // 상단 키 누르면 상단으로 20만큼 이동
            if((hammerY - 20) >= -50){
                hammerY -= 20;
            }
        }
        if (pressedKeys.contains(right)){ // 우측 키 누르면 우측으로 20만큼 이동
            if((hammerX + 20) < 750){
                hammerX += 20;
            }
        }
        if (pressedKeys.contains(left)){ // 좌측 키 누르면 좌측으로 20만큼 이동
            if((hammerX - 20) >= -50){
                hammerX -= 20;
            }
        }
    }

    public int getX(){ return hammerX; } // private된 망치 X좌표 얻기
    public int getY(){ return hammerY; } // private된 망치 Y좌표 얻기
    public void hit() {
        if(!hitTimer.isRunning()) {
            hammerImgbackup = hammerImg;
            hammerImg = hitImg;
            hitTimer.start();
            playSound("squeaky-toy.wav");
        }
    }

    public void release() {
        hammerImg = hammerImgbackup; // 이미지 원상 복구
    }
    public static void playSound(String soundFilePath) { // 뿅망치 소리를 위한 playSound 함수
        // 이 부분은 잘 몰라서 인터넷 참고함
        try {
            File soundFile = new File(soundFilePath); // 파일을 경로에서 받아옴
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip(); //
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-15.0f);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    
    public void draw(Graphics g){
        g.drawImage(hammerImg, hammerX, hammerY, null);
    }

}