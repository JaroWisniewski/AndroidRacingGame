package n0277988.jazz.platformgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by jaros on 16/02/2018.
 */

public class Boost implements GameObject {
    private Rect Object;
    protected Bitmap arrow;

    public Boost(Rect Rect, int Color, Bitmap map) {
        this.Object = Rect;
        this.arrow = map;
    }

    public Boost(int left, int top, Bitmap map){
        this.Object = new Rect(left, top, left + 100, top + 100);
        this.arrow = map;
    }

    public void modify(int Left, int top)
    {
        Object.top = top;
        Object.bottom = top+100;
        Object.right = Left+100;
        Object.left = Left;
    }

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

    public void update(Point point){
        Object.set(point.x - Object.width()/2, point.y - Object.height()/2, point.x + Object.width()/2, point.y + Object.height()/2);
    }
}
