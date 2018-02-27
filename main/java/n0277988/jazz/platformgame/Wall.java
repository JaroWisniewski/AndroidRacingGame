package n0277988.jazz.platformgame;

import android.graphics.Bitmap;
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
    protected Rect Left;
    private Rect Right;
    protected Bitmap wall_texture;


    public Wall(int LeftSide, int Thickness, int Top, int gap, Bitmap map) {
        this.Left = new Rect(0, Top, LeftSide, Top + Thickness);
        this.Right = new Rect(LeftSide + gap, Top, Constants.Screen_Width, Top + Thickness);
        wall_texture = map;
    }

    public void modify(int LeftS, int thick, int top, int gap)
    {
        Left.top = top;
        Left.bottom = top+thick;
        Left.right = LeftS;

        Right.left = LeftS + gap;
        Right.top = top;
        Right.bottom = top + thick;
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
        int x = Left.right;
        while (x >= (0-wall_texture.getWidth())) {
            canvas.drawBitmap(wall_texture, x - wall_texture.getWidth(), Left.top, null);
            x -= wall_texture.getWidth();
        }
        x = Right.left;
        while (x <= Constants.Screen_Width)
        {
            canvas.drawBitmap(wall_texture, x, Right.top, null);
            x += wall_texture.getWidth();

        }
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
