import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;

class Hammer { // 2인용을 구현하기 위한 hAMMER 클래스
    private int hammerX, hammerY;
    private int up,down,left,right;
    private Image hammerImg;

    Hammer(int hammerX, int hammerY, Image hammerImg,
           int up, int down, int left, int right){
        this.hammerX = hammerX; this.hammerY = hammerY;
        this.hammerImg = hammerImg;
        this.up = up;   this.down = down;
        this.left = left;   this.right = right;
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

    public void draw(Graphics g){
        if (hammerImg != null) {
            g.drawImage(hammerImg, hammerX, hammerY, null);
        }
    }

}