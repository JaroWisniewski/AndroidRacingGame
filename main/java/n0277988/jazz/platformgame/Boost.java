package n0277988.jazz.platformgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by jaros on 16/02/2018.
 *
 * Boost Object - controls movement, collision control and drawing of the boost object (power up)
 */

public class Boost implements GameObject {
    private static final int sideLength = 100;
    private Rect Object;
    protected Bitmap arrow;

    public Boost(Rect Rect, Bitmap map) {
        this.Object = Rect;
        this.arrow = map;
    }

    public Boost(int left, int top, Bitmap map){
        this.Object = new Rect(left, top, left + sideLength, top + sideLength);
        this.arrow = map;
    }

    public void modify(int Left, int top)
    {
        Object.top = top;
        Object.bottom = top + sideLength;
        Object.right = Left + sideLength;
        Object.left = Left;
    }

    // Decided to use contains with multiple points as intersects method didn't work correctly
    public boolean playerCollision(GameCharacter player){
        if(Object.contains(player.getCharacter().left, player.getCharacter().top) ||
                Object.contains(player.getCharacter().right, player.getCharacter().top) ||
                Object.contains(player.getCharacter().left, player.getCharacter().bottom) ||
                Object.contains(player.getCharacter().right, player.getCharacter().bottom)||
                Object.contains(player.getCharacter().centerX(), player.getCharacter().top) ||
                Object.contains(player.getCharacter().left, player.getCharacter().centerY()) ||
                Object.contains(player.getCharacter().right, player.getCharacter().centerY()))
        {
            return true;
        }
        return false;
    }

    public int getTop(){
        return Object.top;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(arrow, Object.left, Object.top, null);
    }

    @Override
    public void update() {

    }

    public void move(float speed) {
        Object.top += speed;
        Object.bottom += speed;
    }
}
