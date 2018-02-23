package n0277988.jazz.platformgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by jaros on 16/02/2018.
 */

public class Wall {
    private Rect Left;
    private Rect Right;
    private int color;

    public Wall(int LeftSide, int Thickness, int Top, int gap, int Color) {
        this.Left = new Rect(0, Top, LeftSide, Top + Thickness);
        this.Right = new Rect(LeftSide + gap, Top, Constants.Screen_Width, Top + Thickness);
        this.color = Color;
    }

    public int getTop(){
        return Right.top;
    }

    public boolean isFinish()
    {
        return false;
    }

    public boolean playerCollision(GameCharacter player) {

        if(Left.contains(player.getCharacter().left, player.getCharacter().top) ||
                Left.contains(player.getCharacter().right, player.getCharacter().top) ||
                Left.contains(player.getCharacter().left, player.getCharacter().bottom) ||
                Left.contains(player.getCharacter().right, player.getCharacter().bottom) ||
                Left.contains(player.getCharacter().centerX(), player.getCharacter().top) ||
                Left.contains(player.getCharacter().left, player.getCharacter().centerY()) ||
                Left.contains(player.getCharacter().right, player.getCharacter().centerY())||

                        Right.contains(player.getCharacter().left, player.getCharacter().top) ||
                        Right.contains(player.getCharacter().right, player.getCharacter().top) ||
                        Right.contains(player.getCharacter().left, player.getCharacter().bottom) ||
                        Right.contains(player.getCharacter().right, player.getCharacter().bottom) ||
                        Right.contains(player.getCharacter().centerX(), player.getCharacter().top) ||
                        Right.contains(player.getCharacter().left, player.getCharacter().centerY()) ||
                        Right.contains(player.getCharacter().right, player.getCharacter().centerY())
                )
        {
            return true;
        }
        return false;
    }


    public void draw(Canvas canvas) {
        Paint CharColor = new Paint();
        CharColor.setColor(color);
        canvas.drawRect(Left, CharColor);
        canvas.drawRect(Right, CharColor);
      //  canvas.drawBitmap(character_bitmap, Character.left - , Character.top - , null);
    }


    public void move(float speed) {
        Left.top += speed;
        Left.bottom += speed;
        Right.top += speed;
        Right.bottom += speed;
    }


    public void update() {
    }
}
